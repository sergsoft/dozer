package org.dozer.loader.api;

import org.dozer.loader.DozerBuilder;

public abstract class ExtBeanMappingBuilder extends AbstractBeanMappingBuilder<ExtTypeMappingBuilder> {

    @Override
    public ExtTypeMappingBuilder mapping(String typeA, String typeB, TypeMappingOption... typeMappingOption) {
        return super.mapping(typeA, typeB, typeMappingOption);
    }

    @Override
    public ExtTypeMappingBuilder mapping(TypeDefinition typeA, String typeB, TypeMappingOption... typeMappingOption) {
        return super.mapping(typeA, typeB, typeMappingOption);
    }

    @Override
    public ExtTypeMappingBuilder mapping(String typeA, TypeDefinition typeB, TypeMappingOption... typeMappingOption) {
        return super.mapping(typeA, typeB, typeMappingOption);
    }

    @Override
    public ExtTypeMappingBuilder mapping(Class<?> typeA, Class<?> typeB, TypeMappingOption... typeMappingOption) {
        return super.mapping(typeA, typeB, typeMappingOption);
    }

    @Override
    public ExtTypeMappingBuilder mapping(TypeDefinition typeA, Class<?> typeB, TypeMappingOption... typeMappingOption) {
        return super.mapping(typeA, typeB, typeMappingOption);
    }

    @Override
    public ExtTypeMappingBuilder mapping(Class<?> typeA, TypeDefinition typeB, TypeMappingOption... typeMappingOption) {
        return super.mapping(typeA, typeB, typeMappingOption);
    }

    @Override
    public ExtTypeMappingBuilder mapping(TypeDefinition typeA, TypeDefinition typeB, TypeMappingOption... typeMappingOption) {
        return super.mapping(typeA, typeB, typeMappingOption);
    }

    @Override
    protected ExtTypeMappingBuilder makeTypeMappingBuilder(DozerBuilder.MappingBuilder mappingBuilder) {
        return new ExtTypeMappingBuilder(mappingBuilder);
    }
}
