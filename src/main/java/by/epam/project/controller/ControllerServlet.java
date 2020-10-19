package by.epam.project.controller;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.manager.ConfigurationManager;
import by.epam.project.command.manager.MessageManager;
import by.epam.project.command.factory.ActionFactory;
import by.epam.project.entity.Router;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.pool.exception.PoolException;
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


@WebServlet(urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, e);


        }
    }

    private void processRequest(HttpServletRequest request
            , HttpServletResponse response) throws ServletException, IOException, CommandException {
        Command command = ActionFactory.defineCommand(request);
        Router router = command.execute(request);
        request.getSession().setAttribute("currentPage", router.getPage());
        if (router.getPage() == null){
            router.setPage(ConfigurationManager.getProperty("path.page.index"));
            request.getSession().setAttribute("nullPage",
                    MessageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + router.getPage());
        }
        if (Router.Type.FORWARD.equals(router.getType())) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(router.getPage());
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + router.getPage());
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (PoolException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
