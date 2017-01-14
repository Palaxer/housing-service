package org.palax.entity;

/**
 * The {@code Brigade} class is a information about brigade
 *
 * @author Taras Palashynskyy
 */

public class Brigade {
    /**The value is used for store {@code Brigade} id. */
    private Long brigadeId;
    /**The value is used for store {@code Brigade} name. */
    private String brigadeName;
    /**The value is used for store {@code Brigade} work type which represent by {@link WorkType}. */
    private WorkType workType;

    /**
     * Returns {@code Brigade} id which will be unique
     *
     * @return {@code brigadeId} value
     */
    public Long getBrigadeId() {
        return brigadeId;
    }

    /**
     * Sets {@code Brigade} id which must be unique
     *
     * @param brigadeId the unique {@code brigadeId}
     */
    public void setBrigadeId(Long brigadeId) {
        this.brigadeId = brigadeId;
    }

    /**
     * Returns {@code Brigade} name
     *
     * @return {@code brigadeName} value
     */
    public String getBrigadeName() {
        return brigadeName;
    }

    /**
     * Sets {@code Brigade} name
     *
     * @param brigadeName {@code brigadeName} value
     */
    public void setBrigadeName(String brigadeName) {
        this.brigadeName = brigadeName;
    }

    /**
     * Returns {@code Brigade} work type represent by {@link WorkType}
     *
     * @return {@code workType} that {@code Brigade} can perform
     */
    public WorkType getWorkType() {
        return workType;
    }

    /**
     * Sets {@code Brigade} work type represent by {@link WorkType}
     *
     * @param workType {@code workType} that {@code Brigade} can perform
     */
    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    /**
     * Compares two {@code Brigade} for equality.
     *
     * @param o the {@code Object} value to compare with
     * @return {@code true} if the given {@code Object} is an instance
     *         of a {@code Brigade} that is equal to this {@code Brigade} object;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brigade brigade = (Brigade) o;

        if (brigadeId != null ? !brigadeId.equals(brigade.brigadeId) : brigade.brigadeId != null) return false;
        if (brigadeName != null ? !brigadeName.equals(brigade.brigadeName) : brigade.brigadeName != null) return false;
        return workType != null ? workType.equals(brigade.workType) : brigade.workType == null;
    }

    /**
     * The {@code hashCode} method uses for calculate {@code Brigade} hash code.
     *
     * @return hash code of the {@code Brigade}
     */
    @Override
    public int hashCode() {
        int result = brigadeId != null ? brigadeId.hashCode() : 0;
        result = 31 * result + (brigadeName != null ? brigadeName.hashCode() : 0);
        result = 31 * result + (workType != null ? workType.hashCode() : 0);
        return result;
    }

    /**
     * Formats a {@code Brigade} in the following format
     * <code>Brigade{brigadeId, brigadeName, workType}</code>
     *
     * @return a <code>String</code> object which represent {@code Brigade}
     */
    @Override
    public String toString() {
        return "Brigade{" +
                "brigadeId=" + brigadeId +
                ", brigadeName='" + brigadeName + '\'' +
                ", workType=" + workType +
                '}';
    }
}
