package com.sus.tankcommon.base;

import android.os.Bundle;
import android.view.ViewGroup;

import com.sus.tankcommon.component.ComponentFactory;
import com.sus.tankcommon.component.ComponentLoader;

/**
 * Created by yuhenghui on 16/12/20.
 */
public abstract class AbsNormalFragment extends BaseFragment {

    static {
        ComponentLoader.load();
    }

//    @Override
//    protected void onSIDPopulated(String sid) {
//        GlobalContext.setBusinessId(sid);
//    }

    /**
     * 创建一个组件
     *
     * @param type   组件类型
     * @param pageID 页面ID
     * @param <T>    组建类型
     * @return 组件对象
     */
    protected <T extends IComponent> T newComponent(String type, int pageID) {
        return ComponentFactory.get().newComponent(getContext(), type, pageID);
    }

    /**
     * 对组件进行初始化
     *
     * @param comp   组件对象
     * @param type   组件类型
     * @param parent 组件父容器
     * @param pageId 页面ID
     * @param <T>    组件类型
     */
    protected <T extends IComponent> void initComponent(T comp, String type, ViewGroup parent, int pageId) {
        this.initComponent(comp, type, parent, pageId, null);
    }

    /**
     * 对组件进行初始化
     *
     * @param comp   组件对象
     * @param type   组件类型
     * @param parent 组件父容器
     * @param pageId 页面ID
     * @param extras 入口参数
     * @param <T>    组件类型
     */
    protected <T extends IComponent> void initComponent(T comp, String type, ViewGroup parent, int pageId, Bundle extras) {

        ComponentParams params = ComponentParams.from(getContext(), pageId).addConfigParams(type);
        if (extras != null) {
            params.extras.putAll(extras);
        }
        params.add(getActivity()).add(this);
        comp.init(params, parent);
    }
}
