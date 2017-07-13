package com.sus.tank.warehouse;

import android.view.ViewGroup;

import com.sus.tankcommon.base.ComponentParams;
import com.sus.tankcommon.warehouse.formtips.AbsFormHypeTextComponent;
import com.sus.tankcommon.warehouse.formtips.presenter.AbsFormHypeTextPresenter;
import com.sus.tankcommon.warehouse.formtips.view.IFormHypeText;


/**
 * 接送机表单最底下的提示文案
 *
 * @author kechanghe
 * @since 2017/3/27
 */

public class FormHypeTextComponent extends AbsFormHypeTextComponent {

    @Override
    protected AbsFormHypeTextPresenter onCreatePresenter(ComponentParams params) {
        return new FormHypeTextPresenter(params.bizCtx);
    }

    @Override
    protected IFormHypeText onCreateView(ComponentParams params, ViewGroup container) {
        return super.onCreateView(params, container);
    }
}
