package com.wfg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-10-17 12:26
 * @since V1.0.0
 */
public class ThreadTest implements Runnable {
    int b = 100;

    synchronized void m1() throws InterruptedException {
        b = 1000;
        Thread.sleep(500);
        System.out.println("m1 : b = " + b);
    }

    synchronized void m2() throws InterruptedException {
        Thread.sleep(250);
        b = 2000;
        System.out.println("m2 : b = " + b);
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadTest threadTest = new ThreadTest();
        Thread t = new Thread(threadTest);
        t.start();
        threadTest.m2();
        System.out.println("main thread b = " + threadTest.b);

        String str = "" + "#" + "123" + "#" + "" + "#" + "" + "6";
        String[] strArr = str.split("#");
        System.out.println(strArr.length);
        System.out.println(Arrays.asList(strArr));
        String s1 = "";
        String s2 = null;
        System.out.println(s1.equals(s2));

        Map<Integer, String> map = new HashMap<>();
        map.put(0, "ID");
        map.put(1, "name");
        map.put(2, "age");
        map.remove(0);
//        Set<Map.Entry<Integer, String>> entries = map.entrySet();
//        entries.stream().forEach(integerStringEntry -> {
//            if(integerStringEntry.equals(0)) {
//                entries.remove(0);
//            }
//        });
        //10
        System.out.println(map);
        System.out.println(map.size());
        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i));
            System.out.println("DIARY");
        }

    }

    @Override
    public void run() {
        try {
            m1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
