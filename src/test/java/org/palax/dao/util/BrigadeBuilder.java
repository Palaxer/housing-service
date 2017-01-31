package org.palax.dao.util;

import org.palax.entity.Brigade;
import org.palax.entity.WorkType;

/**
 * {@code BrigadeBuilder} class for building test data to {@link Brigade} entity
 */
public class BrigadeBuilder implements Builder<Brigade> {

    private Brigade brigade;

    private BrigadeBuilder() {
        brigade = new Brigade();
    }

    public static BrigadeBuilder getBuilder() {
        return new BrigadeBuilder();
    }

    public BrigadeBuilder constructBrigade(Long template, Builder<WorkType> workTypeBuilder) {
        if(template != null) {
            brigade.setBrigadeId(template);
            brigade.setBrigadeName("brigade" + template);
        }
        brigade.setWorkType(workTypeBuilder != null ? workTypeBuilder.build() : null);

        return this;
    }

    @Override
    public Brigade build() {
        return brigade;
    }
}
