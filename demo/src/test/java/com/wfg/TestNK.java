package com.wfg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-10-31 11:08
 * @since V1.0.0
 */
public class TestNK {

    private static TestNK test1 = new TestNK();
    private static TestNK test2 = new TestNK();
    private static int i = 0;

    int getI() {
        return i++;
    }

    {
        System.out.println("构造器");
    }

    static {
        System.out.println("静态块");
    }

    public static void main(String[] args){
        TestNK testNK = new TestNK();
        testNK.getI();
        System.out.println();
        TestNK testNK1 = new TestNK();
        testNK1.getI();
        System.out.println(testNK1.getI());
        TestNK testNK2 = new TestNK();
        System.out.println(compare_date("2999-12-31", new Date()));
    }

    public static int compare_date(String DATE1, Date DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = DATE2;
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static int compare_date(Date DATE1, Date DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = DATE1;
            Date dt2 = DATE2;
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public void test(TestNK testNK) {

    }



}
