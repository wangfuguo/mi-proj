package net.yto;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author 00938658-王富国
 * @date 2017/5/3
 */
public class HashMapTest {

    public static void main(String[] args){

        HashMap<Apple, Integer> map = new HashMap<>();
        Apple apple = new Apple("a", "red");
        System.out.println(apple.hashCode());
        map.put(apple, 1);
        apple.setColor("green");
        System.out.println(apple.hashCode());
        System.out.println(map.get(apple));

        String str = "你好";
        try {
            String s = new String(str.getBytes("GB2312"), "utf-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}

class Apple {
    private String name;
    private String color;

    public Apple(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apple)) return false;

        Apple apple = (Apple) o;

        if (getName() != null ? !getName().equals(apple.getName()) : apple.getName() != null) return false;
        return getColor() != null ? getColor().equals(apple.getColor()) : apple.getColor() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        return result;
    }
}
