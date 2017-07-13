package com.sus.tankcommon.base;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;


/**
 * 所有Fragment的一个基类,在这里添加和业务无关的通用功能
 * <p/>
 * Created by yuhenghui on 16/8/18.
 */
abstract class BaseFragment extends Fragment implements IGroupView, KeyEvent.Callback {

    private IPageSwitcher mPageSwitcher;
    private PresenterGroup mTopPresenter;
    private View mRootView;

    private boolean mDestroyed = false;


    /**
     * 提供页面跳转器,用于实现页面的切换
     *
     * @return
     */
    protected final IPageSwitcher getPageSwitcher() {
        if (mPageSwitcher != null) {
            return mPageSwitcher;
        }
        mPageSwitcher = createPageSwitcher();
        return mPageSwitcher;
    }

    /**
     * 创建一个页面切换器,用于处理页面切换功能
     *
     * @return
     */
    protected IPageSwitcher createPageSwitcher() {
        //return new BasePagerSwitcher(getBusinessContext(), this);
        return null;
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (onActivityResultHappen(requestCode, resultCode, data)) {
            return;
        }
        PresenterGroup topPresenter = mTopPresenter;
        if (topPresenter == null) {
            return;
        }
        topPresenter.onDispatchActivityResult(requestCode, resultCode, data);
    }

    /**
     * 处理Fragment的onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return true 已经处理了onActivityResult
     */
    public boolean onActivityResultHappen(int requestCode, int resultCode, Intent data) {
        return false;
    }

    protected abstract PresenterGroup onCreateTopPresenter();

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDestroyed = false;
        //populateCurrentSID();
//        mToastHandler = new ToastHandler(getContext());
//        mDialogHandler = new CommonDialogHandler(getBusinessContext(), this);
        mTopPresenter = onCreateTopPresenter();
        mTopPresenter.setPageSwitcher(getPageSwitcher());
        mTopPresenter.setIView(this);
        mRootView = onCreateViewImpl(inflater, container, savedInstanceState);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mListener);
        mTopPresenter.dispatchPageCreate();
        return mRootView;
    }

    protected final void parentNoClipChildren(View topParent, View child) {
        if (child == null || !(topParent instanceof ViewGroup)) {
            return;
        }
        ViewParent parent = child.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setClipChildren(false);
            /** 到了最顶层的view,不在递归处理*/
            if (topParent != parent) {
                parentNoClipChildren(topParent, (ViewGroup) parent);
            }
        }
    }

//    private void populateCurrentSID() {
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            mCurrentSID = bundle.getString(BaseExtras.EXTRA_CURRENT_SID, null);
//            if (!TextUtils.isEmpty(mCurrentSID)) {
//                onSIDPopulated(mCurrentSID);
//                return;
//            }
//        }
//
//        BusinessContext bizCtx = getBusinessContext();
//        BusinessInfo bizInfo = bizCtx != null ? bizCtx.getBusinessInfo() : null;
//        mCurrentSID = bizInfo != null ? bizInfo.getBusinessId() : null;
//        if (!TextUtils.isEmpty(mCurrentSID)) {
//            onSIDPopulated(mCurrentSID);
//        }
//    }

    @Nullable
    protected View onCreateViewImpl(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public final void onStart() {
        super.onStart();
        mTopPresenter.dispatchPageStart();
        onStartImpl();
    }

    protected void onStartImpl() {
    }

    @Override
    public final void onResume() {
        super.onResume();
        mTopPresenter.dispatchPageResume();
        onResumeImpl();
//        OmegaSDK.fireFragmentResumed(this);
    }

    protected void onResumeImpl() {
    }

    @Override
    public final void onPause() {
        super.onPause();
        mTopPresenter.dispatchPagePause();
        onPauseImpl();
//        OmegaSDK.fireFragmentPaused(this);
    }

    protected void onPauseImpl() {
    }

    @Override
    public final void onStop() {
        super.onStop();
        mTopPresenter.dispatchPageStop();
        onStopImpl();
    }

    protected void onStopImpl() {
    }

    @Override
    public final void onDestroyView() {
        mDestroyed = true;
        super.onDestroyView();
        //dismissCurrentDialog();
        mTopPresenter.dispatchPageDestroy();

        onDestroyViewImpl();

        mPageSwitcher = null;
        mTopPresenter = null;
//        mToastHandler = null;
//        mDialogHandler = null;
        mRootView = null;
    }

    protected boolean isDestroyed() {
        return mDestroyed;
    }
//
//    private void dismissCurrentDialog() {
//        if (mDialogHandler == null) {
//            return;
//        }
//        mDialogHandler.dismissCurrent();
//    }

    protected void onDestroyViewImpl() {
    }


//    /**
//     * 展示loading信息
//     *
//     * @param info
//     */
//    @Override
//    public final void showDialog(DialogInfo info) {
//        if (!isDestroyed() && mDialogHandler != null) {
//            mDialogHandler.show(info);
//        }
//    }

    /**
     * 返回当前是否有dialog正在显示
     *
     * @return
     */
    @Override
    public boolean isDialogShowing() {
        //return mDialogHandler.isDialogShowing();
        return false;
    }

    /**
     * 取消正在展示的loading
     */
    @Override
    public final void dismissDialog(int dialogId) {
//        if (!isDestroyed() && mDialogHandler != null) {
//            mDialogHandler.dismiss(dialogId);
//        }
    }

    /**
     * Dialog被点击
     *
     * @param dialogId 哪个dialog被点击
     * @param action   哪个按钮被点击
     */
    @Override
    public final void onDialogClicked(int dialogId, int action) {
//        if (!isDestroyed() && !onDialogAction(dialogId, action) && mTopPresenter != null) {
//            mTopPresenter.dispatchDialogAction(dialogId, action);
//        }
    }

    /**
     * 处理Fragment中添加的dialog的回调
     *
     * @param dialogId
     * @param action
     * @return
     */
    protected boolean onDialogAction(int dialogId, int action) {
        return false;
    }

//    /**
//     * 展示一个Toast
//     *
//     * @param info
//     */
//    @Override
//    public final void showToast(ToastInfo info) {
//        if (!isDestroyed() && mToastHandler != null) {
//            mToastHandler.showToast(info);
//        }
//    }


    private ViewTreeObserver.OnGlobalLayoutListener mListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            View rootView = mRootView;
            if (rootView == null) {
                return;
            }
            rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            Animator enterAnimator = offerEnterAnimation();
            if (enterAnimator != null) {
                enterAnimator.start();
            }

            onFirstLayoutDone();
        }
    };

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        onCreateAnimationHappen(enter);
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    private void onCreateAnimationHappen(boolean enter) {
        Animator animator = !enter ? offerExitAnimation() : null;
        if (animator != null) {
            animator.start();
        }
    }

    protected void onFirstLayoutDone() {

    }

    protected Animator offerEnterAnimation() {
        return null;
    }

    protected Animator offerExitAnimation() {
        return null;
    }

    @Override
    public void setBackVisible(boolean visible) {
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isDestroyed() || event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            return false;
        }
//        if (mDialogHandler != null && mDialogHandler.onBackPressed()) {
//            return true;
//        }
        return mTopPresenter.dispatchBackPressed(IPresenter.BackType.BackKey);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        return false;
    }
}
