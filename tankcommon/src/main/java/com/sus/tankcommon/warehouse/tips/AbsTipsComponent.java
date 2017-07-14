package com.sus.tankcommon.warehouse.tips;

import android.view.ViewGroup;

import com.sus.tankcommon.base.BaseComponent;
import com.sus.tankcommon.component.ComponentParams;
import com.sus.tankcommon.warehouse.tips.presenter.AbsTipsPresenter;
import com.sus.tankcommon.warehouse.tips.view.TipsView;
import com.sus.tankcommon.warehouse.tips.view.ITipsView;


/**
 * Created by sushuai
 * Date: 17/7/13
 */

public abstract class AbsTipsComponent extends BaseComponent<ITipsView, AbsTipsPresenter> {

    @Override
    protected void bind(ComponentParams params, ITipsView view, AbsTipsPresenter presenter) {

    }

    @Override
    protected ITipsView onCreateView(ComponentParams params, ViewGroup container) {
        return new TipsView(params.bizCtx);
    }

    protected abstract AbsTipsPresenter onCreatePresenter(ComponentParams params);
}
