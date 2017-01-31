package org.palax.dao.util;

import org.palax.entity.Brigade;
import org.palax.entity.Role;
import org.palax.entity.User;

/**
 * {@code UserBuilder} class for building test data to {@link User} entity
 */
public class UserBuilder implements Builder<User> {

    private User user;

    private UserBuilder() {
        user = new User();
    }

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    public UserBuilder constructUser(Long template, Builder<Role> roleBuilder, Builder<Brigade> brigadeBuilder) {
        if(template != null) {
            user.setUserId(template);
            user.setLogin("login" + template);
            user.setPassword("passwd" + template);
            user.setFirstName("first_name" + template);
            user.setLastName("last_name" + template);
            user.setStreet("str" + template);
            user.setHouseNumber(template.toString());
            user.setApartment(template);
            user.setCity("city" + template);
            user.setPhoneNumber("093-722-939" + template);
        }
        user.setRole(roleBuilder != null ? roleBuilder.build() : null);
        user.setBrigade(brigadeBuilder != null ? brigadeBuilder.build() : null);

        return this;
    }

    @Override
    public User build() {
        return user;
    }
}
