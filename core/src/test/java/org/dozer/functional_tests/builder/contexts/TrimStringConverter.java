package org.dozer.functional_tests.builder.contexts;

import org.dozer.DozerConverter;

public class TrimStringConverter extends DozerConverter<String, String> {

    public TrimStringConverter() {
        super(String.class, String.class);
    }

    @Override
    public String convertTo(String source, String destination) {
        return source != null ? source.trim() : null;
    }

    @Override
    public String convertFrom(String source, String destination) {
        return source != null ? source.trim() : null;
    }
}
