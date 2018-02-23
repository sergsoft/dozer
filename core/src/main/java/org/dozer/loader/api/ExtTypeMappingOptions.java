package org.dozer.loader.api;

import org.dozer.classmap.MappingDirection;
import org.dozer.classmap.RelationshipType;
import org.dozer.loader.DozerBuilder;

import static org.dozer.loader.api.ExtTypeMappingOption.of;

public final class ExtTypeMappingOptions {

    public static ExtTypeMappingOption beanFactory(final String value) {
        return of(TypeMappingOptions.beanFactory(value));
    }

    public static ExtTypeMappingOption dateFormat(final String value) {
        return of(TypeMappingOptions.dateFormat(value));
    }

    public static ExtTypeMappingOption mapEmptyString() {
        return of(TypeMappingOptions.mapEmptyString());
    }

    public static ExtTypeMappingOption mapEmptyString(final boolean value) {
        return of(TypeMappingOptions.mapEmptyString(value));
    }

    public static ExtTypeMappingOption mapNull() {
        return of(TypeMappingOptions.mapNull());
    }

    public static ExtTypeMappingOption mapNull(final boolean value) {
        return of(TypeMappingOptions.mapNull(value));
    }

    public static ExtTypeMappingOption relationshipType(final RelationshipType value) {
        return of(TypeMappingOptions.relationshipType(value));
    }

    public static ExtTypeMappingOption stopOnErrors() {
        return of(TypeMappingOptions.stopOnErrors());
    }

    public static ExtTypeMappingOption stopOnErrors(final boolean value) {
        return of(TypeMappingOptions.stopOnErrors(value));
    }

    public static ExtTypeMappingOption trimStrings() {
        return of(TypeMappingOptions.trimStrings());
    }

    public static ExtTypeMappingOption trimStrings(final boolean value) {
        return of(TypeMappingOptions.trimStrings(value));
    }

    public static ExtTypeMappingOption oneWay() {
        return of(TypeMappingOptions.oneWay());
    }

    public static ExtTypeMappingOption wildcard(final boolean value) {
        return of(TypeMappingOptions.wildcard(value));
    }
}
