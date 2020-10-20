package by.epam.project.entity;

public class Router {
    public enum Type {
        FORWARD,
        REDIRECT
    }

    private String page;
    private Type type;

    public Router(String page, Type type) {
        this.page = page;
        this.type = type;
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

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Router)) return false;

        Router router = (Router) o;

        if (getPage() != null ? !getPage().equals(router.getPage()) : router.getPage() != null) return false;
        return getType() == router.getType();
    }

    @Override
    public int hashCode() {
        int result = getPage() != null ? getPage().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("Router{").append("page='").append(page).append('\'').append(", type=")
                .append(type).append('}').toString();
    }
}
