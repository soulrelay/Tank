package com.sus.tankcommon.base;

/**
 * 组件容器的抽象
 * Created by yuhenghui on 16/9/12.
 */
public interface IGroupView extends IView {
    /**
     * 显示一个dialog
     *
     * @param info
     */
    //void showDialog(DialogInfo info);

    /**
     * dismiss一个dialog
     *
     * @param dialogId
     */
    void dismissDialog(int dialogId);

    /**
     * dialog按钮被点击后回调
     *
     * @param dialogId
     * @param action
     */
    void onDialogClicked(int dialogId, int action);

    /**
     * 显示一个Toast
     *
     * @param info
     */
    //void showToast(ToastInfo info);

    /**
     * 设置back按钮的可见性
     *
     * @param visible
     */
    void setBackVisible(boolean visible);

    /**
     * 设置title信息
     *
     * @param title
     */
    void setTitle(String title);

    /**
     * 查看当前是否有dialog显示中
     */
    boolean isDialogShowing();
}
