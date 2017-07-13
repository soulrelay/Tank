package com.sus.tankcommon.base.dialog;

/**
 * Created by yuhenghui on 16/9/19.
 */
public class LoadingDialogInfo extends DialogInfo {

    String mMessage;

    public LoadingDialogInfo(int dialogId) {
        super(dialogId);
    }

    public LoadingDialogInfo setMessage(String message) {
        mMessage = message;
        return this;
    }
}
