package net.yto;

import java.util.Arrays;

/**
 * @author 00938658-王富国
 * @date 2017/5/8
 */
public class MergeSort {

    public static void mergeSort(int[] arr, int left, int right) {
        if(left < right) {
            int mid = (left + right) >> 1;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        for (; i <= mid && j <= right;) {
            if(arr[i] <= arr[j]) {
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }
        while (i <= mid) {
            temp[k] = arr[i];
            i++;
            k++;
        }
        while (j <= right) {
            temp[k] = arr[j];
            j++;
            k++;
        }
        for (int n = 0; n < temp.length; n++) {
            arr[left + n] = temp[n];
        }
    }

    public static void main(String[] args){

        int[] arr = new int[]{10, 5, 8, 2, 3, 6, 1, 9, -8};
        System.out.println(Arrays.toString(arr));
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

    }
}
