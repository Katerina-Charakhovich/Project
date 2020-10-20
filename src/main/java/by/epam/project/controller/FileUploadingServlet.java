package by.epam.project.controller;

import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.entity.impl.User;
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

@WebServlet(urlPatterns = {"/upload/*"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)

public class FileUploadingServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "C:\\Kristina\\TaskWebLogin\\src\\main\\webapp\\pictures_for_project\\picturesForAvatar\\";
    public static final String SUCCESSFULLY_LOADING = MessageManager.getProperty("message.successLoading");
    public static final String NOT_SUCCESSFULLY_LOADING = MessageManager.getProperty("message.notSuccessLoading");
    public static final String FILE_FOR_UPLOAD = ConfigurationManager.getProperty("path.page.profile");
    private static final String EMAIL = "email";
    private final UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();
    private static final String GENDER = "gender";
    private static final String COUNTRY = "country";
    private static final String ABOUT_ME = "about_me";
    private static final String AVATAR = "avatar";
    private static final String NAME = "name";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL);
        File fileSaveDir = new File(UPLOAD_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        for (Part part : request.getParts()) {
            try {
                String path = part.getSubmittedFileName();
                String randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf("."));
                part.write(UPLOAD_DIR + randFilename);
                request.setAttribute("upload_result", SUCCESSFULLY_LOADING);
                userServiceImpl.updateAvatar(email, randFilename);
                User user = userServiceImpl.findUserWithTheAllInfoByLogin(email);
                request.setAttribute(GENDER, user.getGender());
                request.setAttribute(COUNTRY, user.getCountry());
                request.setAttribute(ABOUT_ME, user.getAboutMe());
                request.setAttribute(NAME, user.getName());
                request.setAttribute(AVATAR, user.getAvatar());
            } catch (IOException | ServiceException e) {
                request.setAttribute("upload_result", NOT_SUCCESSFULLY_LOADING);
            }
            request.getRequestDispatcher(FILE_FOR_UPLOAD).forward(request, response);
        }
    }
}




