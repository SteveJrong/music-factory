package com.stevejrong.music.factory.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class GenericsUtil {
    /**
     * 通过反射获取定义Class时，声明的父类的范型参数的类型
     *
     * @param clazz
     * @return 返回第一个类型
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射获取定义Class时，声明的父类的范型参数的类型
     *
     * @param clazz
     * @param index 返回某下标的类型
     * @return
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
