package com.sus.tankcommon.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.sus.tankcommon.base.BaseEventPublisher.OnEventListener;

/**
 * 所有Presenter的基类,实现了与业务无关的功能
 * 如页面跳转,事件分发,生命周期回调等,属于IComponent生产的产品族中的一项
 * <p/>
 * Created by yuhenghui on 16/8/22.
 */
public abstract class IPresenter<V extends IView> {
    protected Context mContext;
    protected PresenterGroup mParent;
    protected V mView;
    protected boolean mRemoved = false;
    Bundle mArguments;

    public IPresenter(Context context) {
        mContext = context;
    }

    protected void setParent(PresenterGroup parent) {
        mParent = parent;
    }

    protected PresenterGroup getParent() {
        return mParent;
    }

    /**
     * 页面跳转到指定的Fragment
     *
     * @param clazz
     * @param options
     */
    protected void forward(Class<? extends Fragment> clazz, Bundle options) {
        IPageSwitcher switcher = getPageSwitcher();
        if (switcher == null) {
            return;
        }
        switcher.forward(clazz, options);
    }

    /**
     * 回到上一个页面
     */
    protected void goBack() {
        goBack(null);
    }

    /**
     * 回到上一个页面
     *
     * @param args 参数信息
     */
    protected void goBack(Bundle args) {
        IPageSwitcher switcher = getPageSwitcher();
        if (switcher == null) {
            return;
        }
        switcher.goBack(args);
    }

    /**
     * 回到根页面
     */
    protected void goBackRoot() {
        goBackRoot(null);
    }

    /**
     * 回到根页面
     */
    protected void goBackRoot(Bundle args) {
        IPageSwitcher switcher = getPageSwitcher();
        if (switcher == null) {
            return;
        }
        switcher.goBackRoot(args);
    }

    /**
     * 获取宿主Fragment
     *
     * @return 宿主Fragment, 可能为空.不建议从这个Fragment中取出Activity使用
     */
    protected Fragment getHost() {
        IPageSwitcher switcher = getPageSwitcher();
        return switcher != null ? switcher.getHost() : null;
    }

    protected IPageSwitcher getPageSwitcher() {
        return mParent != null ? mParent.getPageSwitcher() : null;
    }

    /** ---------------------------------启动Activity的功能开始----------------------------------------*/
    /**
     * 启动一个Activity
     *
     * @param intent
     */
    protected void startActivity(Intent intent) {
        startActivityForResult(intent, -1, null);
    }

    /**
     * 启动一个Activity
     *
     * @param intent
     * @param options
     */
    protected void startActivity(Intent intent, Bundle options) {
        startActivityForResult(intent, -1, options);
    }

    /**
     * 启动一个Activity并等待结果
     *
     * @param intent
     * @param requestCode
     */
    protected void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, null);
    }

    /**
     * 启动一个Activity并等待结果
     *
     * @param intent
     * @param requestCode
     * @param options
     */
    protected void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (mParent != null) {
            mParent.startActivityForChild(intent, requestCode, options, this);
        }
    }

    /**
     * 处理onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
    /** ------------------------------------事件分发功能开始-------------------------------------------*/
    /**
     * 向外发布一个需要联动的事件
     *
     * @param category
     */
    public void doPublish(String category) {
        this.doPublish(category, null);
    }

    /**
     * 向外发布一个需要联动的事件
     *
     * @param category
     * @param obj
     */
    public void doPublish(String category, Object obj) {
        if (!TextUtils.isEmpty(category) && !mRemoved) {
            BaseEventPublisher.getPublisher().publish(category, obj);
        }
    }

    /**
     * 订阅一个信息
     *
     * @param category
     * @param l
     */
    public void subscribe(String category, OnEventListener l) {
        if (!TextUtils.isEmpty(category) && l != null && !mRemoved) {
            BaseEventPublisher.getPublisher().subscribe(category, l);
        }
    }

    /**
     * 取消一个消息订阅
     *
     * @param category
     * @param l
     */
    public void unsubscribe(String category, OnEventListener l) {
        if (TextUtils.isEmpty(category) || l == null) {
            return;
        }
        BaseEventPublisher.getPublisher().unsubscribe(category, l);
    }
    /** ------------------------------------事件分发功能结束-------------------------------------------*/

    /** ------------------------------Presenter与IView的绑定功能开始-----------------------------------*/
    /**
     * 将View绑定到Presenter中, 通常这个操作需要在Component初始化的时候完成。
     *
     * @param iView
     */
    public void setIView(V iView) {
        mView = iView;
    }

    /** ------------------------------Presenter与IView的绑定功能结束-----------------------------------*/

    /**
     * ---------------------------------页面生命周期回调功能开始---------------------------------------
     */
    protected void onAdd(Bundle arguments) {
    }

    protected void onPageStart() {
    }

    protected void onPageResume() {
    }

    protected void onPagePause() {
    }

    protected void onPageStop() {
    }

    final void dispatchRemove() {
        onRemove();
        mRemoved = true;
    }

    protected void onRemove() {
    }

    protected void onPageShow() {
    }

    protected void onPageHide() {
    }

    protected void onLeaveHome() {
    }

    protected void onBackHome(Bundle arguments) {
    }

    /**
     * ---------------------------------页面生命周期回调功能开始---------------------------------------
     */

//    /**
//     * 显示一个Dialog, DialogInfo用于填充dialog中的内容
//     *
//     * @param info {@link com.didi.onecar.base.dialog.LoadingDialogInfo}
//     *             {@link com.didi.onecar.base.dialog.NormalDialogInfo}
//     */
//    protected void showDialog(DialogInfo info) {
//        if (mParent != null) {
//            mParent.showDialogForChild(info, this);
//        }
//    }

    /**
     * 返回dialog是否正在显示
     *
     * @return
     */
    protected boolean isDialogShowing() {
        if (mParent != null) {
            return mParent.isDialogShowing();
        }
        return false;
    }

    /**
     * dismiss一个Dialog
     *
     * @param dialogId
     */
    protected void dismissDialog(int dialogId) {
        if (mParent != null) {
            mParent.dismissDialogForChild(dialogId, this);
        }
    }

//    /**
//     * dialog按钮被点击或者被取消之后回调
//     *
//     * @param dialogId
//     * @param action   {@link com.didi.onecar.base.dialog.IDialog}
//     */
//    protected void onDialogAction(int dialogId, int action) {
//
//    }
//
//    protected void showToast(ToastInfo info) {
//        if (mParent != null) {
//            mParent.showToast(info);
//        }
//    }

    /**
     * 在Presenter中直接使用Fragment启动Activity时
     * 先用这个接口重新生成requestCode
     *
     * @param requestCode
     * @return
     */
    protected int requestCodeForHost(int requestCode) {
        if (mParent != null) {
            return mParent.hostRequestCodeForChild(this, requestCode);
        } else {
            return requestCode;
        }
    }

    /**
     * 点击左上角返回按钮和Back键时回调
     */
    protected boolean onBackPressed(BackType backType) {
        return false;
    }

    public enum BackType {
        TopLeft, BackKey
    }
}
