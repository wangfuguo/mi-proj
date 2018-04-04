package com.wfg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-02 8:49
 * @since V1.0.0
 */
public class Test1102 {

    final static int a = 10 * 20;

    public static void main(String[] args){
        final int aa = 10 * 20;
        BB bb = new BB();
        BB bb2 = new BB();
        Map map = new HashMap<String, String>();
        Map<String, String> map2 = new HashMap<String, String>();
        map.put("ss", "ss");
        List<? extends Object> list = new ArrayList<String>();
        List<?> lists = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        char c = 's';
        System.out.println(String.valueOf(c));
        AAA aaa = new BBB();
        System.out.println(aaa.name);



//        System.out.println(BB.m);

    }
}

interface SS {
    public static int m = 100;
}

class AA {

    public static int m = 10;
    
    static {
        System.out.println(m);
        n = 2000;
        System.out.println("AA static block！");
    }

    public static int n = 200;


    {
        System.out.println("AA()");
    }

}

class BB extends AA implements SS {

    private static BB bb = new BB();

    public BB() {
        System.out.println("BB()");
    }
    
    static {
        System.out.println("静态代码块!");
    }

    {
        System.out.println("不带签名");
    }

    void getAA() {

    }
}

class AAA {
    public String name = "AAA";
    void kkk(){
        System.out.println("AAA:KKK");
    }
}

class BBB extends AAA {
    public String name = "BBB";
    @Override
    void kkk() {
        System.out.println("BBB:KKK");
    }
}
