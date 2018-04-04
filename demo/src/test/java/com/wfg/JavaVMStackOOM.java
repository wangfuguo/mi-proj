package com.wfg;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-09-06 16:23
 * @since V1.0.0
 */
public class JavaVMStackOOM {

    private void dontStop() {
        while (true) {

        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(() ->{
                dontStop();
            });
            thread.start();
        }
    }

    public static void main(String[] args){
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
