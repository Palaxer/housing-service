package org.palax.entity;

import java.sql.Timestamp;

/**
 * The {@code WorkPlane} class is a all the information about the {@link Bid}
 * of the tenant represent by {@link User} and the {@link Brigade}
 * that will carry out a request to perform
 *
 * @author Taras Palashynskyy
 */

public class WorkPlane {
    /**The value is used for store {@code WorkPlane} id. */
    private Long workPlaneId;
    /**The value is used for store {@code WorkPlane} {@link Brigade}
     * which will perform {@link Bid} */
    private Brigade brigade;
    /**The value is used for store {@code WorkPlane} {@link Bid} */
    private Bid bid;
    /**The value is used for store {@code WorkPlane} status */
    private String status;
    /**The value is used for store {@code WorkPlane} advisor who process the {@code Bid} represent by {@link User} */
    private User userAdvisor;
    /**The value is used for store {@code WorkPlane} work time represents by {@link Timestamp}. */
    private Timestamp workTime;
    /**The value is used for store {@code WorkPlane} complete work time represents by {@link Timestamp}. */
    private Timestamp completeTime;

    /**
     * Returns {@code WorkPlane} id which will be unique
     *
     * @return {@code workPlaneId} value
     */
    public Long getWorkPlaneId() {
        return workPlaneId;
    }

    /**
     * Sets {@code WorkPlane} id which must be unique
     *
     * @param workPlaneId the unique {@code workPlaneId}
     */
    public void setWorkPlaneId(Long workPlaneId) {
        this.workPlaneId = workPlaneId;
    }

    /**
     * Returns {@code WorkPlane} {@link Brigade} which will perform {@link Bid}
     *
     * @return {@code brigade} value
     */
    public Brigade getBrigade() {
        return brigade;
    }

    /**
     * Sets {@code WorkPlane} {@link Brigade} which will perform {@link Bid}
     *
     * @param brigade {@link Brigade} which will perform {@link Bid}
     */
    public void setBrigade(Brigade brigade) {
        this.brigade = brigade;
    }

    /**
     * Returns {@code WorkPlane} {@link Bid}
     *
     * @return {@link Bid} value
     */
    public Bid getBid() {
        return bid;
    }

    /**
     * Sets {@code WorkPlane} {@link Bid}
     *
     * @param bid {@link Bid}
     */
    public void setBid(Bid bid) {
        this.bid = bid;
    }

    /**
     * Returns {@code WorkPlane} status
     *
     * @return {@code status} value
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets {@code WorkPlane} status
     *
     * @param status {@code status} which represent {@code WorkPlane} status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns {@code WorkPlane} advisor who process the {@code Bid} represent by {@link User}
     *
     * @return {@code userAdvisor} represent by {@link User}
     */
    public User getUserAdvisor() {
        return userAdvisor;
    }

    /**
     * Sets {@code WorkPlane} advisor who process the {@code Bid} represent by {@link User}
     *
     * @param userAdvisor {@code userAdvisor} represent by {@link User}
     */
    public void setUserAdvisor(User userAdvisor) {
        this.userAdvisor = userAdvisor;
    }

    /**
     * Returns {@code WorkPlane} work time represents by {@link Timestamp}
     *
     * @return {@code workTime} is a work time represents by {@link Timestamp}
     */
    public Timestamp getWorkTime() {
        return workTime;
    }

    /**
     * Sets {@code WorkPlane} work time represents by {@link Timestamp}
     *
     * @param workTime {@code workTime} is a work time represents by {@link Timestamp}
     */
    public void setWorkTime(Timestamp workTime) {
        this.workTime = workTime;
    }

    /**
     * Returns {@code WorkPlane} complete work time represents by {@link Timestamp}
     *
     * @return {@code completeTime} is a complete work time represents by {@link Timestamp}
     */
    public Timestamp getCompleteTime() {
        return completeTime;
    }

    /**
     * Sets {@code WorkPlane} complete work time represents by {@link Timestamp}
     *
     * @param completeTime {@code completeTime} is a complete work time represents by {@link Timestamp}
     */
    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * Compares two {@code WorkPlane} for equality.
     *
     * @param o the {@code Object} value to compare with
     * @return {@code true} if the given {@code Object} is an instance
     *         of a {@code WorkPlane} that is equal to this {@code WorkPlane} object;
     *         <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkPlane workPlane = (WorkPlane) o;

        if (workPlaneId != null ? !workPlaneId.equals(workPlane.workPlaneId) : workPlane.workPlaneId != null) return false;
        if (brigade != null ? !brigade.equals(workPlane.brigade) : workPlane.brigade != null) return false;
        if (bid != null ? !bid.equals(workPlane.bid) : workPlane.bid != null) return false;
        if (status != null ? !status.equals(workPlane.status) : workPlane.status != null) return false;
        if (userAdvisor != null ? !userAdvisor.equals(workPlane.userAdvisor) : workPlane.userAdvisor != null)
            return false;
        if (workTime != null ? !workTime.equals(workPlane.workTime) : workPlane.workTime != null) return false;
        return completeTime != null ? completeTime.equals(workPlane.completeTime) : workPlane.completeTime == null;
    }

    /**
     * The {@code hashCode} method uses for calculate {@code WorkPlane} hash code.
     *
     * @return hash code of the {@code WorkPlane}
     */
    @Override
    public int hashCode() {
        int result = workPlaneId != null ? workPlaneId.hashCode() : 0;
        result = 31 * result + (brigade != null ? brigade.hashCode() : 0);
        result = 31 * result + (bid != null ? bid.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (userAdvisor != null ? userAdvisor.hashCode() : 0);
        result = 31 * result + (workTime != null ? workTime.hashCode() : 0);
        result = 31 * result + (completeTime != null ? completeTime.hashCode() : 0);
        return result;
    }

    /**
     * Formats a {@code WorkPlane} in the following format
     * <code>WorkPlane{workPlaneId, brigade, bid, status, userAdvisor, workTime, completeTime}</code>
     *
     * @return a <code>String</code> object which represent {@code WorkPlane}
     */
    @Override
    public String toString() {
        return "WorkPlane{" +
                "workPlaneId=" + workPlaneId +
                ", brigade=" + brigade +
                ", bid=" + bid +
                ", status='" + status + '\'' +
                ", userAdvisor=" + userAdvisor +
                ", workTime=" + workTime +
                ", completeTime=" + completeTime +
                '}';
    }
}
