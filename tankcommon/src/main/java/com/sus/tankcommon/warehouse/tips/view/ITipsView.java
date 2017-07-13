package com.sus.tankcommon.warehouse.tips.view;


import com.sus.tankcommon.base.IView;
import com.sus.tankcommon.warehouse.tips.model.ITipsModel;

/**
 * Created by sushuai
 * Date: 17/7/13
 */

public interface ITipsView<T extends ITipsModel> extends IView {

    void setData(String data);
}
