package by.epam.project.dao;

/**
 * Class that helps you resolve the correct localization of the movie
 */
public class LocalizationHelper {

    /**
     * Build localized column string.
     *
     * @param columnName the column name
     * @param language   the language
     * @return the string
     */
    public static String buildLocalizedColumn(String columnName, String language) {
        return columnName.concat(language);
    }

    private LocalizationHelper() {
    }
}
