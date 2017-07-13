package com.sus.tankcommon.base.dialog;

import android.graphics.drawable.Drawable;

//import com.didi.sdk.view.dialog.AlertController;

/**
 * Created by yuhenghui on 16/9/19.
 */
public class NormalDialogInfo extends DialogInfo {
    int icon;
    Drawable iconDrawable;
    //AlertController.IconType iconType;
    CharSequence title;
    CharSequence message;
    CharSequence positiveText;
    CharSequence neutralText;
    CharSequence negativeText;
    boolean iconVisible = true;
    boolean cancelable;
    boolean closeVisible = false;

    public NormalDialogInfo(int dialogId) {
        super(dialogId);
    }

    public NormalDialogInfo setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public NormalDialogInfo setIcon(Drawable drawable) {
        this.iconDrawable = drawable;
        return this;
    }

//    public NormalDialogInfo setIcon(AlertController.IconType iconType) {
//        this.iconType = iconType;
//        return this;
//    }

    public NormalDialogInfo setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public NormalDialogInfo setMessage(CharSequence message) {
        this.message = message;
        return this;
    }

    public NormalDialogInfo setPositiveText(CharSequence positiveText) {
        this.positiveText = positiveText;
        return this;
    }

    public NormalDialogInfo setNeutralText(CharSequence neutralText) {
        this.neutralText = neutralText;
        return this;
    }

    public NormalDialogInfo setNegativeText(CharSequence negativeText) {
        this.negativeText = negativeText;
        return this;
    }

    public NormalDialogInfo setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public NormalDialogInfo setIconVisible(boolean iconVisible) {
        this.iconVisible = iconVisible;
        return this;
    }

    public NormalDialogInfo setCloseVisible(boolean closeVisible) {
        this.closeVisible = closeVisible;
        return this;
    }
}
