package org.dozer.loader.api;

import org.dozer.loader.DozerBuilder;

public class ExtTypeMappingOptionWrapper extends AbstractHasContextInfo<ExtTypeMappingOption> implements ExtTypeMappingOption {
    private final TypeMappingOption typeMappingOption;

    public ExtTypeMappingOptionWrapper(TypeMappingOption typeMappingOption) {
        this.typeMappingOption = typeMappingOption;
    }

    @Override
    public void apply(DozerBuilder.MappingBuilder fieldMappingBuilder) {
        typeMappingOption.apply(fieldMappingBuilder);
    }
}
