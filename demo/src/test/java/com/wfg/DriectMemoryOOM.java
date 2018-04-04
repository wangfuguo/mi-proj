package com.wfg;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-09-07 15:54
 * @since V1.0.0
 */
public class DriectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {

        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe)unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }

    }

}
