package org.dozer.loader.api;

public interface ExtTypeMappingOption extends TypeMappingOption, HasContextInfo<ExtTypeMappingOption> {

    static ExtTypeMappingOption of(TypeMappingOption typeMappingOption) {
        return new ExtTypeMappingOptionWrapper(typeMappingOption);
    }
}
