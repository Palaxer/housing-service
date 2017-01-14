package org.palax.entity;

/**
 * The {@code User} class is represent a user information for all role
 *
 * @author Taras Palashynskyy
 */

public class User {
    /**The value is used for store {@code User} id. */
    private Long userId;
    /**The value is used for store {@code User} login. */
    private String login;
    /**The value is used for store {@code User} password. */
    private String password;
    /**The value is used for store {@code User} {@link Role}. */
    private Role role;
    /**The value is used for store {@code User} first name. */
    private String firstName;
    /**The value is used for store {@code User} last name. */
    private String lastName;
    /**The value is used for store {@code User} position. */
    private String position;
    /**The value is used for store {@code User} {@link Brigade}. */
    private Brigade brigade;
    /**The value is used for store {@code User} street. */
    private String street;
    /**The value is used for store {@code User} house number. */
    private String houseNumber;
    /**The value is used for store {@code User} apartment. */
    private Long apartment;
    /**The value is used for store {@code User} city. */
    private String city;

    /**
     * Returns {@code User} id which will be unique
     *
     * @return {@code userId} value
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets {@code User} id which must be unique
     *
     * @param userId the unique {@code userId}
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Returns {@code User} login
     *
     * @return user {@code login} value
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets {@code User} login
     *
     * @param login the unique user {@code login}
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Returns {@code User} password
     *
     * @return user {@code password} value
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets {@code User} password
     *
     * @param password user {@code password}
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns {@code User} {@link Role} which represent user type
     *
     * @return user {@code role} which represent by {@link Role}
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets {@code User} {@link Role} which represent user type
     *
     * @param role user {@code role} which represent by {@link Role}
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Returns {@code User} firstName
     *
     * @return user {@code firstName}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets {@code User} firstName
     *
     * @param firstName user {@code firstName}
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns {@code User} lastName
     *
     * @return user {@code lastName}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets {@code User} lastName
     *
     * @param lastName user {@code lastName}
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns {@code User} position
     *
     * @return user {@code position}
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets {@code User} position
     *
     * @param position user {@code position}
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Returns {@code User} {@link Brigade}
     *
     * @return user {@code brigade}
     */
    public Brigade getBrigade() {
        return brigade;
    }

    /**
     * Sets {@code User} {@link Brigade}
     *
     * @param brigade user {@code brigade}
     */
    public void setBrigade(Brigade brigade) {
        this.brigade = brigade;
    }

    /**
     * Returns {@code User} street where user live
     *
     * @return {@code street} where user live
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets {@code User} street where user live
     *
     * @param street {@code street} where user live
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Returns {@code User} house number where user live
     *
     * @return {@code houseNumber} where user live
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets {@code User} house number where user live
     *
     * @param houseNumber {@code houseNumber} where user live
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Returns {@code User} apartment where user live
     *
     * @return {@code apartment} where user live
     */
    public Long getApartment() {
        return apartment;
    }

    /**
     * Sets {@code User} apartment where user live
     *
     * @param apartment {@code apartment} where user live
     */
    public void setApartment(Long apartment) {
        this.apartment = apartment;
    }

    /**
     * Returns {@code User} city where user live
     *
     * @return {@code city} where user live
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets {@code User} city where user live
     *
     * @param city {@code city} where user live
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Compares two {@code User} for equality.
     *
     * @param o the {@code Object} value to compare with
     * @return {@code true} if the given {@code Object} is an instance
     *         of a {@code User} that is equal to this {@code User} object;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (position != null ? !position.equals(user.position) : user.position != null) return false;
        if (brigade != null ? !brigade.equals(user.brigade) : user.brigade != null) return false;
        if (street != null ? !street.equals(user.street) : user.street != null) return false;
        if (houseNumber != null ? !houseNumber.equals(user.houseNumber) : user.houseNumber != null) return false;
        if (apartment != null ? !apartment.equals(user.apartment) : user.apartment != null) return false;
        return city != null ? city.equals(user.city) : user.city == null;
    }

    /**
     * The {@code hashCode} method uses for calculate {@code User} hash code.
     *
     * @return hash code of the {@code User}
     */
    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (brigade != null ? brigade.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    /**
     * Formats a {@code User} in the following format
     * <code>User{userId, login, password, role, firstName, lastName, position, brigade,
     * street, houseNumber, apartment, city}</code>
     *
     * @return a <code>String</code> object which represent {@code User}
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", brigade=" + brigade +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", apartment=" + apartment +
                ", city='" + city + '\'' +
                '}';
    }
}
