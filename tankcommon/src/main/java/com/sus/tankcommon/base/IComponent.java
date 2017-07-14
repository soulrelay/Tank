package com.sus.tankcommon.base;

import android.support.annotation.Keep;
import android.view.ViewGroup;

import com.sus.tankcommon.component.ComponentParams;

/**
 * 组件统一接口
 * 这是抽象工厂的接口
 * 每个具体的组件会有自己的一个IComponent实现
 * 每个具体的实现能够生产一个IView和一个IPresenter组成的产品族
 */
@Keep
public interface IComponent<V extends IView, P extends IPresenter> {

    /**
     * 对IComponent进行初始化
     * IComponent内部对应的IView和IPresenter在这里创建
     * IView和IPresenter之间的关联也在这里实现
     *
     * @param params    初始化参数对象
     * @param container 组件View的父容器
     */
    void init(ComponentParams params, ViewGroup container);

    /**
     * 生产一个IView对象,用于外部使用
     * 也可以为空,直接操作外部视图
     *
     * @return View
     */
    V getView();

    /**
     * 生产一个IPresenter对象,用于操作IView和Model之间的交互
     *
     * @return Presenter
     */
    P getPresenter();
}
