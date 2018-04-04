package net.yto;

import java.util.HashMap;

/**
 * @author 00938658-王富国
 * @date 2017/5/3
 */
public class Test170503 {

    public static void main(String[] args){

        System.out.println(getHash("test"));
        System.out.println(getHash2("test"));
        System.out.println(6 + (6 >> 1));

        HashMap<String, String> hashMap = new HashMap(2, 2);
        hashMap.put("A", "a");
        hashMap.put("B", "b");
        hashMap.put("C", "c");
        hashMap.put("D", "d");
        hashMap.put("E", "e");
        hashMap.put("F", "f");

    }

    public static int getHash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static int getHash2(Object key) {
        return (key == null) ? 0 : (key.hashCode()) ^ (key.hashCode() >>> 16);
    }
}
