package by.epam.project.tag;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Display current day in footer (every page)
 */
@SuppressWarnings("serial")
public class CurrentDayTag extends TagSupport {
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public int doStartTag() {
        try {
            LocalDate today = LocalDate.now();
            pageContext.getOut().write("<h4>" + today + "</h4>");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "CurrentDayTag", e);
        }
        return SKIP_BODY;
    }
}
