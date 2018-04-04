package net.yto;

/**
 * @author 00938658-王富国
 * @date 2017/4/17
 */
public class Test20170417 {

    public static void main(String[] args){
        Test20170417 test20170417 = new Test20170417();
        System.out.println(test20170417.getFucntionName(Test201704172.class));
    }

    public <A extends FunctionalTest> String getFucntionName(Class<A> type) {
        return type.getSimpleName();
    }
}

class Test201704172 extends FunctionalTest {

}
