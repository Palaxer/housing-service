package org.palax.dao.util;

import org.palax.entity.Bid;
import org.palax.entity.Brigade;
import org.palax.entity.User;
import org.palax.entity.WorkPlane;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * {@code WorkPlaneBuilder} class for building test data to {@link WorkPlane} entity
 */
public class WorkPlaneBuilder implements Builder<WorkPlane> {

    private WorkPlane workPlane;

    private WorkPlaneBuilder() {
        workPlane = new WorkPlane();
    }

    public static WorkPlaneBuilder getBuilder() {
        return new WorkPlaneBuilder();
    }

    public WorkPlaneBuilder constructWorkPlane(Long template, String status, Builder<User> usetBuilder,
                                               Builder<Brigade> brigadeBuilder, Builder<Bid> bidBuilder) {
        if(template != null) {
            workPlane.setWorkPlaneId(template);
            workPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
            workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        }
        workPlane.setUserAdvisor(usetBuilder != null ? usetBuilder.build() : null);
        workPlane.setBrigade(brigadeBuilder != null ? brigadeBuilder.build() : null);
        workPlane.setBid(bidBuilder != null ? bidBuilder.build() : null);
        workPlane.setStatus(status);

        return this;
    }

    @Override
    public WorkPlane build() {
        return workPlane;
    }
}
