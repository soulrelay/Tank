package com.sus.tankcommon.component;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sus.tankcommon.base.IComponent;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 组件工厂
 * Created by yuhenghui on 16/12/21.
 */
public class ComponentFactory {

    private static final boolean DEBUG = false;
    private static final String TAG = ComponentFactory.class.getCanonicalName();

    private ComponentPool mComponentPool;
    private Map<String, String> mCommons = Collections.synchronizedMap(new LinkedHashMap<String, String>());

    private ComponentFactory() {
        mComponentPool = new ComponentPool();
    }

    /**
     * 注册一个组件到组件池中
     *
     * @param type  组件类型
     * @param name  组件名称
     * @param clazz 组件类型对象
     */
    public void register(String type, String name, Class<? extends IComponent> clazz) {
        mComponentPool.register(type, name, clazz);
    }

    /**
     * 注册一个标准组件到组件池中
     *
     * @param type  组件类型
     * @param name  组件名称
     * @param clazz 组件类型对象
     */
    public void registerCommon(String type, String name, Class<? extends IComponent> clazz) {
        mCommons.put(type, name);
        mComponentPool.register(type, name, clazz);
    }

    /**
     * 创建一个组件对象
     *
     * @param ctx    上下文环境
     * @param type   类型信息
     * @param pageId 页面ID
     * @param <T>    返回对象类型
     * @return 组件对象
     */
    public final <T extends IComponent> T newComponent(Context ctx, String type, int pageId) {
//        /** 如果服务器没有配置组件,直接创建默认的组件*/
//        ComponentConfig cfg = configuredComponent(ctx, sid, type, pageId);
//        /** 如果配置为关闭,直接返回null*/
//        if (cfg != null && !cfg.open()) {
//            return null;
//        }
        /** 如果配置了组件名称,先尝试从外部加载*/
        String cmpName = mCommons.get(type);
        T component = null;
        if (component != null) {
            return component;
        }
        /** 插件没有提供组件时,创建内部组件,配置的内部组件为空时,使用默认内部组件*/
        component = newInnerComponent(type, cmpName);
        if (component != null) {
            return component;
        }
        if (!TextUtils.equals(cmpName, mCommons.get(type))) {
            return newInnerComponent(type, cmpName);
        } else {
            return null;
        }
    }

    /**
     * 创建一个某种类型的组件
     *
     * @param type 类型信息
     * @param <T>  返回对象类型
     * @return 组件对象
     */
    @SuppressWarnings("unchecked")
    private <T extends IComponent> T newInnerComponent(String type, String cmpName) {
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(cmpName)) {
            return null;
        }
        Class clazz = mComponentPool.query(type, cmpName);
        if (clazz != null) {
            try {
                return (T) clazz.newInstance();
            } catch (InstantiationException e) {
                if (DEBUG) {
                    Log.i(TAG, "组件初始化异常", e);
                }
            } catch (IllegalAccessException e) {
                if (DEBUG) {
                    Log.i(TAG, "组件类型访问异常", e);
                }
            } catch (ClassCastException e) {
                if (DEBUG) {
                    Log.i(TAG, "组件类型转化异常", e);
                }
            }
        } else {
            if (DEBUG) {
                Log.i(TAG, "组件类型不存在");
            }
        }
        return null;
    }


    //    /**
//     * 根据业务线和页面ID创建插件内部提供的组件
//     *
//     * @param sid    业务线ID
//     * @param pageId 页面ID
//     * @return 通过动态加载创建的组件
//     */
    @SuppressWarnings("unchecked")
//    private <T extends IComponent> T newPluginComponent(Context ctx, String sid, String type, String cmpName, int pageId) {
//        if (TextUtils.equals(sid, SidConverter.SID_SOFA) || TextUtils.equals(sid, SidConverter.SID_OFO)) {
//            IPluginEntrance entrance = PluginManager.get(ctx).queryPluginEntrance(sid);
//            if (entrance == null) {
//                return null;
//            }
//
//            try {
//                return (T) entrance.newComponent(type, cmpName, sid, pageId);
//            } catch (ClassCastException e) {
//                if (DEBUG) {
//                    Log.e(TAG, "组件类型转化异常", e);
//                }
//            } catch (Throwable e) {
//                if (DEBUG) {
//                    Log.e(TAG, "创建组件异常", e);
//                }
//            }
//        }
//
//        return null;
//    }


    /**
     * 查询组件的配置信息
     *
     * @param sid    业务线ID
     * @param type   组件类型
     * @param pageId 页面ID
     * @return 组件的配置信息
     */
//    private ComponentConfig configuredComponent(Context ctx, String sid, String type, int pageId) {
//        ComponentConfig config = ComponentsConfig.get(ctx).queryConfig(type, sid, pageId);
//        if (config != null && config.valid() && TextUtils.equals(type, config.type())) {
//            return config;
//        } else {
//            return null;
//        }
//    }

    /**
     * 获取新业务线衍生于业务线的sid
     *
     * @return
     */
//    public String getParentSid(Context ctx, String sid) {
//        return ComponentsConfig.get(ctx).subsid2sid(sid);
//    }
//
//    public int sid2Bid(Context ctx, String subSid) {
//        return ComponentsConfig.get(ctx).subsid2bid(subSid);
//    }

    public static ComponentFactory get() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final ComponentFactory INSTANCE = new ComponentFactory();
    }
}