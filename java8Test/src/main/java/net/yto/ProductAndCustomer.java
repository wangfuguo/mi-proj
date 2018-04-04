package net.yto;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author 00938658-王富国
 * @date 2017/5/3
 */
public class ProductAndCustomer {

    public static void main(String[] args){

        BigPlate bigPlate = new BigPlate();
        for (int i = 0; i < 10; i++) {
            new Thread(new ThreadAdd(bigPlate)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new ThreadGet(bigPlate)).start();
        }

    }

}

class BigPlate {
    private ArrayBlockingQueue<Object> objDeque = new ArrayBlockingQueue<Object>(5);

    public void putEgg(Object egg) {
        objDeque.add(egg);
        System.out.println("放入鸡蛋");
    }

    public Object getEgg() {
        try {
            Object take = objDeque.take();
            System.out.println("取出鸡蛋");
            return take;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class ThreadAdd implements Runnable {

    private BigPlate bigPlate;

    public ThreadAdd(BigPlate bigPlate) {
        this.bigPlate = bigPlate;
    }

    @Override
    public void run() {
        bigPlate.putEgg(new Object());
    }
}

class ThreadGet implements Runnable {

    private BigPlate bigPlate;

    public ThreadGet(BigPlate bigPlate) {
        this.bigPlate = bigPlate;
    }

    @Override
    public void run() {
        bigPlate.getEgg();
    }
}
