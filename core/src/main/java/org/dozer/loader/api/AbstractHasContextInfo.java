package org.dozer.loader.api;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class AbstractHasContextInfo<T> implements HasContextInfo<T> {
    private final Set<String> contexts = new HashSet<>();

    @Override
    public T withContext(String... contexts) {
        this.contexts.addAll(asList(contexts));
        return (T) this;
    }

    @Override
    public Set<String> getContexts() {
        return contexts;
    }
}
