package com.wfg;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-09-30 10:02
 * @since V1.0.0
 */
public class BWTest {
    public static void main(String[] args){

        Integer[] arr = {1, 2, 3, 4, 5, 6};
//        try {
//            for (int i = 0, len = arr.length; i < len; i++) {
//                if(arr[i] == 4) {
//                    throw new RuntimeException("4 is not friendly");
//                } else {
//                    System.out.println(arr[i]);
//                }
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
        for (int i = 0, len = arr.length; i < len; i++) {
            try {
                if(arr[i] == 4) {
                    throw new RuntimeException("4 is not friendly");
                } else {
                    System.out.println(arr[i]);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println();

    }
}
