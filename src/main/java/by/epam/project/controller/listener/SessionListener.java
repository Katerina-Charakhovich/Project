package by.epam.project.controller.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;


@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * The type Session listener.
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
        LOGGER.log(Level.INFO, "remove: " + event.getClass().getSimpleName() + " : " + event.getName()
                + " : " + event.getValue());
    }

    public void attributeAdded(HttpSessionBindingEvent event) {
        LOGGER.log(Level.INFO, "add: " + event.getClass().getSimpleName() + " : " + event.getName()
                + " : " + event.getValue());
    }

    public void attributeReplaced(HttpSessionBindingEvent event) {

        LOGGER.log(Level.INFO, "replace: " + event.getClass().getSimpleName() + " : " + event.getName()
                + " : " + event.getValue());
    }
}
