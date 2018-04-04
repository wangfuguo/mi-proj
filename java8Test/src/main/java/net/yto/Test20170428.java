package net.yto;

/**
 * @author 00938658-王富国
 * @date 2017/4/28
 */
public class Test20170428 {

    public static void main(String[] args){

        String ss = "google";
        System.out.println(ss.hashCode());
        System.out.println(ss.hashCode() >>> 16);

    }
}
