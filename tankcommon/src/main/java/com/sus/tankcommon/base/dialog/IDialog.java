package com.sus.tankcommon.base.dialog;

/**
 * Created by yuhenghui on 16/9/13.
 */
public interface IDialog {

    int ACTION_NEGATIVE = 1;
    int ACTION_POSITIVE = 2;
    int ACTION_NEUTRAL = 3;
    int ACTION_CLOSE = 4;

    int getId();
    void show();
    boolean isShowing();
    void dismiss();
    void update(DialogInfo info);
    boolean cancelable();

    interface DialogListener {
        void onAction(int action);
    }
}
