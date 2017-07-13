package com.sus.tank.base;

import android.support.annotation.Keep;

import com.sus.tank.warehouse.TipsComponent;
import com.sus.tankcommon.component.ComponentFactory;
import com.sus.tankcommon.component.Components;


/**
 * 用于注册所有的组件
 * Created by yuhenghui on 16/12/20.
 */
@Keep
final class CompRegister {

    static {
        registerAll();
    }

    @Keep
    static void loadStatic() {
        // do nothing
    }

    private static void registerAll() {
        registerCommon(ComponentFactory.get());
    }

    private static void registerCommon(ComponentFactory factory) {
        factory.registerCommon(Components.Types.TIPS, Components.Names.TIPS_COMMON, TipsComponent.class);


    }
}
