package net.yto;

/**
 * @author 00938658-王富国
 * @date 2017/4/11
 */
public class LambdaTest {

    public static void main(String[] args){

        LambdaTest tester = new LambdaTest();

        final String name = "google";

        MathOperation addition = ( x,  y) -> x + y;
        addition.operation(5, 8);
        MathOperation multiplication = (x, y) -> {
//            name = name + ".com";
            return x * y;
        };
        int addResult = addition.operation(1, 2);
        System.out.println(addResult);

        System.out.println("10 + 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 * 5 = " + tester.operate(10, 5, (x, y) ->{
            return x * y;
        }));

        new Thread(new Runnable() {
            @Override
            public void run() {
               System.out.println("");
            }
        }).start();
        new Thread(() -> {System.out.println("");}).start();

    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

    private int substraction(int a, int b, MathOperation mathOperation) {
        return mathOperation.subtraction(a, b);
    }

}

@FunctionalInterface
interface MathOperation {

    int operation(int a, int b);


    default int subtraction(int a, int b) {
        return a - b;
    }

}


interface MathOperation2 {
    int operation(int b);
}
