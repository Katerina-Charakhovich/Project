package by.epam.project.controller;

import by.epam.project.command.Command;
import by.epam.project.dao.exception.DaoException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.command.factory.ActionFactory;
import by.epam.project.pool.exception.PoolException;
import by.epam.project.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ServiceException | DaoException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ServiceException | DaoException e) {
            LOGGER.log(Level.ERROR, e);


        }
    }

    private void processRequest(HttpServletRequest request
            , HttpServletResponse response) throws ServletException, IOException, DaoException, ServiceException {
        String page;
        Command command = ActionFactory.defineCommand(request);
        page = command.execute(request);
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getProperty("path.page.index");
            request.getSession().setAttribute("nullPage",
                    MessageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
