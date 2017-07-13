package com.sus.tankcommon.base.dialog;

/**
 * Created by yuhenghui on 16/12/1.
 */
class StrongReference<T> {

    private T mValue;

    public StrongReference(T def) {
        mValue = def;
    }

    public void setValue(T value) {
        mValue = value;
    }

    public T getValue() {
        return mValue;
    }
}
