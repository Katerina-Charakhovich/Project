package by.epam.project.entity;

import java.io.Serializable;

public class Entity implements Serializable, Cloneable {
    private long id;
    private int deleted;

    public Entity( int deleted) {
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        if (getId() != entity.getId()) return false;
        return getDeleted() == entity.getDeleted();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getDeleted();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("Entity{").append("id=").append(id).append(", deleted=")
                .append(deleted).append('}').toString();
    }
}
