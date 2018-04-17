package org.dozer.loader.api;

public enum MergePolicy {
    /**
     * not use merge
     * default policy
     */
    NONE,
    /**
     * don't update value
     */
    KEEP_ORIGINAL,
    /**
     * keep the first changed value
     */
    KEEP_FIRST_CHANGE,
    /**
     * update value every time
     */
    KEEP_LAST,
    /**
     * if there are more than one actual changes will throw MergeConflictException
     */
    ERROR_ON_CONFLICTS
}
