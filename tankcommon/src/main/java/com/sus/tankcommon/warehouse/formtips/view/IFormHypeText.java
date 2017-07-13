package com.sus.tankcommon.warehouse.formtips.view;


import com.sus.tankcommon.base.IView;
import com.sus.tankcommon.warehouse.formtips.model.IHypeTextModel;

import java.util.List;

/**
 * 接送机表单最底下的提示文案
 *
 * @author kechanghe
 * @since 2017/3/27
 */

public interface IFormHypeText<T extends IHypeTextModel> extends IView {

    void setData(String data);
}
