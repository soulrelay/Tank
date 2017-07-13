package com.sus.tankcommon.base;

import android.text.TextUtils;


import com.sus.tankcommon.utils.GenericHelper;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UI层通用的事件分发逻辑,基于泛型的消息总线
 * 可以通过类别和消息的对象类型进行订阅和分发
 * <p>
 * Created by yuhenghui on 16/8/23.
 */
public final class BaseEventPublisher {

    private static BaseEventPublisher sPublisher;

    private final Map<String, Set<OnEventListener>> mSpreadList = new HashMap<>();
    private final Map<String, Set<OnEventListener>> mNormalList = new HashMap<>();

    private final Map<String, Object> mStrickList = new ConcurrentHashMap<>();

    private BaseEventPublisher() {
    }

    /**
     * 获取到事件分发的单例对象
     *
     * @return
     */
    public static BaseEventPublisher getPublisher() {
        if (sPublisher == null) {
            synchronized (BaseEventPublisher.class) {
                if (sPublisher == null) {
                    sPublisher = new BaseEventPublisher();
                }
            }
        }
        return sPublisher;
    }

    /**
     * 订阅某个类别的事件, 当事件发生时分发到注册的接口上
     *
     * @param category
     * @param l
     * @return
     */
    public boolean subscribe(String category, OnEventListener l) {
        if (TextUtils.isEmpty(category) || l == null) {
            return false;
        }

        /** 根据泛型信息订阅内容*/
        Class clazz = GenericHelper.getGenericTypeArgument(l.getClass(), OnEventListener.class, 0);
        String key = createKey(category, clazz);
        if (TextUtils.isEmpty(key)) {
            return false;
        }

        /** 根据订阅的内容是否接受子类分别放到不同的队列中*/
        Set<OnEventListener> list;
        Map<String, Set<OnEventListener>> map = spreadable(l.getClass()) ? mSpreadList : mNormalList;
        synchronized (map) {
            list = map.get(key);
            if (list == null) {
                list = new LinkedHashSet<>();
                map.put(key, list);
            }
        }

        synchronized (list) {
            if (list.contains(l)) {
                return false;
            } else {
                list.add(l);
                return true;
            }
        }
    }

    /**
     * 粘性订阅，如果已经有发送过消息，则把最好一次的消息内容发送给当前新的订阅者
     *
     * @param category
     * @param l
     * @return
     */
    public boolean subscribeSticky(String category, OnEventListener l) {
        boolean subscribeSuccess = subscribe(category, l);
        // 已注册成功，则发信息给新注册的这个订阅者
        if (subscribeSuccess) {
            synchronized (mStrickList) {
                // 判断是否有发送过消息
                if (mStrickList.containsKey(category)) {
                    // 发送消息给新注册的订阅者
                    l.onEvent(category, mStrickList.get(category));
                }
            }
        }
        return subscribeSuccess;
    }

    /**
     * 取消某个事件的订阅,取消成功返回true
     * 参数检查没通过,或者不存在反注册的监听返回false
     *
     * @param category
     * @param l
     * @return
     */
    public boolean unsubscribe(String category, OnEventListener l) {
        if (TextUtils.isEmpty(category) || l == null) {
            return false;
        }

        /** 根据泛型信息取消订阅 */
        Class clazz = GenericHelper.getGenericTypeArgument(l.getClass(), OnEventListener.class, 0);
        String key = createKey(category, clazz);
        if (TextUtils.isEmpty(key)) {
            return false;
        }

        /** 根据是否订阅子类型,分别从不同的队列取消订阅*/
        Map<String, Set<OnEventListener>> map = spreadable(l.getClass()) ? mSpreadList : mNormalList;
        Set<OnEventListener> list;
        synchronized (map) {
            list = map.get(key);
        }
        if (list == null) {
            return false;
        }

        synchronized (list) {
            return list.remove(l);
        }
    }

    /**
     * 发布一个只包括类别信息的事件到事件分发器
     *
     * @param category
     */
    public void publish(String category) {
        publish(category, null);
    }

