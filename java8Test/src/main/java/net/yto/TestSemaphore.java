package net.yto;

import java.util.concurrent.*;

/**
 * 信号量(Semaphore)，有时被称为信号灯，是在多线程环境下使用的一种设施, 它负责协调各个线程, 以保证它们能够正确、合理的使用公共资源。
 * Semaphore分为单值和多值两种，前者只能被一个线程获得，后者可以被若干个线程获得。
 * 单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
 * @author 00938658-王富国
 * @date 2017/5/8
 */
public class TestSemaphore {

    public static void main(String[] args){

        Semaphore semaphore = new Semaphore(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 12, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5));
        threadPoolExecutor.execute(new SemThread(semaphore, 5));
        threadPoolExecutor.execute(new SemThread(semaphore, 3));
        threadPoolExecutor.execute(new SemThread(semaphore, 7));
        threadPoolExecutor.shutdown();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new SemThread(semaphore, 6));
        executorService.execute(new SemThread(semaphore, 2));
        executorService.execute(new SemThread(semaphore, 8));
        executorService.execute(new SemThread(semaphore, 5));
        executorService.shutdown();

        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);
        // 模拟20个客户端访问
        for (int index = 0; index < 50; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 获取许可
                        semp.acquire();
                        System.out.println("Accessing: " + NO);
                        Thread.sleep((long) (Math.random() * 6000));
                        // 访问完后，释放
                        semp.release();
                        //availablePermits()指的是当前信号灯库中有多少个可以被使用
                        System.out.println("-----------------" + semp.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();
    }
}

class SemThread implements Runnable {

    private volatile Semaphore semaphore;
    private Integer count;

    public SemThread(Semaphore semaphore, Integer count) {
        this.semaphore = semaphore;
        this.count = count;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(count);
            System.out.println(Thread.currentThread().getName() + " 获取了 " + count + " 个许可...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(count);
            System.out.println(Thread.currentThread().getName() + " 释放了 " + count + " 个许可...");
        }
    }
}