package by.epam.project.command;

/**
 * Use for servlet navigation
 */
public class Router {
    private String page;
    private Type type = Type.FORWARD;

    public Router(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Type getType() {
        return type;
    }

    public void useRedirect() {
        this.type = Type.REDIRECT;
    }

    public enum Type {
        FORWARD,
        REDIRECT
    }
}
