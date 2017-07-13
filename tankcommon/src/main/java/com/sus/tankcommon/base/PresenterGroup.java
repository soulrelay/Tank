package com.sus.tankcommon.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * 用于Fragment对应的Presenter
 * 属于组件容器这一级别的Presenter
 * 1. 管理子Presenter的添加和删除
 * 2. 管理子组件的生命周期回调
 * 3. 负责分发的startActivity的onActivityResult
 * 4. 负责展示dialog,并分发dialog回调
 * <p>
 * <p/>
 * Created by yuhenghui on 16/9/5.
 */
public abstract class PresenterGroup<V extends IGroupView> extends IPresenter<V> {

    private static final int REQUEST_CODE_MASK = 0x0000FF00;
    private static final int REQUEST_CODE_MAX = 0x000000FF;

    private static final int DIALOG_ID_MASK = 0xFFFF0000;
    private static final int DIALOG_ID_MAX = 0x0000FFFF;

    private IPageSwitcher mPageSwitcher;
    protected final Handler mUIHandler;
    private PageState mCurrentState = PageState.NONE;

    public PresenterGroup(Context context, Bundle arguments) {
        super(context);
        mUIHandler = new Handler(Looper.getMainLooper());
        mArguments = arguments;
    }


    /**
     * 子的Presenter集合
     */
    private final List<IPresenter> mChildren = new LinkedList<>();
    private final IndexAllocator<IPresenter> mChildIndexes = new IndexAllocator<>();

    /** ---------------------------------Presenter层级管理功能开始----------------------------------------*/
    /**
     * 向当前Presenter中加入一个子的Presenter
     *
     * @param child
     * @param arguments 组件Presenter的初始化参数
     * @return
     */
    public final boolean addChild(IPresenter child, Bundle arguments) {
        if (!runOnUIThread()) {
            throw new RuntimeException("添加child必须在UI线程!");
        }
        if (child == null) {
            throw new IllegalArgumentException("无法添加一个null的Presenter到父Presenter中!");
        }
        if (child.getParent() != null) {
            throw new IllegalArgumentException(child + "已经添加到" + child.mParent + "中!");
        }
        if (mCurrentState == PageState.DESTROYED) {
            throw new IllegalStateException("页面已经销毁,不能够再往里边添加组件!!!");
        }
        child.setParent(this);
        mChildren.add(child);
        child.mArguments = arguments != null ? arguments : mArguments;
        dispatchLifeCycleWhenAdd(child);
        return true;
    }

    /**
     * 向当前Presenter中加入一个子的Presenter, 默认使用页面的Arguments作为参数
     *
     * @param child
     * @return
     */
    public final boolean addChild(IPresenter child) {
        return addChild(child, null);
    }

    /**
     * 从当前Presenter中移除一个子的Presenter
     *
     * @param child
     * @return
     */
    public final boolean removeChild(IPresenter child) {
        if (!runOnUIThread()) {
            throw new RuntimeException("移除child必须在UI线程执行!");
        }
        if (mCurrentState == PageState.DESTROYED) {
            throw new IllegalStateException("页面已经销毁,已经没有任何组件了!!!");
        }
        if (child != null && child.getParent() != null) {
            boolean success = mChildren.remove(child);
            if (success) {
                mChildIndexes.removeIndex(child);
                dispatchLiftCycleWhenRemove(child);
            }
            child.setParent(null);
            return success;
        } else {
            return false;
        }
    }
    /** ---------------------------------Presenter层级管理功能开始----------------------------------------*/

    /** ---------------------------------Fragment跳转功能开始----------------------------------------*/

    /**
     * 设置页面跳转处理器,有外部提供
     * 只有顶级Presenter才需要
     *
     * @param pageSwitcher
     */
    void setPageSwitcher(IPageSwitcher pageSwitcher) {
        mPageSwitcher = pageSwitcher;
    }

    /**
     * 获取页面跳转处理器,如果有父Presenter使用父Presenter的跳转器
     * 没有父Presenter的情况下自己创建跳转器,子类调用这个方法实现页面的跳转
     *
     * @return
     */
    @Override
    protected final IPageSwitcher getPageSwitcher() {
        return mParent != null ? mParent.getPageSwitcher() : mPageSwitcher;
    }

