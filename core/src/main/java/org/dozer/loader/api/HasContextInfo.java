package org.dozer.loader.api;

import java.util.Set;

public interface HasContextInfo<T> {

    T withContext(String... contexts);

    Set<String> getContexts();

    default boolean isApplicable(String context) {
        Set<String> contexts = getContexts();
        return contexts == null || contexts.isEmpty() || contexts.contains(context);
    }
}
