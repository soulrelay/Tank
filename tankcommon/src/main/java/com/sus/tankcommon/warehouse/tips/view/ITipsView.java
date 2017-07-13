package com.sus.tankcommon.warehouse.tips.view;


import com.sus.tankcommon.base.IView;
import com.sus.tankcommon.warehouse.tips.model.ITipsModel;

/**
 * 接送机表单最底下的提示文案
 *
 * @author kechanghe
 * @since 2017/3/27
 */

public interface ITipsView<T extends ITipsModel> extends IView {

    void setData(String data);
}
