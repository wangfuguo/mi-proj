package com.wfg;

import java.util.*;

/**
 * 功能描述: 快速排序，双向扫描
 *
 * @author 00938658-王富国
 * @date 2017-09-12 10:17
 * @since V1.0.0
 */
public class QuickSort {

    transient static int head;

    public static void main(String[] args){

//        Deque<String> arrDeque = new ArrayDeque<>();
//        Deque<String> linkDeque = new LinkedList<>();
//        String[] arr = new String[10];
//        arr[0] = ("google");
//        System.out.println(arr[0]);
//        System.out.println(arr.length);
//        arr[0] = null;
//        System.out.println(arr.length);
//        System.out.println((9 + 1) & (10 - 1));
//        //(h + 1) & (elements.length - 1)
//        System.out.println((head - 1) & (arr.length - 1));
//        arr[head = (head - 1) & (arr.length - 1)] = "9";
        Integer[] arr = {8, 2, 9, 1, 5, 10, 12, 3, 18, 6};
        System.out.println(Arrays.asList(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.asList(arr));

    }

    public static void quickSort(Integer[] arr, int left, int right) {
        if(left < right) {
            int partition = partition(arr, left, right);
            quickSort(arr, left, partition - 1);
            quickSort(arr, partition + 1, right);
        }
    }

    public static int partition(Integer[] arr, int left, int right) {
        Integer pivot = arr[left];
        int i = left, j = right;
        while (i < j) {
            while (arr[j] >= pivot && i < j) {
                j--;
            }
            arr[i] = arr[j];
            while (arr[i] <= pivot && i < j) {
                i++;
            }
            arr[j] = arr[i];
        }
        arr[i] = pivot;
        return i;
    }
}
