package com.sus.tankcommon.component;

import android.text.TextUtils;


import com.sus.tankcommon.base.IComponent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 组件池,所有的组件根据类别和名称注册到这里
 * Created by yuhenghui on 16/12/20.
 */
class ComponentPool {

    private Map<String, Map<String, Class<? extends IComponent>>> mComponents = new LinkedHashMap<>();

    /**
     * 注册一个组件到组件池中
     *
     * @param type  组件类型
     * @param name  组件名称
     * @param clazz 组件类型
     */
    public void register(String type, String name, Class<? extends IComponent> clazz) {
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(name) || clazz == null) {
            return;
        }
        Map<String, Class<? extends IComponent>> typeMap;
        synchronized (mComponents) {
            typeMap = mComponents.get(type);
            if (typeMap == null) {
                typeMap = new LinkedHashMap<>();
                mComponents.put(type, typeMap);
            }
        }
        synchronized (typeMap) {
            typeMap.put(name, clazz);
        }
    }

    /**
     * 查询一个组件信息
     *
     * @param type 组件类型
     * @param name 组件名称
     * @return 组件的class对象
     */
    public Class<? extends IComponent> query(String type, String name) {
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(name)) {
            return null;
        }
        Map<String, Class<? extends IComponent>> typeMap;
        synchronized (mComponents) {
            typeMap = mComponents.get(type);
        }
        if (typeMap == null) {
            return null;
        }

        synchronized (typeMap) {
            return typeMap.get(name);
        }
    }
}
