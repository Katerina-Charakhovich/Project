package by.epam.project.controller;

import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.exception.CommandException;
import by.epam.project.entity.impl.User;
import by.epam.project.service.MediaService;
import by.epam.project.service.UserService;
import by.epam.project.service.impl.MediaServiceImpl;
import by.epam.project.service.impl.UserServiceImpl;
import by.epam.project.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
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


/**
 * Servlet for loading avatars
 */
@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();
    private MediaService mediaService = MediaServiceImpl.getInstance();

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
                        request.getSession().setAttribute(RequestAttribute.CURRENT_PAGE, PathToPage.PROFILE_PAGE);
                        request.getSession().setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND, COMMAND_PROFILE);
                        request.getRequestDispatcher(PathToPage.PROFILE_PAGE).forward(request, response);
                        break;
                    }
                    case FILM_EN: {
                        String filmName = (String) request.getSession().getAttribute(FILM_NAME);

                        initFilmEn(part, randFilename, filmName, EN);
                        request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, randFilename);
                        request.getSession().setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND, COMMAND_FORWARD_TO_FILM_EDIT);
                        request.getSession().setAttribute(RequestAttribute.CURRENT_PAGE, PathToPage.FILM_EDIT_EN);
                        request.getRequestDispatcher(PathToPage.FILM_EDIT_EN).forward(request, response);
                        break;
                    }
                    case FILM_RU: {
                        String filmName = (String) request.getSession().getAttribute(FILM_NAME);
                        initFilmRu(part, randFilename, filmName, "ru");
                        request.getSession().setAttribute(RequestAttribute.CURRENT_FILM_AVATAR, randFilename);
                        request.getSession().setAttribute(RequestAttribute.LANG_CHANGE_PROCESS_COMMAND, COMMAND_FORWARD_TO_FILM_EDIT);
                        request.getSession().setAttribute(RequestAttribute.CURRENT_PAGE, PathToPage.FILM_EDIT_EN);
                        request.getRequestDispatcher(PathToPage.FILM_EDIT_EN).forward(request, response);
                        break;
                    }
                    default: {
                        throw new CommandException("Failed photo upload attempt");
                    }
                }
            } catch (IOException | CommandException e) {
                request.setAttribute(RequestAttribute.UPLOAD_RESULT, false);
                request.setAttribute(RequestAttribute.ERROR, e.getMessage());
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(PathToPage.ERROR_PAGE);
                dispatcher.forward(request, response);
            }
        }
    }

    private void initFilmRu(Part part, String randFilename, String filmName, String language) throws IOException, CommandException {
        File fileSaveDirForFilmAvatarRu = new File(UPLOAD_DIR_FOR_FILM_AVATAR_RU);
        if (!fileSaveDirForFilmAvatarRu.exists()) {
            fileSaveDirForFilmAvatarRu.mkdirs();
        }
        part.write(UPLOAD_DIR_FOR_FILM_AVATAR_RU + randFilename);
        part.write(SERVER_UPLOAD_DIR_FOR_FILM_RU + randFilename);
        try {
            mediaService.updateAvatarRu(filmName, randFilename, language.toLowerCase());
        } catch (ServiceException e) {
            throw new CommandException("Failed photo upload attempt");
        }
    }

    private void initFilmEn(Part part, String randFilename, String filmName, String language) throws IOException, CommandException {
        File fileSaveDirForFilmAvatarEn = new File(UPLOAD_DIR_FOR_FILM_AVATAR_EN);
        if (!fileSaveDirForFilmAvatarEn.exists()) {
            fileSaveDirForFilmAvatarEn.mkdirs();
        }
        part.write(UPLOAD_DIR_FOR_FILM_AVATAR_EN + randFilename);
        part.write(SERVER_UPLOAD_DIR_FOR_FILM_EN + randFilename);
        try {
            mediaService.updateAvatarEn(filmName, randFilename, language.toLowerCase());
        } catch (ServiceException e) {
            throw new CommandException("Failed photo upload attempt");
        }
    }

    private void initUserAvatar(HttpServletRequest request, Part part, String randFilename) throws
            CommandException, IOException {
        File fileSaveDirForUserAvatar = new File(UPLOAD_DIR_FOR_USER_AVATAR);
        if (!fileSaveDirForUserAvatar.exists()) {
            fileSaveDirForUserAvatar.mkdirs();
        }
        part.write(UPLOAD_DIR_FOR_USER_AVATAR + randFilename);
        part.write(SERVER_UPLOAD_DIR + randFilename);

        String email = (String) request.getSession().getAttribute(EMAIL);
        try {
            userService.updateAvatar(email, randFilename);

            User user = userService.findUserWithTheAllInfoByLogin(email);
            request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
            request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
            request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
            request.setAttribute(RequestAttribute.NAME_USER, user.getName());
            request.setAttribute(RequestAttribute.AVATAR, user.getAvatar());
        } catch (ServiceException e) {
            throw new CommandException("Failed photo upload attempt");
        }
    }
}





