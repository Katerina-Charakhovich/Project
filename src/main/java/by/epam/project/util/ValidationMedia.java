package by.epam.project.util;

import by.epam.project.command.CommandUtil;


/**
 * Class for movie validation
 */
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ValidationMedia getInstance() {
        return instance;
    }

    /**
     * Is right link boolean.
     *
     * @param link the link
     * @return the boolean
     */
    public boolean isRightLink(String link) {
        return link.matches(LINK_EXPRESSION);
    }

    /**
     * Is right film name en boolean.
     *
     * @param filmName the film name
     * @return the boolean
     */
    public boolean isRightFilmNameEn(String filmName) {
        return filmName.matches(FILM_NAME_EN_EXPRESSION);
    }

    /**
     * Is right film name ru boolean.
     *
     * @param filmName the film name
     * @return the boolean
     */
    public boolean isRightFilmNameRu(String filmName) {
        return filmName.matches(FILM_NAME_RU_EXPRESSION);
    }

    /**
     * Is right year of creation boolean.
     *
     * @param currentYearOfCreation the current year of creation
     * @return the boolean
     */
    public boolean isRightYearOfCreation(String currentYearOfCreation) {
        return currentYearOfCreation.matches(FILM_YEAR_OF_CREATION_EXPRESSION);
    }

    /**
     * Is right description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public boolean isRightDescription(String description) {
        return description.length() < MAX_LENGTH_DESCRIPTION;
    }

    /**
     * Is right genre en boolean.
     *
     * @param genre the genre
     * @return the boolean
     */
    public boolean isRightGenreEn(String genre) {
        return genre.matches(FILM_GENRE_EN_EXPRESSION);
    }

    /**
     * Is right genre ru boolean.
     *
     * @param genre the genre
     * @return the boolean
     */
    public boolean isRightGenreRu(String genre) {
        return genre.matches(FILM_GENRE_RU_EXPRESSION);
    }

    /**
     * Validate link boolean.
     *
     * @param linkEn the link en
     * @return the boolean
     */
    public boolean validateLink(String linkEn) {
        if (CommandUtil.hasValue(linkEn)) {
            return isRightLink(linkEn);
        }
        return true;
    }

    /**
     * Validate year boolean.
     *
     * @param currentYearOfCreation the current year of creation
     * @return the boolean
     */
    public boolean validateYear(String currentYearOfCreation) {
        if (CommandUtil.hasValue(currentYearOfCreation)) {
            return isRightYearOfCreation(currentYearOfCreation);
        }
        return true;
    }

    /**
     * Validate name en boolean.
     *
     * @param filmName the film name
     * @return the boolean
     */
    public boolean validateNameEn(String filmName) {
        return CommandUtil.hasValue(filmName) && isRightFilmNameEn(filmName);
    }

    /**
     * Validate genre en boolean.
     *
     * @param genreEn the genre en
     * @return the boolean
     */
    public boolean validateGenreEn(String genreEn) {
        if (CommandUtil.hasValue(genreEn)) {
            return isRightGenreEn(genreEn);
        }
        return true;
    }

    /**
     * Validate name ru boolean.
     *
     * @param filmName the film name
     * @return the boolean
     */
    public boolean validateNameRu(String filmName) {
        return !CommandUtil.hasValue(filmName) || isRightFilmNameRu(filmName);
    }

    /**
     * Validate genre ru boolean.
     *
     * @param genreRu the genre ru
     * @return the boolean
     */
    public boolean validateGenreRu(String genreRu) {
        if (CommandUtil.hasValue(genreRu)) {
            return isRightGenreRu(genreRu);
        }
        return true;
    }

    /**
     * Validate description boolean.
     *
     * @param descriptionEn the description en
     * @return the boolean
     */
    public boolean validateDescription(String descriptionEn) {
        if (CommandUtil.hasValue(descriptionEn)) {
            return isRightDescription(descriptionEn);
        }
        return true;
    }

}
