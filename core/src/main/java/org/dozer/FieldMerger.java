package org.dozer;

import org.dozer.fieldmap.FieldMap;

public interface FieldMerger {

    MergeResult bypassValue(Object destObj, Object destFieldValue, FieldMap fieldMap, Object srcObj);

    void reset();

    class MergeResult {
        private final boolean bypass;
        private final Object newDestValue;

        public MergeResult(boolean bypass, Object newDestValue) {
            this.bypass = bypass;
            this.newDestValue = newDestValue;
        }

        public boolean isBypass() {
            return bypass;
        }

        public Object getNewDestValue() {
            return newDestValue;
        }
    }
}
