package org.dozer.functional_tests.builder.contexts;

import org.dozer.DozerConverter;

public class AbsConverter extends DozerConverter<Integer, Integer> {
    public AbsConverter() {
        super(Integer.class, Integer.class);
    }

    @Override
    public Integer convertTo(Integer source, Integer destination) {
        return source != null ? Math.abs(source) : null;
    }

    @Override
    public Integer convertFrom(Integer source, Integer destination) {
        return source != null ? Math.abs(source) : null;
    }
}
