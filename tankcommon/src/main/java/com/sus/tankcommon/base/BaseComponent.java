package com.sus.tankcommon.base;

import android.view.ViewGroup;

/**
 * 适用于简单场景下的 Component 基类。</p>
 *
 * @author Jin Liang
 * @since 16/8/23
 */

public abstract class BaseComponent<V extends IView, P extends IPresenter> implements
        IComponent {
    private V mView;
    private P mPresenter;

    /**
     * 初始化组件的各个部分(View 和 Presenter)，在使用组件时必须先调用此方法
     *
     * @param params
     * @param container
     */
    @Override
    public void init(ComponentParams params, ViewGroup container) {
        mView = onCreateView(params, container);
        mPresenter = onCreatePresenter(params);
        // 将View绑定到Presenter
        if (mPresenter != null && mView != null) {
            mPresenter.setIView(mView);
        }
        bind(params, mView, mPresenter);
    }

    /**
     * 为view设置各种监听,涉及到业务逻辑的调用Presenter的方法处理
     *
     * @param params
     * @param view
     * @param presenter
     */
    protected abstract void bind(ComponentParams params, V view, P presenter);

    protected abstract V onCreateView(ComponentParams params, ViewGroup container);

    protected abstract P onCreatePresenter(ComponentParams params);

    @Override
    public V getView() {
        return mView;
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }
}
