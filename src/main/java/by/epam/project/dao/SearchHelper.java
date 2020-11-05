package by.epam.project.dao;

/**
 * The type Search helper.
 */
public class SearchHelper {
    private static final String SYMBOL_FOR_CONCAT = "%";

    /**
     * Build appeal for data base string.
     *
     * @param content the content
     * @return the string
     */
    public static String buildAppealForDataBase(String content) {
        return SYMBOL_FOR_CONCAT.concat(content).concat(SYMBOL_FOR_CONCAT);
    }

    private SearchHelper() {
    }
}
