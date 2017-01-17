package org.palax.dao;

import org.palax.entity.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The {@code DataGenerator} is a class which used for entity generation
 *
 * @author Taras Palashynskyy
 */

class DataGenerator {

    /**
     * Generate {@link Role} instance with the filled all field
     *
     * @param template {@code template} will be set in all fields
     * @return fill {@link Role} instance
     */
    static Role generateRole(long template) {
        Role role = new Role();
        role.setRoleId(template);
        role.setRoleType("role" + template);

        return role;
    }

    /**
     * Generate {@link WorkType} instance with the filled all field
     *
     * @param template {@code template} will be set in all fields
     * @return fill {@link WorkType} instance
     */
    static WorkType generateWorkType(long template) {
        WorkType workType = new WorkType();
        workType.setWorkTypeId(template);
        workType.setTypeName("work type" + template);

        return workType;
    }

    /**
     * Generate {@link Brigade} instance with the filled all field
     *
     * @param template {@code template} will be set in all fields
     * @return fill {@link Brigade} instance
     */
    static Brigade generateBrigade(long template) {
        Brigade brigade = new Brigade();
        brigade.setBrigadeId(template);
        brigade.setBrigadeName("brigade" + template);
        brigade.setWorkType(DataGenerator.generateWorkType(template));

        return brigade;
    }

    /**
     * Generate {@link User} instance with the filled all field
     *
     * @param template {@code template} will be set in all fields
     * @return fill {@link User} instance
     */
    static User generateUser(long template) {
        User user = new User();
        user.setUserId(template);
        user.setLogin("login" + template);
        user.setPassword("passwd" + template);
        user.setRole(DataGenerator.generateRole(template));
        user.setFirstName("first name" + template);
        user.setLastName("last name" + template);
        user.setPosition("position" + template);
        user.setBrigade(DataGenerator.generateBrigade(template));
        user.setStreet("street" + template);
        user.setHouseNumber("1/" + template);
        user.setApartment(template);
        user.setCity("city" + template);
        user.setPhoneNumber("093-722-939" + template);

        return user;
    }

    /**
     * Generate {@link Bid} instance with the filled all field
     *
     * @param template {@code template} will be set in all fields
     * @return fill {@link Bid} instance
     */
    static Bid generateBid(long template) {
        Bid bid = new Bid();

        bid.setBidId(template);
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.now()));
        bid.setDescription("desc" + template);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.now()));
        bid.setStatus("status" + template);
        bid.setUserTenant(DataGenerator.generateUser(template));
        bid.setWorkScope(template % 10);
        bid.setWorkType(DataGenerator.generateWorkType(template));

        return bid;
    }

    /**
     * Generate {@link WorkPlane} instance with the filled all field
     *
     * @param template {@code template} will be set in all fields
     * @return fill {@link WorkPlane} instance
     */
    static WorkPlane generateWorkPlane(long template) {
        WorkPlane workPlane = new WorkPlane();

        workPlane.setBrigade(DataGenerator.generateBrigade(template));
        workPlane.setUserAdvisor(DataGenerator.generateUser(template));
        workPlane.setStatus("status" + template);
        workPlane.setBid(DataGenerator.generateBid(template));
        workPlane.setWorkPlaneId(template);
        workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.now()));
        workPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.now()));

        return workPlane;
    }
}
