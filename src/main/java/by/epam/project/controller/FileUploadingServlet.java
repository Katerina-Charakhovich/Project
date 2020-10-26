package by.epam.project.controller;

import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.entity.impl.User;
import by.epam.project.service.impl.UserServiceImpl;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;

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

    private UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL);
        File fileSaveDir = new File(UPLOAD_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        request.getServletContext().getRealPath("/");
        for (Part part : request.getParts()) {
            try {
                String path = part.getSubmittedFileName();
                String randFilename = UUID.randomUUID() + path.substring(path.lastIndexOf("."));
                part.write(UPLOAD_DIR + randFilename);
                part.write(SERVER_UPLOAD_DIR + randFilename);
                userServiceImpl.updateAvatar(email, randFilename);
                User user = userServiceImpl.findUserWithTheAllInfoByLogin(email);
                request.setAttribute(RequestAttribute.GENDER, user.getUserGender());
                request.setAttribute(RequestAttribute.COUNTRY, user.getCountry());
                request.setAttribute(RequestAttribute.ABOUT_ME, user.getAboutMe());
                request.setAttribute(RequestAttribute.NAME_USER, user.getName());
                request.setAttribute(RequestAttribute.AVATAR, user.getAvatar());
                request.getServletPath();
            } catch (IOException | ServiceException e) {
                boolean uploadResult = false;
                request.setAttribute(RequestAttribute.UPLOAD_RESULT, uploadResult);
                String error = e.getMessage();
                request.setAttribute(RequestAttribute.ERROR, error);
                request.getRequestDispatcher(PathToPage.ERROR_PAGE).forward(request, response);
            }
            request.getRequestDispatcher(PathToPage.PROFILE_PAGE).forward(request, response);
        }
    }
}




