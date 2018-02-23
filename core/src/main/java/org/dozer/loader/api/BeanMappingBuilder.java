package org.dozer.loader.api;

import org.dozer.loader.DozerBuilder;

public abstract class BeanMappingBuilder extends AbstractBeanMappingBuilder<TypeMappingBuilder> {

    @Override
    protected TypeMappingBuilder makeTypeMappingBuilder(DozerBuilder.MappingBuilder mappingBuilder) {
        return new TypeMappingBuilder(mappingBuilder);
    }
}
