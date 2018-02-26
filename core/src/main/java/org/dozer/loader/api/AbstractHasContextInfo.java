package org.dozer.loader.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public abstract class AbstractHasContextInfo<T> implements HasContextInfo<T> {

    private final Set<String> contexts = new HashSet<>();

    @Override
    public T withContexts(String... context) {
        contexts.addAll(asList(context));
        return (T) this;
    }

    @Override
    public Collection<String> getContexts() {
        return contexts;
    }
}
