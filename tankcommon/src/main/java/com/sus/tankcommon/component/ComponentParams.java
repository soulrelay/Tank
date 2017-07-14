package com.sus.tankcommon.component;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * 组件初始化参数
 * 以对象的形式提供,保证组件接口参数可以保持不变
 */
public class ComponentParams {
    public Context bizCtx;
    private WeakReference<Activity> activity;
    private WeakReference<Fragment> fragment;
    public int pageID;

    /**
     * 通过参数构建一个组件初始化参数
     *
     * @param bizCtx 上下文环境
     * @param pageID 页面ID
     * @return 初始化参数
     */
    public static ComponentParams from(Context bizCtx, int pageID) {
        ComponentParams params = new ComponentParams();
        params.bizCtx = bizCtx;
        params.pageID = pageID;
        return params;
    }

    /**
     * 设置上下文环境
     *
     * @param bizCtx 上下文环境
     * @return 自身
     */
    public ComponentParams add(Context bizCtx) {
        this.bizCtx = bizCtx;
        return this;
    }

    /**
     * 设置组件的Activity信息
     *
     * @param activity
     * @return
     */
    public ComponentParams add(Activity activity) {
        this.activity = new WeakReference<>(activity);
        return this;
    }

    /**
     * 获取Activity对象
     *
     * @return Activity对象
     */
    public Activity getActivity() {
        return activity != null ? activity.get() : null;
    }

    /**
     * 添加Fragment项目
     *
     * @param fragment Fragment对象
     */
    public ComponentParams add(Fragment fragment) {
        this.fragment = new WeakReference<>(fragment);
        return this;
    }

    /**
     * 获取Fragment实例
     *
     * @return
     */
    public Fragment getFragment() {
        return fragment != null ? fragment.get() : null;
    }


    /**
     * 设置页面ID
     *
     * @param pageID 页面ID
     * @return 自身
     */
    public ComponentParams add(int pageID) {
        this.pageID = pageID;
        return this;
    }
}
