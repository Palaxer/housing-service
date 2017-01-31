package org.palax.dao.util;

import org.palax.entity.WorkType;

/**
 * {@code WorkTypeBuilder} class for building test data to {@link WorkType} entity
 */
public class WorkTypeBuilder implements Builder<WorkType> {

    private WorkType workType;

    private WorkTypeBuilder() {
        workType = new WorkType();
    }

    public static WorkTypeBuilder getBuilder() {
        return new WorkTypeBuilder();
    }

    public WorkTypeBuilder constructWorkType(Long template) {
        if(template != null) {
            workType.setWorkTypeId(template);
            workType.setTypeName("work_type" + template);
        }

        return this;
    }

    @Override
    public WorkType build() {
        return workType;
    }
}
