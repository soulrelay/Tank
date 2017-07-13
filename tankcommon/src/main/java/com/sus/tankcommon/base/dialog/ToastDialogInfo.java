package com.sus.tankcommon.base.dialog;

import android.graphics.drawable.Drawable;

/**
 * Created by yuhenghui on 16/9/26.
 */
public class ToastDialogInfo extends DialogInfo {
    Drawable icon;
    IconType iconType;

    String message;

    public ToastDialogInfo(int dialogId) {
        super(dialogId);
        /** 默认是不可以取消的*/
        setCancelable(false);
    }

    public void setIcon(IconType iconType) {
        this.iconType = iconType;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum IconType {
        INFO, COMPLETE, ERROR
    }
}
