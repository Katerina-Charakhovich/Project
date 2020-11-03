package by.epam.project.util;

import by.epam.project.command.CommandUtil;


public class ValidationMedia {
    private static ValidationMedia instance = new ValidationMedia();
    private static final String LINK_EXPRESSION = "(https?://)?(www\\.)?(yotu\\.be/|youtube\\.com/)?((.+/)?(watch(\\?v=|.+&v=))?(v=)?)([\\w_-]{11})(&.+)?";
    private static final String FILM_NAME_EN_EXPRESSION = "^[a-zA-Z0-9\\s?!,.:'\\-]+$";
    private static final String FILM_NAME_RU_EXPRESSION = "^[а-яА-ЯёЁ0-9\\s?!,.:'\\-]+$";
    private static final String FILM_YEAR_OF_CREATION_EXPRESSION = "^\\d{0,4}$";
    private static final int MAX_LENGTH_DESCRIPTION = 1000;
    private static final String FILM_GENRE_EN_EXPRESSION = "^[a-zA-Z0-9\\s?!,.:'\\-]+$";
    private static final String FILM_GENRE_RU_EXPRESSION = "^[а-яА-ЯёЁ0-9\\s?!,.:'\\-]+$";

    private ValidationMedia() {

    }

    public static ValidationMedia getInstance() {
        return instance;
    }

    public boolean isRightLink(String link) {
        return link.matches(LINK_EXPRESSION);
    }

    public boolean isRightFilmNameEn(String filmName) {
        return filmName.matches(FILM_NAME_EN_EXPRESSION);
    }

    public boolean isRightFilmNameRu(String filmName) {
        return filmName.matches(FILM_NAME_RU_EXPRESSION);
    }

    public boolean isRightYearOfCreation(String currentYearOfCreation) {
        return currentYearOfCreation.matches(FILM_YEAR_OF_CREATION_EXPRESSION);
    }

    public boolean isRightDescription(String description) {
        return description.length() < MAX_LENGTH_DESCRIPTION;
    }

    public boolean isRightGenreEn(String genre) {
        return genre.matches(FILM_GENRE_EN_EXPRESSION);
    }

    public boolean isRightGenreRu(String genre) {
        return genre.matches(FILM_GENRE_RU_EXPRESSION);
    }

    public boolean validateLink(String linkEn) {
        if (CommandUtil.hasValue(linkEn)) {
            return isRightLink(linkEn);
        }
        return true;
    }

    public boolean validateYear(String currentYearOfCreation) {
        if (CommandUtil.hasValue(currentYearOfCreation)) {
            return isRightYearOfCreation(currentYearOfCreation);
        }
        return true;
    }

    public boolean validateNameEn(String filmName) {
        return CommandUtil.hasValue(filmName) && isRightFilmNameEn(filmName);
    }

    public boolean validateGenreEn(String genreEn) {
        if (CommandUtil.hasValue(genreEn)) {
            return isRightGenreEn(genreEn);
        }
        return true;
    }

    public boolean validateNameRu(String filmName) {
        return CommandUtil.hasValue(filmName) && isRightFilmNameRu(filmName);
    }

    public boolean validateGenreRu(String genreRu) {
        if (CommandUtil.hasValue(genreRu)) {
            return isRightGenreRu(genreRu);
        }
        return true;
    }

    public boolean validateDescription(String descriptionEn) {
        if (CommandUtil.hasValue(descriptionEn)) {
            return isRightDescription(descriptionEn);
        }
        return true;
    }

}
