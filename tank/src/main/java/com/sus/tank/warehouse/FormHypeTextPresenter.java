package com.sus.tank.warehouse;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sus.tankcommon.warehouse.formtips.presenter.AbsFormHypeTextPresenter;


/**
 * Created by chensi on 2017/6/27.
 */

public class FormHypeTextPresenter extends AbsFormHypeTextPresenter {

    public FormHypeTextPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onAdd(Bundle arguments) {
        super.onAdd(arguments);
        mView.setData("11111111111test");
    }


}
