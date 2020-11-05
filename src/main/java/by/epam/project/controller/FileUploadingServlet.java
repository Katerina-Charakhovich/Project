package by.epam.project.controller;

import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.entity.impl.User;
import by.epam.project.service.MediaService;
import by.epam.project.service.UserService;
import by.epam.project.service.impl.MediaServiceImpl;
import by.epam.project.service.impl.UserServiceImpl;
import by.epam.project.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

import java.util.UUID;

import static by.epam.project.command.RequestAttribute.*;


@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)

public class FileUploadingServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();
    private MediaService mediaService = MediaServiceImpl.getInstance();

    /**
     * Servlet for loading avatars
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getServletContext().getRealPath(ADD_FOR_PATH_AVATAR);
        for (Part part : request.getParts()) {
            try {
                String path = part.getSubmittedFileName();
                String randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf("."));
                String avatar = request.getParameter(AVATAR);
                switch (avatar) {
                    case USER: {
                        initUserAvatar(request, part, randFilename);
                        request.getRequestDispatcher(PathToPage.PROFILE_PAGE).forward(request, response);
                        break;
                    }
                    case FILM_EN: {
                        String filmName = (String) request.getSession().getAttribute(FILM_NAME);
                        String language = (String) request.getSession().getAttribute(LANGUAGE);
                        initFilmEn(request, part, randFilename, filmName, language);
                        request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, randFilename);
                        request.getRequestDispatcher(PathToPage.FILM_EDIT_EN).forward(request, response);
                        break;
                    }
                    case FILM_RU: {
                        String filmName = (String) request.getSession().getAttribute(FILM_NAME);
                        String language = (String) request.getSession().getAttribute(LANGUAGE);
                        initFilmRu(part, randFilename, filmName, language);
                        request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, randFilename);
                        request.getRequestDispatcher(PathToPage.FILM_EDIT_EN).forward(request, response);
                        break;
                    }
                    default: {
                        throw new ServiceException("Failed photo upload attempt");
                    }
                }
            } catch (IOException | ServiceException e) {
                boolean uploadResult = false;
                request.setAttribute(RequestAttribute.UPLOAD_RESULT, uploadResult);
                String error = e.getMessage();
                request.setAttribute(RequestAttribute.ERROR, error);
                request.getRequestDispatcher(PathToPage.ERROR_PAGE).forward(request, response);
            }
        }
    }

    private void initFilmRu(Part part, String randFilename, String filmName, String language) throws IOException, ServiceException {
        File fileSaveDirForFilmAvatarRu = new File(UPLOAD_DIR_FOR_FILM_AVATAR_RU);
        if (!fileSaveDirForFilmAvatarRu.exists()) {
            fileSaveDirForFilmAvatarRu.mkdirs();
        }
        part.write(UPLOAD_DIR_FOR_FILM_AVATAR_RU + randFilename);
        part.write(SERVER_UPLOAD_DIR_FOR_FILM_RU + randFilename);
        mediaService.updateAvatarRu(filmName, randFilename, language.toLowerCase());
    }

    private void initFilmEn(HttpServletRequest request, Part part, String randFilename, String filmName, String language) throws IOException, ServiceException {
        File fileSaveDirForFilmAvatarEn = new File(UPLOAD_DIR_FOR_FILM_AVATAR_EN);
        if (!fileSaveDirForFilmAvatarEn.exists()) {
            fileSaveDirForFilmAvatarEn.mkdirs();
        }
        part.write(UPLOAD_DIR_FOR_FILM_AVATAR_EN + randFilename);
        part.write(SERVER_UPLOAD_DIR_FOR_FILM_EN + randFilename);
        mediaService.updateAvatarEn(filmName, randFilename, language.toLowerCase());
    }

    private void initUserAvatar(HttpServletRequest request, Part part, String randFilename) throws
            ServiceException, IOException {
        File fileSaveDirForUserAvatar = new File(UPLOAD_DIR_FOR_USER_AVATAR);
        if (!fileSaveDirForUserAvatar.exists()) {
            fileSaveDirForUserAvatar.mkdirs();
        }
        part.write(UPLOAD_DIR_FOR_USER_AVATAR + randFilename);
        part.write(SERVER_UPLOAD_DIR + randFilename);

        String email = (String) request.getSession().getAttribute(EMAIL);
        userService.updateAvatar(email, randFilename);
        User user = userService.findUserWithTheAllInfoByLogin(email);
        request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
        request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
        request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
        request.setAttribute(RequestAttribute.NAME_USER, user.getName());
        request.setAttribute(RequestAttribute.AVATAR, user.getAvatar());
    }
}





