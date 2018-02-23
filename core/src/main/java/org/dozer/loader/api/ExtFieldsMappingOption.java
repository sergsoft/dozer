package org.dozer.loader.api;

public interface ExtFieldsMappingOption extends FieldsMappingOption, HasContextInfo<ExtFieldsMappingOption> {

    static ExtFieldsMappingOption of(FieldsMappingOption fieldsMappingOption) {
        return new ExtFieldsMappingOptionWrapper(fieldsMappingOption);
    }
}
