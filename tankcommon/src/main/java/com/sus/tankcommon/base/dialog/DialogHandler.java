package com.sus.tankcommon.base.dialog;

import android.os.Handler;
import android.os.Looper;


import com.sus.tankcommon.base.IGroupView;

import java.util.concurrent.CountDownLatch;

/**
 * Created by yuhenghui on 16/9/13.
 */
public abstract class DialogHandler {

    protected IGroupView mGroupView;
    protected IDialog mShowDialog;
    protected Handler mUIHandler;

    public DialogHandler(IGroupView groupView) {
        mGroupView = groupView;
        mUIHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 显示一个Dialog
     *
     * @param info 填充dialog的信息
     * @return
     */
    public final boolean show(final DialogInfo info) {
        if (info == null) {
            return false;
        }
        if (runOnUIThread()) {
            return showImpl(info);
        } else {
            final CountDownLatch latch = new CountDownLatch(1);
            final StrongReference<Boolean> ref = new StrongReference<>(false);
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        ref.setValue(showImpl(info));
                    } finally {
                        latch.countDown();
                    }
                }
            });
            try {
                latch.await();
                return ref.getValue();
            } catch (InterruptedException e) {
                return false;
            }
        }
    }

    private boolean showImpl(DialogInfo info) {
        boolean showDialog = mShowDialog != null && mShowDialog.isShowing();
        if (showDialog && info.getDialogId() == mShowDialog.getId()) {
            mShowDialog.update(info);
            return true;
        } else {
            dismissCurrent();
            IDialog dialog = createDialog(info);
            if (dialog == null) {
                return false;
            }
            mShowDialog = dialog;
            dialog.show();
            return true;
        }
    }

    public final void dismissCurrent() {
        if (runOnUIThread()) {
            dismissCurrentImpl();
        } else {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    dismissCurrentImpl();
                }
            });
        }
    }

    public final boolean onBackPressed() {
        if (runOnUIThread()) {
            return onBackPressedImpl();
        } else {

            final CountDownLatch latch = new CountDownLatch(1);
            final StrongReference<Boolean> ref = new StrongReference<>(false);
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        ref.setValue(onBackPressedImpl());
                    } finally {
                        latch.countDown();
                    }
                }
            });

            try {
                latch.await();
                return ref.getValue();
            } catch (InterruptedException e) {
                return false;
            }
        }
    }

    private boolean onBackPressedImpl() {
        boolean showDialog = mShowDialog != null && mShowDialog.isShowing();
        if (!showDialog) {
            return false;
        } else {
            if (mShowDialog.cancelable()) {
                dismissCurrentImpl();
            }
            return true;
        }
    }

    private void dismissCurrentImpl() {
        if (mShowDialog == null || !mShowDialog.isShowing()) {
            return;
        }
        dismiss(mShowDialog.getId());
    }

    /**
     * 销毁一个Dialog
     *
     * @param dialogId dialog的ID信息,用于唯一标识一个页面内的一个Dialog
     */
    public final void dismiss(final int dialogId) {
        if (runOnUIThread()) {
            dismissImpl(dialogId);
        } else {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    dismissImpl(dialogId);
                }
            });
        }
    }

    private void dismissImpl(int dialogId) {
        /** 如果请求dismiss的dialog不存在,直接返回*/
        IDialog dismissDialog = mShowDialog;
        boolean showDialog = mShowDialog != null && mShowDialog.isShowing();
        if (!showDialog || dismissDialog.getId() != dialogId) {
            return;
        }
        mShowDialog = null;
        dismissDialog.dismiss();
    }

    /**
     * 判断当前是否有dialog正在显示
     * @return
     */
    public boolean isDialogShowing() {
        return mShowDialog != null && mShowDialog.isShowing();
    }

    protected boolean runOnUIThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    protected abstract IDialog createDialog(DialogInfo info);
}
