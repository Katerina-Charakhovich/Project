package by.epam.project.command;

import by.epam.project.util.ValidationMedia;

import javax.servlet.http.HttpServletRequest;

import static by.epam.project.command.RequestAttribute.*;


/**
 * The type Command util.
 */
public class CommandUtil {

    private CommandUtil() {
    }

    /**
     * Has value boolean.
     *
     * @param value the value
     * @return the boolean
     */
    public static boolean hasValue(String value) {
        return value != null && !value.isEmpty();
    }

    /**
     * Validate common film fields router.
     *
     * @param request               the request
     * @param descriptionEn         the description en
     * @param genreEn               the genre en
     * @param linkEn                the link en
     * @param currentYearOfCreation the current year of creation
     * @param filmName              the film name
     * @param page                  the page
     * @param filmLang              the film lang
     * @return the router
     */
    public static Router validateCommonFilmFields(HttpServletRequest request, String descriptionEn, String genreEn,
                                                  String linkEn, String currentYearOfCreation, String filmName, String page, String filmLang) {
        ValidationMedia validationMedia = ValidationMedia.getInstance();
        if (!validationMedia.validateDescription(descriptionEn)) {
            return errorResponse(request, LABEL_FOR_FILM_DESCRIPTION, page);
        }
        if (!validationMedia.validateLink(linkEn)) {
            return errorResponse(request, LABEL_FOR_FILM_LINK, page);
        }
        if (!validationMedia.validateYear(currentYearOfCreation)) {
            return errorResponse(request, LABEL_FOR_FILM_YEAR_OF_CREATION, page);
        }

        return validateNameGenre(request, genreEn, filmName, page, filmLang);
    }

    private static Router validateNameGenre(HttpServletRequest request, String genreEn, String filmName, String page, String filmLang) {
        ValidationMedia validationMedia = ValidationMedia.getInstance();
        if (filmLang.equals(EN)) {
            if (!validationMedia.validateGenreEn(genreEn)) {
                return errorResponse(request, LABEL_FOR_FILM_GENRE, page);
            }
            if (!validationMedia.validateNameEn(filmName)) {
                return errorResponse(request, LABEL_FOR_FILM_NAME, page);
            }
        } else {
            if (!validationMedia.validateGenreRu(genreEn)) {
                return errorResponse(request, LABEL_FOR_FILM_GENRE, page);
            }
            if (!validationMedia.validateNameRu(filmName)) {
                return errorResponse(request, LABEL_FOR_FILM_NAME, page);
            }
        }
        return null;
    }

    private static Router errorResponse(HttpServletRequest request, String field, String page) {
        request.setAttribute(RequestAttribute.ERROR_DATA, true);
        request.setAttribute(LABEL_FOR_FILM_FIELD, field);
        return new Router(page);
    }

    /**
     * Calculate table parameter string.
     *
     * @param request       the request
     * @param parameterName the parameter name
     * @param defaultValue  the default value
     * @return the string
     */
    public static String calculateTableParameter(HttpServletRequest request, String parameterName, String defaultValue) {
        String current = request.getParameter(parameterName);
        if (current == null || current.isEmpty()) {
            Object currentPage = request.getSession().getAttribute(parameterName);
            if (currentPage != null) {
                current = currentPage.toString();
            } else {
                current = defaultValue;
            }
        }
        return current;
    }
}
