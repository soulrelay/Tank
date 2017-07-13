package com.sus.tankcommon.base.dialog;

/**
 * Created by yuhenghui on 16/9/19.
 */
public abstract class DialogInfo {
    int dialogId;
    boolean cancelable;

    protected DialogInfo(int dialogId) {
        this.dialogId = dialogId;
    }

    public int getDialogId() {
        return dialogId;
    }

    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }

    public DialogInfo setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }
}
