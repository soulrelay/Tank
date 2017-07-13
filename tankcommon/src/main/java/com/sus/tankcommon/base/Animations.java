package com.sus.tankcommon.base;

import android.support.annotation.AnimRes;

import com.sus.tankcommon.R;

/**
 * Created by yuhenghui on 16/10/21.
 */
public class Animations {

    private int mEnterAnim;
    private int mExitAnim;
    private int mEnterPopAnim;
    private int mExitPopAnim;

    public Animations() {
        mEnterAnim = R.anim.base_fragment_enter;
        mExitAnim = R.anim.base_fragment_exit;
        mEnterPopAnim = R.anim.base_fragment_pop_enter;
        mExitPopAnim = R.anim.base_fragment_pop_exit;
    }

    public Animations(int enter, int exit, int enterPop, int exitPop) {
        mEnterAnim = enter;
        mExitAnim = exit;
        mEnterPopAnim = enterPop;
        mExitPopAnim = exitPop;
    }

    /**
     * @return Fragment 进入动画
     */
    @AnimRes
    public int enterAnim() {
        return mEnterAnim;
    }

    /**
     * 设置进入动画
     *
     * @param enter
     */
    public void setEnterAnim(@AnimRes int enter) {
        mEnterAnim = enter;
    }

    /**
     * @return Fragment 退出动画
     */
    @AnimRes
    public int exitAnim() {
        return mExitAnim;
    }

    /**
     * 设置退出动画
     *
     * @param exit
     */
    public void setExitAnim(int exit) {
        mExitAnim = exit;
    }

    /**
     * @return Fragment Pop 进入动画
     */
    @AnimRes
    public int enterPopAnim() {
        return mEnterPopAnim;
    }

    /**
     * 设置enter pop动画
     *
     * @param enterPop
     */
    public void setEnterPopAnim(@AnimRes int enterPop) {
        mEnterPopAnim = enterPop;
    }

    /**
     * @return Fragment Pop退出动画
     */
    @AnimRes
    public int exitPopAnim() {
        return mExitPopAnim;
    }

    /**
     * 设置exit pop动画
     *
     * @param exitPop
     */
    public void setExitPopAnim(@AnimRes int exitPop) {
        mExitPopAnim = exitPop;
    }
}
