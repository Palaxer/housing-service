package org.palax.entity;

/**
 * The {@code Role} class is a information about user role
 *
 * @author Taras Palashynskyy
 */

public class Role {
    /**The value is used for store {@code Role} id. */
    private Long roleId;
    /**The value is used for store {@code Role} type */
    private String roleType;

    /**
     * Returns {@code Role} id which will be unique
     *
     * @return {@code roleId} value
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * Sets {@code Role} id which must be unique
     *
     * @param roleId the unique {@code roleId}
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * Returns {@code Role} type
     *
     * @return {@code roleType} value
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * Sets {@code Role} type
     *
     * @param roleType {@code roleType} value
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * Compares two {@code Role} for equality.
     *
     * @param o the {@code Object} value to compare with
     * @return {@code true} if the given {@code Object} is an instance
     *         of a {@code Role} that is equal to this {@code Role} object;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (roleId != null ? !roleId.equals(role.roleId) : role.roleId != null) return false;
        return roleType != null ? roleType.equals(role.roleType) : role.roleType == null;
    }

    /**
     * The {@code hashCode} method uses for calculate {@code Role} hash code.
     *
     * @return hash code of the {@code Role}
     */
    @Override
    public int hashCode() {
        int result = roleId != null ? roleId.hashCode() : 0;
        result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
        return result;
    }

    /**
     * Formats a {@code Role} in the following format
     * <code>Role{roleId, roleType}</code>
     *
     * @return a <code>String</code> object which represent {@code Role}
     */
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleType='" + roleType + '\'' +
                '}';
    }
}