    /**
     * 发布一个事件提交到事件分发器
     *
     * @param event
     */
    public void publish(String category, Object event) {
        if (TextUtils.isEmpty(category)) {
            return;
        }

        /** 通过实际分发的类型进行分发*/
        Class clazz = event != null ? event.getClass() : NullEvent.class;
        String key = createKey(category, clazz);

        publishStep(key, category, event, mNormalList);
        publishStep(key, category, event, mSpreadList);

        /** 将内容分发给订阅了父类内容的监听*/
        clazz = clazz.getSuperclass();
        while (clazz != null) {
            key = createKey(category, clazz);
            publishStep(key, category, event, mSpreadList);
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * 会缓存该事件，等新的注册时，下发事件给注册
     * event是发送的给监听者的参数，可以是null
     */
    public void publishSticky(String category) {
        publishSticky(category, null);
    }

    public void publishSticky(String category, Object event) {
        if (TextUtils.isEmpty(category)) {
            return;
        }
        synchronized (mStrickList) {
            // 直接使用category作为key，而不是createKey，因为不同的注册界面，sticky共用一个即可
            Object mapVal = event != null ? event : new NullEvent();
            mStrickList.put(category, mapVal);
        }
        publish(category, event);
    }

    /**
     * 移除sticky数据
     *
     * @param category
     */
    public void removeStickyEvent(String category) {
        synchronized (mStrickList) {
            mStrickList.remove(category);
        }
    }

    public void removeAllStickyEvent() {
        synchronized (mStrickList) {
            mStrickList.clear();
        }
    }

    /**
     * 根据key来分发内容给订阅者
     *
     * @param key
     * @param category
     * @param event
     * @param map
     */
    private void publishStep(String key, String category, Object event,
                             Map<String, Set<OnEventListener>> map) {
        OnEventListener[] listeners;

        synchronized (map) {
            Set<OnEventListener> list = map.get(key);
            listeners = list != null ? list.toArray(new OnEventListener[list.size()]) : null;
        }

        int size = listeners != null ? listeners.length : 0;
        for (int i = 0; i < size; i++) {
            listeners[i].onEvent(category, event);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{spread: ").append(mSpreadList);
        builder.append(", normal: ").append(mNormalList);
        return builder.append("}").toString();
    }

    /**
     * 订阅者是否接受子类型的事件信息
     *
     * @param clazz
     * @return true接受
     */
    private static boolean spreadable(Class clazz) {
        Method onEventMethod;
        try {
            onEventMethod = clazz.getMethod("onEvent", String.class, Object.class);
        } catch (NoSuchMethodException e) {
            /** 查找onEvent方法出错,走默认流程*/
            return true;
        }
        /** 参数数量不是两个,直接走默认流程*/
        Annotation[][] annotations = onEventMethod.getParameterAnnotations();
        if (annotations == null || annotations.length != 2) {
            return true;
        }
        int len = annotations[1] != null ? annotations[1].length : 0;
        for (int i = 0; i < len; i++) {
            if (StrictType.class.isAssignableFrom(annotations[1][i].getClass())) {
                return false;
            }
        }
        return true;
    }

    private static String createKey(String category, Class<? extends Object> clazz) {
        if (TextUtils.isEmpty(category) || clazz == null) {
            return null;
        }
        return category + "@" + clazz.getCanonicalName();
    }

    /**
     * 标识接口,用于接收只有category的事件
     */
    public static final class NullEvent {
        private NullEvent() {
        }
    }


    /**
     * 事件订阅者,分发系统会根据订阅的类别和内容类型进行分发
     *
     * @param <T> 事件内容.事件内容参数可以带上StrictType,
     *            带上StrictType表示当前事件订阅者只接受声明的类型,不接受其子类型
     */
    public interface OnEventListener<T> {
        void onEvent(String category, T event);
    }

    /**
     * 参数标识注解,带这个注解的就表示对于方法只接收完全一致的类型,不接收其子类型
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.TYPE})
    public @interface StrictType {
    }
}
