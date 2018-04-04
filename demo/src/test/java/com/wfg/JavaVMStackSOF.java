package com.wfg;

/**
 * 功能描述: VM Args: -Xss128k
 *
 * @author 00938658-王富国
 * @date 2017-09-06 15:59
 * @since V1.0.0
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args){
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }

    }

}
