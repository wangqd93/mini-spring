package com.bycsmys.mini.spring.beans.factory.config;

import java.util.*;

public class ConstructorArgumentValues {

    private final Map<Integer, ConstructorArgumentValue> indexedArgumentValues = new HashMap<>(0);

    private final List<ConstructorArgumentValue> genericConstructorArgumentValues = new LinkedList<>();

    private void addArgumentValue(Integer key, ConstructorArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);
    }

    public boolean hasIndexArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericConstructorArgumentValues.add(new ConstructorArgumentValue(type, value));
    }

    public void addGenericArgumentValue(ConstructorArgumentValue newValue) {
        if (newValue.getName() != null) {
            for (Iterator<ConstructorArgumentValue> it = this.genericConstructorArgumentValues.iterator(); it.hasNext(); ) {
                ConstructorArgumentValue currentValue = it.next();
                if (newValue.getName().equals(currentValue.getName())) {
                    it.remove();
                }
            }
        }
        this.genericConstructorArgumentValues.add(newValue);
        this.indexedArgumentValues.put(genericConstructorArgumentValues.size() - 1, newValue);
    }

    public ConstructorArgumentValue getGenericArgumentValue(String requiredName) {
        for (ConstructorArgumentValue valueHolder : this.genericConstructorArgumentValues) {
            if (valueHolder.getName() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericConstructorArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericConstructorArgumentValues.isEmpty();
    }






}
