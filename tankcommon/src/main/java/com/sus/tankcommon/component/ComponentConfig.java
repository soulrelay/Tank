package com.sus.tankcommon.component;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 单个组件的配置信息
 * <p/>
 * Created by yuhenghui on 16/11/9.
 */
public class ComponentConfig {
    /** ios为cid android 通过 type 和 name 获取Component **/
    private static final String KEY_TYPE_STRING = "cid"; // android type
    /** ios cname 为 unit(为业务逻辑 对应android presenter) android 为组件名称 **/
    private static final String KEY_NAME_STRING = "cname";
    private static final String KEY_OPEN_BOOLEAN = "open";
    private static final String KEY_DESC_STRING = "desc";
    private static final String KEY_PARAMS_STRING = "params";

    Component component;
    boolean open; // 默认状态
    String desc; // 描述信息
    String params; // 默认参数

    /**
     * 获取组件的类型
     *
     * @return 类型
     */
    public String type() {
        return component.type;
    }

    /**
     * 获取组件的名称
     *
     * @return 名称
     */
    public String name() {
        return component.name;
    }

    /**
     * 获取组件的状态
     *
     * @return 状态
     */
    public boolean open() {
        return open;
    }

    /**
     * 获取组件的描述
     *
     * @return 组件的描述信息
     */
    public String desc() {
        return desc;
    }

    /**
     * 获取组件的入口参数
     *
     * @return 入口参数, 由组件工厂创建组件时解析和传递
     */
    public String params() {
        return params;
    }

    /**
     * 对数据的有效性进行校验
     *
     * @return true:有效数据
     */
    public boolean valid() {
        return component != null && !TextUtils.isEmpty(component.type)
                && !TextUtils.isEmpty(component.name);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ ").append(KEY_TYPE_STRING).append(":").append(component.type);
        builder.append(", ").append(KEY_NAME_STRING).append(":").append(component.name);
        builder.append(", ").append(KEY_OPEN_BOOLEAN).append(":").append(open);
        builder.append(", ").append(KEY_DESC_STRING).append(":").append(desc);
        builder.append(", ").append(KEY_PARAMS_STRING).append(":").append(params);
        builder.append("}");
        return builder.toString();
    }

    /**
     * 解析一个组件的配置信息
     *
     * @param json 单个组件的配置信息
     * @return 组件的对象化信息
     */
    static ComponentConfig fromJSON(JSONObject json) throws JSONException {
        if (json == null) {
            return null;
        }
        /** 必须要有类型信息,没有类型信息认为是格式问题*/
        int type = json.optInt(KEY_TYPE_STRING);
        if (type <= 0) {
            throw new JSONException("unknown component type: " + type);
        }
        /** 必须要有名称信息,如果没有认为是格式问题*/
        String name = json.getString(KEY_NAME_STRING);
        if (TextUtils.isEmpty(name)) {
            throw new JSONException("unknown component name: " + name);
        }
        ComponentConfig config = new ComponentConfig();
        config.component = new ComponentConfig().new Component();
        config.component.type = config.component.reverse(type);
        config.open = json.optInt(KEY_OPEN_BOOLEAN) == 1 ? true : false;
        config.desc = json.optString(KEY_DESC_STRING, null);
        config.params = json.optString(KEY_PARAMS_STRING, null);
        return config;
    }

    /**
     * android 通过 type 和 name 获取component
     */
    final class Component {
        String type; //类型信息,如:banner
        String name; //名称信息,如:banner_common

        String reverse(int cid) {
            TYPE comType = TYPE.get(cid);
            if (comType == null) {
                return null;
            }
            switch (comType) {
                case TIPS: {
                    type = Components.Types.TIPS;
                    name = Components.Names.TIPS_COMMON;
                    break;
                }
                default: {
                    break;
                }
            }

            return type;
        }

    }

    private enum TYPE {
        TIPS(3);

        TYPE (int type) {
            value = type;
        }

        int value;

        private static Map<Integer, TYPE> types = new HashMap<Integer, TYPE>();
        static {
            for (TYPE t : values()) {
                types.put(t.value, t);
            }
        }

        public static TYPE get(int cid) {
            return types.get(cid);
        }
    }
}
