package by.epam.project.command;

import by.epam.project.util.ValidationMedia;

import javax.servlet.http.HttpServletRequest;

public class CommandUtil {

    public static boolean hasValue(String value) {
        return value != null && !value.isEmpty();
    }

    public static Router validateCommonFilmFields(HttpServletRequest request, String descriptionEn, String genreEn,
                                            String linkEn, String currentYearOfCreation, String filmName, String page, String filmLang) {
        ValidationMedia validationMedia = ValidationMedia.getInstance();
        if (!validationMedia.validateDescription(descriptionEn)) {
            return errorResponse(request, "Film.Description", page);
        }

        if (!validationMedia.validateLink(linkEn)) {
            return errorResponse(request, "Film.link", page);
        }
        if (!validationMedia.validateYear(currentYearOfCreation)) {
            return errorResponse(request, "Film.YearOfCreation", page);
        }

        return validateNameGenre(request, genreEn, filmName, page, filmLang);
    }

    private static Router validateNameGenre(HttpServletRequest request, String genreEn, String filmName, String page, String filmLang) {
        ValidationMedia validationMedia = ValidationMedia.getInstance();
        if (filmLang.equals("en")) {
            if (!validationMedia.validateGenreEn(genreEn)) {
                return errorResponse(request, "Film.genre", page);
            }

            if (!validationMedia.validateNameEn(filmName)) {
                return errorResponse(request, "Label.FilmName", page);
            }
        } else {
            if (!validationMedia.validateGenreRu(genreEn)) {
                return errorResponse(request, "Film.genre", page);
            }

            if (!validationMedia.validateNameRu(filmName)) {
                return errorResponse(request, "Label.FilmName", page);
            }
        }
        return null;
    }

    private static Router errorResponse(HttpServletRequest request, String field, String page) {
        request.setAttribute(RequestAttribute.ERROR_DATA, true);
        request.setAttribute("field", field);
        return new Router(page);
    }
}
