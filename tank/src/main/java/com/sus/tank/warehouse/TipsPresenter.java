package com.sus.tank.warehouse;

import android.content.Context;
import android.os.Bundle;

import com.sus.tankcommon.warehouse.tips.presenter.AbsTipsPresenter;

/**
 * Created by sushuai
 * Date: 17/7/13
 */
public class TipsPresenter extends AbsTipsPresenter {

    public TipsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onAdd(Bundle arguments) {
        super.onAdd(arguments);
        mView.setData("我是组件1");
    }


}
