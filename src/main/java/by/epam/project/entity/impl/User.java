package by.epam.project.entity.impl;
import by.epam.project.entity.Entity;


public class User implements Entity {
    long userId;
    private String email;
    private UserRole userRole;
    private String userGender;
    private String country;
    private String aboutMe;
    private String avatar;
    private String name;
    private boolean locked;

    public User(long userId, String email, String role) {
        this.userId = userId;
        this.email = email;
        this.userRole = UserRole.valueOf(role);
    }

    public User() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(String role) {
        this.userRole = UserRole.valueOf(role);
    }


    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getUserId() != user.getUserId()) return false;
        if (isLocked() != user.isLocked()) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getUserRole() != user.getUserRole()) return false;
        if (getUserGender() != user.getUserGender()) return false;
        if (getCountry() != null ? !getCountry().equals(user.getCountry()) : user.getCountry() != null) return false;
        if (getAboutMe() != null ? !getAboutMe().equals(user.getAboutMe()) : user.getAboutMe() != null) return false;
        if (getAvatar() != null ? !getAvatar().equals(user.getAvatar()) : user.getAvatar() != null) return false;
        return getName() != null ? getName().equals(user.getName()) : user.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getUserRole() != null ? getUserRole().hashCode() : 0);
        result = 31 * result + (getUserGender() != null ? getUserGender().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getAboutMe() != null ? getAboutMe().hashCode() : 0);
        result = 31 * result + (getAvatar() != null ? getAvatar().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (isLocked() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append("User{").append("Email='").append(email).append('\'')
                .append(", userRole='").append(userRole).append('\'').append(", userGender='")
                .append(userGender).append('\'').append(", country='").append(country)
                .append('\'').append(", aboutMe='")
                .append(aboutMe).append('\'').append(", avatar='")
                .append(avatar).append('\'').append(", name='")
                .append(name).append('\'').append(", locked='")
                .append(locked).append('\'').append('}').toString();
    }
    public enum UserRole {
        USER,
        ADMIN;
    }
}
