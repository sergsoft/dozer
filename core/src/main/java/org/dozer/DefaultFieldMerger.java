package org.dozer;

import org.dozer.fieldmap.FieldMap;
import org.dozer.loader.api.MergePolicy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.dozer.loader.api.MergePolicy.KEEP_FIRST_CHANGE;
import static org.dozer.loader.api.MergePolicy.KEEP_ORIGINAL;
import static org.dozer.loader.api.MergePolicy.NONE;

public class DefaultFieldMerger implements FieldMerger {
    private Map<String, MergeRecord> cache;

    @Override
    public MergeResult bypassValue(Object destObj, Object destFieldValue, FieldMap fieldMap, Object srcObj) {
        if (NONE.equals(fieldMap.getMergePolicy())) {
            return new MergeResult(false, destFieldValue);
        }

        Object oldValue = fieldMap.getDestFieldValue(destObj);
        MergeRecord mergeRecord = getCacheValue(makeKey(destObj, fieldMap));
        return mergeRecord.newValue(fieldMap.getMergePolicy(), oldValue, destFieldValue);
    }

    private String makeKey(Object destObj, FieldMap fieldMap) {
        return fieldMap.getSrcFieldName() + "_" + fieldMap.getDestFieldName() + System.identityHashCode(destObj);
    }

    @Override
    public void reset() {
        cache = null;
    }

    private MergeRecord getCacheValue(String key) {
        if (cache == null) {
            cache = new HashMap<>();
        }
        MergeRecord mergeRecord = cache.get(key);
        if (mergeRecord == null) {
            mergeRecord = new MergeRecord();
            cache.put(key, mergeRecord);
        }
        return mergeRecord;
    }

    private static class MergeRecord {
        private int count = 0;
        private Object initValue;

        MergeResult newValue(MergePolicy mergePolicy, Object currentValue, Object newValue) {
            if (count == 0) {
                count++;
                initValue = currentValue;
                return new MergeResult(KEEP_ORIGINAL.equals(mergePolicy), newValue);
            }
            count++;
            switch (mergePolicy) {
                case KEEP_ORIGINAL:
                    return new MergeResult(true, null);
                case KEEP_LAST:
                    return new MergeResult(false, newValue);
                case KEEP_FIRST_CHANGE:
                case ERROR_ON_CONFLICTS:
                    if (Objects.equals(currentValue, newValue)) {
                        // not changed
                        return new MergeResult(true, null);
                    }
                    if (Objects.equals(initValue, currentValue) ) {
                        // first update
                        return new MergeResult(false, newValue);
                    }
                    if (Objects.equals(initValue, newValue)) {
                        // initValue == newValue - reset old value - skip it
                        return new MergeResult(true, null);
                    }
                    // initValue != currentValue != newValue
                    if (KEEP_FIRST_CHANGE.equals(mergePolicy)) {
                        return new MergeResult(true, null);
                    } else {
                        throw new MergeConflictException();
                    }
            }
            throw new UnsupportedOperationException("Unsupported merge policy: " + mergePolicy);
        }
    }
}
