package net.yto;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author 00938658-王富国
 * @date 2017/5/8
 */
public class Test170508 {
    public static void main(String[] args){
        Class aClass = Integer.TYPE;
        System.out.println(Arrays.toString(aClass.getGenericInterfaces()));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("a", "A");
        hashMap.put("b", "B");
        hashMap.put("c", "C");
        hashMap.forEach((s, s2) -> {});
        System.out.println(getHash("ss"));
        System.out.println("ss".hashCode());
        System.out.println(getHash("google"));
        System.out.println("google".hashCode());
        System.out.println(getHash(1L));
        System.out.println(new Long(1L).hashCode());
        System.out.println(getHash(Integer.MAX_VALUE + 5L));
        System.out.println(new Long(Integer.MAX_VALUE + 5L).hashCode());
        System.out.println(getHash(new Integer(Integer.MAX_VALUE >>> 1)));
        System.out.println(new Integer(Integer.MAX_VALUE >>> 1).hashCode());
        System.out.println(getHash(new Integer(65536)));
        System.out.println(new Integer(65536).hashCode());
        System.out.println(65536 >> 1);
        System.out.println(-65536 >> 1);
        System.out.println(-65536 >>> 1);

        System.out.println(tableSizeFor(Integer.MAX_VALUE  >> 2));

    }

    public static int getHash(Object key) {
        int h = 0;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static final int tableSizeFor(int cap) {
        int MAXIMUM_CAPACITY = 1 << 30;
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
