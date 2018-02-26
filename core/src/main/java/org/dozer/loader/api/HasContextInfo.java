package org.dozer.loader.api;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.emptySet;

public interface HasContextInfo<T> {

    default T withContext(String context) {
        return withContexts(context);
    }

    T withContexts(String... context);

    default Collection<String> getContexts() {
        return emptySet();
    }
}
