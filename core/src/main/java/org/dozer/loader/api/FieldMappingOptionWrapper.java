package org.dozer.loader.api;

import org.dozer.loader.DozerBuilder;

public class FieldMappingOptionWrapper extends AbstractHasContextInfo<FieldsMappingOption> implements FieldsMappingOption {
    private final FieldsMappingOption fieldsMappingOption;

    public FieldMappingOptionWrapper(FieldsMappingOption fieldsMappingOption) {
        this.fieldsMappingOption = fieldsMappingOption;
    }

    @Override
    public void apply(DozerBuilder.FieldMappingBuilder fieldMappingBuilder) {
        fieldsMappingOption.apply(fieldMappingBuilder);
    }
}
