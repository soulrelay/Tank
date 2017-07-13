package com.sus.tankcommon.warehouse.formtips;

import android.view.ViewGroup;

import com.sus.tankcommon.base.BaseComponent;
import com.sus.tankcommon.base.ComponentParams;
import com.sus.tankcommon.warehouse.formtips.presenter.AbsFormHypeTextPresenter;
import com.sus.tankcommon.warehouse.formtips.view.FormHypeTextImpl;
import com.sus.tankcommon.warehouse.formtips.view.IFormHypeText;


/**
 * 接送机表单最底下的提示文案
 *
 * @author kechanghe
 * @since 2017/3/27
 */

public abstract class AbsFormHypeTextComponent extends BaseComponent<IFormHypeText, AbsFormHypeTextPresenter> {

    @Override
    protected void bind(ComponentParams params, IFormHypeText view, AbsFormHypeTextPresenter presenter) {

    }

    @Override
    protected IFormHypeText onCreateView(ComponentParams params, ViewGroup container) {
        return new FormHypeTextImpl(params.bizCtx);
    }

    protected abstract AbsFormHypeTextPresenter onCreatePresenter(ComponentParams params);
}
