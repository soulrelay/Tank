package com.sus.tankcommon.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 页面切换的接口,实现页面的跳转和回退
 */
public interface IPageSwitcher {
    /**
     * 前进到下一个页面
     *
     * @param clazz      Fragment的具体实现类
     * @param args       传递给下一个页面的参数
     * @param animations 转场动画
     * @return
     */
    boolean forward(Class<? extends Fragment> clazz, Bundle args, Animations animations);

    /**
     * 前进到下一个页面
     *
     * @param clazz Fragment的具体实现类
     * @param args  传递给下一个页面的参数
     * @return
     */
    boolean forward(Class<? extends Fragment> clazz, Bundle args);

    /**
     * 前进到下一个页面
     *
     * @param intent     封装下个页面的信息
     * @param animations 转场动画
     * @return
     */
    boolean forward(Intent intent, Animations animations);

    /**
     * 前进到下一个页面
     *
     * @param intent 封装下个页面的信息
     * @return
     */
    boolean forward(Intent intent);

    /**
     * 回退到上一个页面
     *
     * @return true回退成功
     */
    boolean goBack();

    /**
     * 回退到上一个页面
     *
     * @param args 参数信息
     * @return
     */
    boolean goBack(Bundle args);

    /**
     * 回退到根页面
     *
     * @return true回退成功
     */
    boolean goBackRoot();

    /**
     * 回退到根页面
     *
     * @param args 参数信息
     * @return
     */
    boolean goBackRoot(Bundle args);

    /**
     * 返回页面切换器所依附的Fragment
     *
     * @return
     */
    Fragment getHost();

    /**
     * 启动一个Activity
     *
     * @param intent
     * @param requestCode
     * @param options
     */
    void startActivityForResult(Intent intent, int requestCode, Bundle options);
}
