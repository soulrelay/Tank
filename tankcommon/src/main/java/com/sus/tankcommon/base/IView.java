package com.sus.tankcommon.base;

import android.view.View;

/**
 * 视图统一接口
 * 属于IComponent生产的产品族中的产品
 * <p/>
 * Created by yuhenghui on 16/8/22.
 */
public interface IView {
    /**
     * IView是组件的组成部分,用于提供视图的功能接口
     * 在实际的使用中需要将IView对应的实际视图添加到布局结构中
     * 通过这个接口可以获取到视图,返回类型为android.view.View
     *
     * 在特殊情况下,可以不应返回View,IView的实现直接操作已经有的View
     * 比如:地图,订单服务组件等
     *
     * @return View
     */
    View getView();
}
