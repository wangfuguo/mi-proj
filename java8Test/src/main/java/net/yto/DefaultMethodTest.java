package net.yto;

/**
 * @author 00938658-王富国
 * @date 2017/4/11
 */
public class DefaultMethodTest {

    public static void main(String[] args){

       Student student = new Student();
       student.say();
       //People.sleep();

    }

}

class Student implements People, Chinese {
    @Override
    public void say() {
        People.super.say();
        Chinese.super.say();
    }
}

interface People {
    default void say() {
        System.out.println("说话");
    }

    default void eat() {
        System.out.println("吃饭");
    }

    static void sleep() {
        System.out.println("睡觉");
    }
}

interface Chinese {
    default void say() {
        System.out.println("说汉语");
    }
}
