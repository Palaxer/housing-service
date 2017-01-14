package org.palax.entity;

import java.sql.Timestamp;

/**
 * The {@code Bid} class represents a request which gave the tenant
 *
 * @author Taras Palashynskyy
 */

public class Bid {
    /**The value is used for store {@code Bid} id. */
    private Long bidId;
    /**The value is used for store {@code Bid} work scope. */
    private Long workScope;
    /**The value is used for store {@code Bid} desired lead time works represents by {@link Timestamp}.  */
    private Timestamp leadTime;
    /**The value is used for store {@code Bid} status */
    private String status;
    /**The value is used for store {@code Bid} {@link WorkType} */
    private WorkType workType;
    /**The value is used for store {@code Bid} tenant who created the {@code Bid} represent by {@link User} */
    private User userTenant;
    /**The value is used for store {@code Bid} description */
    private String description;
    /**The value is used for store time of the {@code Bid} registration represents by {@link Timestamp} */
    private Timestamp bidTime;

    /**
     * Returns {@code Bid} id which will be unique
     *
     * @return {@code bidId} value
     */
    public Long getBidId() {
        return bidId;
    }

    /**
     * Sets {@code Bid} id which must be unique
     *
     * @param bidId the unique {@code bidId}
     */
    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    /**
     * Returns {@code Bid} work scope
     *
     * @return {@code workScope} value which must be from 1 to 10
     */
    public Long getWorkScope() {
        return workScope;
    }

    /**
     * Sets {@code Bid} workScope
     *
     * @param workScope {@code workScope} which must be from 1 to 10
     */
    public void setWorkScope(Long workScope) {
        this.workScope = workScope;
    }

    /**
     * Returns {@code Bid} desired lead time works represents by {@link Timestamp}
     *
     * @return a {@code leadTime} which is {@link Timestamp} object
     */
    public Timestamp getLeadTime() {
        return leadTime;
    }

    /**
     * Sets {@code Bid} lead time
     *
     * @param leadTime {@code workScope} which must be a {@link Timestamp} object
     */
    public void setLeadTime(Timestamp leadTime) {
        this.leadTime = leadTime;
    }

    /**
     * Returns {@code Bid} status
     *
     * @return {@code status} value
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets {@code Bid} status
     *
     * @param status {@code status} which represent {@code Bid} status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns {@code Bid} work type which represent by {@link WorkType}
     *
     * @return {@code workType} which represent by {@link WorkType}
     */
    public WorkType getWorkType() {
        return workType;
    }

    /**
     * Sets {@code Bid} work type which represent by {@link WorkType}
     *
     * @param workType {@code workType} which represent by {@link WorkType}
     */
    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    /**
     * Returns {@code Bid} tenant who created the {@code Bid} represent by {@link User}
     *
     * @return {@code userTenant} represent by {@link User}
     */
    public User getUserTenant() {
        return userTenant;
    }

    /**
     * Sets {@code Bid} tenant who created the {@code Bid} represent by {@link User}
     *
     * @param userTenant {@code userTenant} which represent by {@link User}
     */
    public void setUserTenant(User userTenant) {
        this.userTenant = userTenant;
    }

    /**
     * Returns {@code Bid} description
     *
     * @return {@code description} value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets {@code Bid} description
     *
     * @param description {@code description} which describe {@code Bid}
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns {@code Bid} creation time represents by {@link Timestamp}
     *
     * @return {@code bidTime} is a creation time represents by {@link Timestamp}
     */
    public Timestamp getBidTime() {
        return bidTime;
    }

    /**
     * Sets {@code Bid} creation time represents by {@link Timestamp}
     *
     * @param bidTime {@code bidTime} is a creation time represents by {@link Timestamp}
     */
    public void setBidTime(Timestamp bidTime) {
        this.bidTime = bidTime;
    }

    /**
     * Compares two {@code Bid} for equality.
     *
     * @param o the {@code Object} value to compare with
     * @return {@code true} if the given {@code Object} is an instance
     *         of a {@code Bid} that is equal to this {@code Bid} object;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        if (bidId != null ? !bidId.equals(bid.bidId) : bid.bidId != null) return false;
        if (workScope != null ? !workScope.equals(bid.workScope) : bid.workScope != null) return false;
        if (leadTime != null ? !leadTime.equals(bid.leadTime) : bid.leadTime != null) return false;
        if (status != null ? !status.equals(bid.status) : bid.status != null) return false;
        if (workType != null ? !workType.equals(bid.workType) : bid.workType != null) return false;
        if (userTenant != null ? !userTenant.equals(bid.userTenant) : bid.userTenant != null) return false;
        if (description != null ? !description.equals(bid.description) : bid.description != null) return false;
        return bidTime != null ? bidTime.equals(bid.bidTime) : bid.bidTime == null;
    }

    /**
     * The {@code hashCode} method uses for calculate {@code Bid} hash code.
     *
     * @return hash code of the {@code Bid}
     */
    @Override
    public int hashCode() {
        int result = bidId != null ? bidId.hashCode() : 0;
        result = 31 * result + (workScope != null ? workScope.hashCode() : 0);
        result = 31 * result + (leadTime != null ? leadTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (workType != null ? workType.hashCode() : 0);
        result = 31 * result + (userTenant != null ? userTenant.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (bidTime != null ? bidTime.hashCode() : 0);
        return result;
    }

    /**
     * Formats a {@code Bid} in the following format
     * <code>Bid{bidId, workScope, leadTime, status, workType, userTenant, description, bidTime}</code>
     *
     * @return a <code>String</code> object which represent {@code Bid}
     */
    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", workScope=" + workScope +
                ", leadTime=" + leadTime +
                ", status='" + status + '\'' +
                ", workType=" + workType +
                ", userTenant=" + userTenant +
                ", description='" + description + '\'' +
                ", bidTime=" + bidTime +
                '}';
    }
}