    /**
     * ---------------------------------Fragment跳转功能结束----------------------------------------
     */
    @Override
    protected final void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (mPageSwitcher == null) {
            return;
        }
        mPageSwitcher.startActivityForResult(intent, requestCode, options);
    }

    /**
     * 启动一个Activity并等待结果
     *
     * @param intent
     * @param requestCode
     * @param options
     */
    final void startActivityForChild(Intent intent, int requestCode, Bundle options, IPresenter child) {
        if (intent == null || child == null) {
            return;
        }
        if (mPageSwitcher == null) {
            return;
        }
        /** 如果Parent为空,是顶级容器,直接请求,不需要其它的信息*/
        if (requestCode == -1) {
            mPageSwitcher.startActivityForResult(intent, requestCode, options);
            return;
        }
        /** 为子Presenter生成一个新的requestCode*/
        requestCode = hostRequestCodeForChild(child, requestCode);
        mPageSwitcher.startActivityForResult(intent, requestCode, options);
    }

    @Override
    protected final int requestCodeForHost(int requestCode) {
        return requestCode;
    }

    /**
     * 为子Presenter创建一个新的requestCode
     *
     * @param child
     * @param requestCode
     * @return
     */
    int hostRequestCodeForChild(IPresenter child, int requestCode) {
        /** 如果requestCode是一个无效值,不做处理*/
        if (requestCode == -1) {
            return requestCode;
        }
        if ((requestCode & REQUEST_CODE_MASK) != 0) {
            throw new RuntimeException("request code 必须在0到" + REQUEST_CODE_MAX + "之间");
        }
        /** 获取自身在父容器中的索引信息*/
        int index = mChildIndexes.allocateIndex(child, 1, REQUEST_CODE_MAX);
        if (index <= 0) {
            throw new RuntimeException("子Presenter已经超过容量,请审核自己的代码!");
        }
        requestCode = (index << 8) | requestCode;
        return requestCode;
    }

    /**
     * 分发OnActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    final void onDispatchActivityResult(int requestCode, int resultCode, Intent data) {
        /** 如果Presenter的标记为0,表示当前Presenter*/
        if ((requestCode & REQUEST_CODE_MASK) == 0) {
            onActivityResult(requestCode, resultCode, data);
            return;
        }

        /** 获得Presenter的偏移信息,如果偏移信息不符合逻辑,不再处理*/
        int index = (requestCode & REQUEST_CODE_MASK) >> 8;
        IPresenter<V> presenter = mChildIndexes.findByIndex(index);
        if (presenter == null) {
            return;
        }
        requestCode = requestCode & (~REQUEST_CODE_MASK);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ---------------------------------启动Activity的功能结束----------------------------------------
     */

    /**
     * 分发页面创建的生命周期回调
     */
    final void dispatchPageCreate() {
        subscribeCommonListeners();

        onAdd(mArguments);

        for (int i = 0; i < mChildren.size(); i++) {
            IPresenter child = mChildren.get(i);
            child.onAdd(child.mArguments);
        }

        mCurrentState = PageState.CREATED;
    }

    /**
     * 分发页面Start的声明周期回调
     */
    final void dispatchPageStart() {
        onPageStart();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPageStart();
        }

        mCurrentState = PageState.STARTED;
    }

    /**
     * 分发页面Resume的生命周期回调
     */
    final void dispatchPageResume() {
        onPageResume();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPageResume();
        }

        mCurrentState = PageState.RESUMED;
    }

    /**
     * 分发页面Pause的生命周期回调
     */
    final void dispatchPagePause() {
        onPagePause();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPagePause();
        }

        mCurrentState = PageState.PAUSED;
    }

    /**
     * 分发页面Stop的生命周期回调
     */
    final void dispatchPageStop() {
        onPageStop();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPageStop();
        }
        mCurrentState = PageState.STOPPED;
    }

    /**
     * 分发页面销毁的生命周期回调
     */
    final void dispatchPageDestroy() {
        /** 清理掉所有没有处理的信息*/
        mUIHandler.removeCallbacksAndMessages(null);

        unsubscribeCommonListeners();

        dispatchRemove();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = size - 1; i >= 0; i--) {
            removeChild(mChildren.get(i));
        }

        mCurrentState = PageState.DESTROYED;
    }

    /**
     * 为HomeFragment分发onShow事件
     */
    final void dispatchOnPageShow() {
        onPageShow();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPageShow();
        }
    }

    /**
     * 为HomeFragment分发onHide事件
     */
    final void dispatchOnPageHide() {
        onPageHide();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onPageHide();
        }
    }

    /**
     * 为HomeFragment分发LeaveHome事件
     */
    final void dispatchLeaveHome() {

        unsubscribeCommonListeners();

        onLeaveHome();

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onLeaveHome();
        }
    }

    /**
     * 为HomeFragment分发LeaveHome事件
     *
     * @param arguments
     */
    final void dispatchBackHome(Bundle arguments) {

        subscribeCommonListeners();

        onBackHome(arguments);

        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = 0; i < size; i++) {
            mChildren.get(i).onBackHome(arguments);
        }
    }

    private void subscribeCommonListeners() {
//        subscribe(EventKeys.Common.UPDATE_TITLE, mTitleListener);
//        subscribe(EventKeys.Common.BACK_VISIBILITY, mBackVisibleListener);
    }

    private void unsubscribeCommonListeners() {
//        unsubscribe(EventKeys.Common.UPDATE_TITLE, mTitleListener);
//        unsubscribe(EventKeys.Common.BACK_VISIBILITY, mBackVisibleListener);
    }

    protected boolean runOnUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
