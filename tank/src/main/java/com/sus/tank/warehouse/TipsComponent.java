package com.sus.tank.warehouse;

import android.view.ViewGroup;

import com.sus.tankcommon.base.ComponentParams;
import com.sus.tankcommon.warehouse.tips.AbsTipsComponent;
import com.sus.tankcommon.warehouse.tips.presenter.AbsTipsPresenter;
import com.sus.tankcommon.warehouse.tips.view.ITipsView;


/**
 * Created by sushuai
 * Date: 17/7/13
 */

public class TipsComponent extends AbsTipsComponent {

    @Override
    protected AbsTipsPresenter onCreatePresenter(ComponentParams params) {
        return new TipsPresenter(params.bizCtx);
    }

    @Override
    protected ITipsView onCreateView(ComponentParams params, ViewGroup container) {
        return super.onCreateView(params, container);
    }
}
