package com.sus.tank.template.first;

import android.content.Context;
import android.os.Bundle;

import com.sus.tankcommon.base.PresenterGroup;


/**
 * Created by sushuai
 * Date: 17/7/13
 */
public class FirstPresenter extends PresenterGroup<IFirstView> {

    public FirstPresenter(Context context, Bundle arguments) {
        super(context, arguments);
    }

    @Override
    protected void onAdd(Bundle arguments) {
        super.onAdd(arguments);
    }

    @Override
    protected void onPageResume() {
        super.onPageResume();
    }

    @Override
    public boolean onBackPressed(BackType backType) {
        return true;
    }
}