//
//    @Override
//    protected final void showDialog(DialogInfo info) {
//        mView.showDialog(info);
//    }

    @Override
    protected final boolean isDialogShowing() {
        return mView.isDialogShowing();
    }

//    final void showDialogForChild(DialogInfo info, IPresenter child) {
//        int newDialogId = checkAndBuildNewDialogId(info.getDialogId(), child);
//        info.setDialogId(newDialogId);
//        this.showDialog(info);
//    }

    @Override
    protected final void dismissDialog(int dialogId) {
        mView.dismissDialog(dialogId);
    }

    final void dismissDialogForChild(int dialogId, IPresenter child) {
        this.dismissDialog(checkAndBuildNewDialogId(dialogId, child));
    }
//
//    /**
//     * 分发dialog的点击事件
//     *
//     * @param dialogId
//     * @param action
//     */
//    final void dispatchDialogAction(int dialogId, int action) {
//        int index = (dialogId & DIALOG_ID_MASK) >> 16;
//        if (index == 0) {
//            onDialogAction(dialogId, action);
//        } else {
//            IPresenter child = mChildIndexes.findByIndex(index);
//            int newDialogId = dialogId & (~DIALOG_ID_MASK);
//            if (child != null) {
//                child.onDialogAction(newDialogId, action);
//            }
//        }
//    }

//    @Override
//    protected final void showToast(ToastInfo info) {
//        mView.showToast(info);
//    }

    private int checkAndBuildNewDialogId(int dialogId, IPresenter child) {
        if ((dialogId & DIALOG_ID_MASK) != 0) {
            throw new RuntimeException("Dialog id必须在0到" + DIALOG_ID_MAX + "之间");
        }
        /** 获取自身在父容器中的索引信息*/
        int index = mChildIndexes.allocateIndex(child, 1, DIALOG_ID_MAX);
        if (index <= 0) {
            throw new RuntimeException("在父容器中查找不到自身!");
        }

        return (index << 16) | dialogId;
    }

    public boolean dispatchBackPressed(BackType backType) {
        int size = mChildren != null ? mChildren.size() : 0;
        for (int i = size - 1; i >= 0; i--) {
            IPresenter child = mChildren.get(i);
            if (child == null) {
                continue;
            }
            boolean handled = child.onBackPressed(backType);
            Log.d("dispatchBackPressed", child.getClass().getSimpleName());
            if (handled) {
                return handled;
            }
        }
        return onBackPressed(backType);
    }

    private void updateTitle(final String title) {
        if (runOnUIThread()) {
            mView.setTitle(title);
        } else {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    mView.setTitle(title);
                }
            });
        }
    }

//    private OnEventListener<String> mTitleListener = new OnEventListener<String>() {
//        @Override
//        public void onEvent(String category, String event) {
//            if (!TextUtils.equals(EventKeys.Common.UPDATE_TITLE, category)) {
//                return;
//            }
//            updateTitle(event);
//        }
//    };
//
//    private OnEventListener<Boolean> mBackVisibleListener = new OnEventListener<Boolean>() {
//        @Override
//        public void onEvent(String category, Boolean event) {
//            if (TextUtils.equals(category, EventKeys.Common.BACK_VISIBILITY)) {
//                updateBackVisible(event);
//            }
//        }
//    };

    private void updateBackVisible(final boolean visible) {
        if (runOnUIThread()) {
            mView.setBackVisible(visible);
        } else {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    mView.setBackVisible(visible);
                }
            });
        }
    }

    private enum PageState {
        NONE, CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED
    }

    private void dispatchLifeCycleWhenAdd(IPresenter child) {
        Bundle arguments = child.mArguments;
        switch (mCurrentState) {
            case CREATED: {
                child.onAdd(arguments);
                break;
            }
            case STARTED: {
                child.onAdd(arguments);
                child.onPageStart();
                break;
            }
            case RESUMED: {
                child.onAdd(arguments);
                child.onPageStart();
                child.onPageResume();
                break;
            }
            case PAUSED: {
                child.onAdd(arguments);
                child.onPageStart();
                child.onPageResume();
                child.onPagePause();
                break;
            }
            case STOPPED: {
                child.onAdd(arguments);
                child.onPageStart();
                child.onPageResume();
                child.onPagePause();
                child.onPageStop();
                break;
            }
        }
    }

    private void dispatchLiftCycleWhenRemove(IPresenter child) {
        switch (mCurrentState) {
            case CREATED: {
                child.onPageStart();
                child.onPageResume();
                child.onPagePause();
                child.onPageStop();
                child.dispatchRemove();
                break;
            }
            case STARTED: {
                child.onPageResume();
                child.onPagePause();
                child.onPageStop();
                child.dispatchRemove();
                break;
            }
            case RESUMED: {
                child.onPagePause();
                child.onPageStop();
                child.dispatchRemove();
                break;
            }
            case PAUSED: {
                child.onPageStop();
                child.dispatchRemove();
            }
            case STOPPED: {
                child.dispatchRemove();
                break;
            }
        }
    }
}
