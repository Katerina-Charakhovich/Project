package by.epam.project.controller.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class SimpleRequestListener implements ServletRequestListener {
    public static final Logger LOGGER = LogManager.getLogger();

    public void requestInitialized(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        String uri = "Request Initialized for " + request.getRequestURI();
        String id = "Request Initialized with ID=" + request.getRequestedSessionId();
        LOGGER.log(Level.INFO, uri + "\n" + id);
        ServletContext context = event.getServletContext();
        Integer reqCount = (Integer) request.getSession().getAttribute("counter");
        if (reqCount == null) {
            reqCount = 0;
        }
        LOGGER.log(Level.INFO, uri + "\n" + id + ", Request Counter =" + reqCount);
    }

    public void requestDestroyed(ServletRequestEvent event) {
        LOGGER.log(Level.INFO, "Request Destroyed: "
                + event.getServletRequest().getAttribute("lifecycle"));
    }
}
