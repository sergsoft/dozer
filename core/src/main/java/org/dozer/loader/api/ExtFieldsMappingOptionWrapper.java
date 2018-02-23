package org.dozer.loader.api;

import org.dozer.loader.DozerBuilder;

public class ExtFieldsMappingOptionWrapper extends AbstractHasContextInfo<ExtFieldsMappingOption> implements ExtFieldsMappingOption {
    private final FieldsMappingOption fieldsMappingOption;

    public ExtFieldsMappingOptionWrapper(FieldsMappingOption fieldsMappingOption) {
        this.fieldsMappingOption = fieldsMappingOption;
    }

    @Override
    public void apply(DozerBuilder.FieldMappingBuilder fieldMappingBuilder) {
        fieldsMappingOption.apply(fieldMappingBuilder);
    }
}
