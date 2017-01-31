package org.palax.dao.util;

import org.palax.entity.Role;

/**
 * {@code RoleBuilder} class for building test data to {@link Role} entity
 */
public class RoleBuilder implements Builder<Role> {

    private Role role;

    private RoleBuilder() {
        role = new Role();
    }

    public static RoleBuilder getBuilder() {
        return new RoleBuilder();
    }

    public RoleBuilder constructRole(Long template) {
        if(template != null) {
            role.setRoleId(template);
            role.setRoleType("role" + template);
        }

        return this;
    }

    @Override
    public Role build() {
        return role;
    }
}
