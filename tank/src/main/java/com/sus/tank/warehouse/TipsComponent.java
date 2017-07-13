package com.sus.tank.warehouse;

import android.view.ViewGroup;

import com.sus.tankcommon.base.ComponentParams;
import com.sus.tankcommon.warehouse.tips.AbsTipsComponent;
import com.sus.tankcommon.warehouse.tips.presenter.AbsTipsPresenter;
import com.sus.tankcommon.warehouse.tips.view.ITipsView;


/**
 * 接送机表单最底下的提示文案
 *
 * @author kechanghe
 * @since 2017/3/27
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
