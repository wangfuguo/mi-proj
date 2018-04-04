package com.wfg;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-09-06 16:52
 * @since V1.0.0
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args){

        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }

    }
}
