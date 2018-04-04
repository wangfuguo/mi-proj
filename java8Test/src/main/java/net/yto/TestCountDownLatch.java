package net.yto;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 00938658-王富国
 * @date 2017/5/8
 */
public class TestCountDownLatch {
    public static void main(String[] args){

        CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new CDLThread(countDownLatch, 3000));
        executorService.execute(new CDLThread(countDownLatch, 5000));
        executorService.shutdown();
        new Thread(() -> {
            try {
                System.out.println("another thread is stat at " + LocalDateTime.now().toString());
                Thread.sleep(8000);
                System.out.println("another thread is over at " + LocalDateTime.now().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            countDownLatch.await();
            System.out.println("all countDownLatch thread is over at " + LocalDateTime.now().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main is over");
    }
}

class CDLThread implements Runnable {

    private CountDownLatch countDownLatch;
    private int workTime;

    public CDLThread(CountDownLatch countDownLatch, int workTime) {
        this.countDownLatch = countDownLatch;
        this.workTime = workTime;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start at " + LocalDateTime.now().toString());
        try {
            Thread.sleep(workTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " end at " + LocalDateTime.now().toString());
            countDownLatch.countDown();
        }
    }
}
