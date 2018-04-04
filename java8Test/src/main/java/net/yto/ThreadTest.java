package net.yto;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 00938658-王富国
 * @date 2017/5/3
 */
public class ThreadTest {

    public static volatile AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        Object obj = new Object();

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(10, 18, 2, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.DiscardOldestPolicy());
//        ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
//        threadPoolExecutor.execute(() -> {
//            System.out.println("google");
//        });
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new ThreadB("A", 1, obj));
            threadPoolExecutor.execute(new ThreadB("B", 2, obj));
            threadPoolExecutor.execute(new ThreadB("C", 3, obj));
        }
        threadPoolExecutor.shutdown();

//        new Thread(new ThreadA("A", 1, obj)).start();
//        new Thread(new ThreadA("B", 2, obj)).start();
//        new Thread(new ThreadA("C", 3, obj)).start();

    }
}

class ThreadA implements Runnable {
    private String name;
    private Integer seq;
    private Object lock;

    public ThreadA(String name, Integer seq, Object lock) {
        this.name = name;
        this.seq = seq;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++)
            synchronized (lock) {
                if (ThreadTest.count.get() % 3 == seq - 1) {
                    System.out.println("count:" + (ThreadTest.count.get()) + "  ThreadName : " + name);
                    ThreadTest.count.incrementAndGet();
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}

class ThreadB implements Runnable {
    private String name;
    private Integer seq;
    private Object lock;

    public ThreadB(String name, Integer seq, Object lock) {
        this.name = name;
        this.seq = seq;
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            if (ThreadTest.count.get() % 3 == seq - 1) {
                System.out.println("count:" + (ThreadTest.count.get()) + "  ThreadName : " + name);
                ThreadTest.count.incrementAndGet();
                lock.notifyAll();
            } else {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}