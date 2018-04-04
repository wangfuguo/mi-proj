package com.wfg;

import org.apache.commons.lang.math.RandomUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-08-09 9:42
 * @since V1.0.0
 */
public class Tests {
    public static void main(String[] args){

        List<? extends B> list = new ArrayList<A>();
//        list.add(new B());
//        list.add(new A());
        List<? super A> lists = new ArrayList<A>();
        lists.add(new A());
        lists.add(new C());
        System.out.println();
        List<? extends A> sss = new ArrayList<A>();
        System.out.println(list.getClass() == sss.getClass());
        System.out.println(list.getClass());
        List<String> result = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list2.add("a");
        list2.add("b");
        list1.stream().forEach(s -> {
            list2.stream().forEach(s1 -> {
                if(s.equals(s1)) {
                    result.add(s1);
                }
            });
        });
        for (String s1 : list1) {
            for (String s2 : list2) {
                if(s1.equals(s2)) {
                    result.add(s1);
                }
            }
        }


        try(C c = new C();) {
            c.test();
        } catch (Exception e) {

        }
        I.test();
        System.out.println(String.format("%0" + 5+ "d", 62));
        System.out.println(String.format("%0" + 5 + "d", 9));
        Integer middleLen = 3;
        System.out.println(Integer.parseInt(1 + String.format("%0" + middleLen + "d", 0)));
        String sass = null;
        String s = sass + "a";
        System.out.println(s);
        System.out.println("Y0011".substring(0,1));
        System.out.println("Y002".hashCode() & 5);
        System.out.println(RandomUtils.nextInt(9999));
    }
}

class C extends A implements AutoCloseable {

    public void test() {
        try {
            System.out.println("try。。。");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try(InputStream inputStream = new FileInputStream("D:\\adcfg.json");){
            while (inputStream.read() != -1) {
                System.out.println((char) inputStream.read());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        System.out.println("close method is called！");
    }
}

class A extends B implements I {

}

class B {

}

interface I {
    static void test(){
        System.out.println("ssss");
        return;
    };
}
