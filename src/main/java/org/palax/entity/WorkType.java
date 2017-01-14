package org.palax.entity;

/**
 * The {@code WorkType} class is a information about {@link Bid} and {@link Brigade} work type
 *
 * @author Taras Palashynskyy
 */

public class WorkType {
    /**The value is used for store {@code WorkType} id. */
    private Long workTypeId;
    /**The value is used for store {@code WorkType} name. */
    private String typeName;

    /**
     * Returns {@code WorkType} id which will be unique
     *
     * @return {@code workTypeId} value
     */
    public Long getWorkTypeId() {
        return workTypeId;
    }

    /**
     * Sets {@code WorkType} id which must be unique
     *
     * @param workTypeId the unique {@code workTypeId}
     */
    public void setWorkTypeId(Long workTypeId) {
        this.workTypeId = workTypeId;
    }

    /**
     * Returns {@code WorkType} name
     *
     * @return {@code typeName} value
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets {@code WorkType} name
     *
     * @param typeName {@code typeName} value
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Compares two {@code WorkType} for equality.
     *
     * @param o the {@code Object} value to compare with
     * @return {@code true} if the given {@code Object} is an instance
     *         of a {@code WorkType} that is equal to this {@code WorkType} object;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkType workType = (WorkType) o;

        if (workTypeId != null ? !workTypeId.equals(workType.workTypeId) : workType.workTypeId != null) return false;
        return typeName != null ? typeName.equals(workType.typeName) : workType.typeName == null;
    }

    /**
     * The {@code hashCode} method uses for calculate {@code WorkType} hash code.
     *
     * @return hash code of the {@code WorkType}
     */
    @Override
    public int hashCode() {
        int result = workTypeId != null ? workTypeId.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        return result;
    }

    /**
     * Formats a {@code WorkType} in the following format
     * <code>WorkType{workTypeId, typeName}</code>
     *
     * @return a <code>String</code> object which represent {@code WorkType}
     */
    @Override
    public String toString() {
        return "WorkType{" +
                "workTypeId=" + workTypeId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
