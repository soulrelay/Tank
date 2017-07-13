package com.sus.tankcommon.component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yuhenghui on 16/12/29.
 */
public  class ComponentLoader {

    private static final String REGISTER_NAME = "com.sus.tank.base.CompRegister";
    private static final String METHOD_NAME = "loadStatic";

   public static void load() {
        try {
            loadImpl();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void loadImpl() throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        Class clazz = ComponentLoader.class.getClassLoader().loadClass(REGISTER_NAME);
        if (clazz != null) {
            Method method = clazz.getDeclaredMethod(METHOD_NAME);
            if (method != null) {
                method.setAccessible(true);
                method.invoke(null);
            }
        }
    }
}
