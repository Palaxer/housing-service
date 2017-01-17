package org.palax.service;

import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.User;

import java.util.List;
import java.util.regex.Pattern;

/**
 * The {@code BidService} service is a convenient API for working with the {@link org.palax.dao.UserDao}
 *
 * @author Taras Palashynskyy
 */

public class UserService {

    private UserService() {

    }

    /**
     * Method to create {@link User}
     *
     * @param user this {@code user} will be created
     * @param role {@code user} will be created with this {@code role}
     * @return returns {@code true} if {@link User} add success
     *         or else {@code false}
     */
    public static boolean createUser(User user, String role){

        user.setRole(MySQLDAOFactory.getRoleDao().getRoleByName(role));

        return MySQLDAOFactory.getUserDao().insertUser(user);
    }

    /**
     * Method returns {@link List} of {@link User} which find by {@link org.palax.entity.Brigade} {@code id}
     *
     * @param id it is a {@link org.palax.entity.Brigade} {@code id}
     * @return returns {@link List} of {@link User} which find by {@link org.palax.entity.Brigade} {@code id}
     */
    public static List<User> getUserByBrigadeId(Long id) {

        return MySQLDAOFactory.getUserDao().getAllUserByBrigadeId(id);
    }

    /**
     * Method to get all {@link User}
     *
     * @return return {@link List} of all {@link User}
     */
    public static List<User> getAllUser() {

        return MySQLDAOFactory.getUserDao().getAllUser();
    }

    /**
     * Method return {@link User} which find by {@code id}
     *
     * @param id it indicates an {@link User} {@code id} that you want return
     * @return return {@link User} by {@code id}
     */
    public static User getUserById(Long id) {

        return MySQLDAOFactory.getUserDao().getUserById(id);
    }

    /**
     * Method return {@link User} which find by {@code login}
     *
     * @param login it indicates an {@link User} {@code login} that you want return
     * @return return {@link User} by {@code login}
     */
    public static User getUserByLogin(String login) {

        return MySQLDAOFactory.getUserDao().getUserByLogin(login);
    }

    /**
     * Method update {@link User}
     *
     * @param user the {@code user} will update if it already exists
     * @return returns {@code true} if the {@code user} updated
     *         or else {@code false}
     */
    public static boolean updateUser(User user) {
        return MySQLDAOFactory.getUserDao().updateUser(user);
    }

    /**
     * Method duplicate {@link User} without changing reference
     * 
     * @param from the value will be copied from here
     * @param to the value will be copied to here
     */
    public static void userDuplicate(User from, User to) {
        to.setUserId(from.getUserId());
        to.setBrigade(from.getBrigade());
        to.setRole(from.getRole());
        to.setLogin(from.getLogin());
        to.setPosition(from.getPosition());
        to.setApartment(from.getApartment());
        to.setCity(from.getCity());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setHouseNumber(from.getHouseNumber());
        to.setPassword(from.getPassword());
        to.setStreet(from.getStreet());
        to.setPhoneNumber(from.getPhoneNumber());
    }

    /**
     * Method validate {@link User} {@code login} with next pattern {@code "[^\\s]{3,10}"}
     *
     * @param login validate value
     * @return returns {@code true} if login was valid
     *         or else {@code false}
     */
    public static boolean loginValid(String login) {
        return Pattern.matches("[^\\s]{3,10}", login);
    }

    /**
     * Method validate {@link User} {@code firstName} with next pattern {@code "[A-z,А-я]{1,25}"}
     *
     * @param firstName validate value
     * @return returns {@code true} if firstName was valid
     *         or else {@code false}
     */
    public static boolean firstNameValid(String firstName) {
        return Pattern.matches("[A-z,А-я]{1,25}", firstName);
    }

    /**
     * Method validate {@link User} {@code lastName} with next pattern {@code "[A-z,А-я]{1,30}-?[A-z,А-я]{1,30}"}
     *
     * @param lastName validate value
     * @return returns {@code true} if lastName was valid
     *         or else {@code false}
     */
    public static boolean lastNameValid(String lastName) {
        return Pattern.matches("[A-z,А-я]{1,30}-?[A-z,А-я]{1,30}", lastName);
    }

    /**
     * Method validate {@link User} {@code street} with next pattern
     * {@code "[A-z,А-я]{1,30}-?\.? ?[0-9]{0,4}[A-z,А-я]{0,30} ?[A-z,А-я]{0,30}|[A-z,А-я]{0,30}-??[0-9]{1,4}[A-z,А-я]{1,30}"}
     *
     * @param street validate value
     * @return returns {@code true} if street was valid
     *         or else {@code false}
     */
    public static boolean streetValid(String street) {
        return Pattern.matches("[A-z,А-я]{1,30}-?\\.? ?[0-9]{0,4}[A-z,А-я]{0,30} ?[A-z,А-я]{0,30}|[A-z,А-я]{0,30}-??[0-9]{1,4}[A-z,А-я]{1,30}", street);
    }

    /**
     * Method validate {@link User} {@code houseNumber} with next pattern
     * {@code "[\d]{1,4}/?[\d]{0,4}[A-z,А-я]{0,2}"}
     *
     * @param houseNumber validate value
     * @return returns {@code true} if houseNumber was valid
     *         or else {@code false}
     */
    public static boolean houseNumberValid(String houseNumber) {
        return Pattern.matches("[\\d]{1,4}/?[\\d]{0,4}[A-z,А-я]{0,2}", houseNumber);
    }

    /**
     * Method validate {@link User} {@code apartment}
     *
     * @param apartment validate value
     * @return returns {@code true} if apartment bigger then 1
     *         or else {@code false}
     */
    public static boolean apartmentValid(Long apartment) {
        return apartment != null && apartment >= 1L;
    }

    /**
     * Method validate {@link User} {@code city} with next pattern
     * {@code "[A-z,А-я]{1,30}-?[A-z,А-я]{1,30}"}
     *
     * @param city validate value
     * @return returns {@code true} if city was valid
     *         or else {@code false}
     */
    public static boolean cityValid(String city) {
        return Pattern.matches("[A-z,А-я]{1,30}-?[A-z,А-я]{1,30}", city);
    }

    /**
     * Method validate {@link User} {@code phoneNumber} with next pattern
     * {@code "[A-z,А-я]{1,30}-?[A-z,А-я]{1,30}"}
     *
     * @param phoneNumber validate value
     * @return returns {@code true} if phone number was valid
     *         or else {@code false}
     */
    public static boolean phoneNumberValid(String phoneNumber) {
        return Pattern.matches("(\\d{3}-){1,2}\\d{4}", phoneNumber);
    }
}
