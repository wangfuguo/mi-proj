package com.wfg;

import java.util.Arrays;

/**
 * 功能描述: 线性查找算法
 *
 * @author 00938658-王富国
 * @date 2017-09-20 9:12
 * @since V1.0.0
 */
public class BFPRT {
    public static void main(String[] args){

        Integer[] arr = {5, 2, 6, 1, 8, 10, 2, 3, 9, 1, 0, 7};
        insertionSort(arr, 0, arr.length - 1);
        bfprt(arr, 0, arr.length - 1, 6);
        System.out.println(Arrays.asList(arr));

    }

    public static int bfprt(Integer[] arr, int left, int right, int id){
        //System.out.println(Arrays.asList(arr));
        if(right - left + 1 <= 5){
            insertionSort(arr, left, right);
            return arr[left + id - 1];
        }

        int t = left - 1;
        for (int st = left, ed; (ed = st + 4) <= right; st += 5) //每5个进行处理
        {
            insertionSort(arr, st, ed); //5个数的排序
            t++;
            swap(arr, t, st+2); //将中位数替换到数组前面，便于递归求取中位数的中位数
        }
        System.out.println(Arrays.asList(arr));
        int pivotId = (left + t) >> 1; //l到t的中位数的下标，作为主元的下标
        bfprt(arr, left, t, pivotId - left + 1);//不关心中位数的值，保证中位数在正确的位置
        int m = partition(arr, left, right, pivotId), cur = m - left + 1;
        if (id == cur){
            return arr[m];                   //刚好是第id个数
        }
        else if(id < cur){
            return bfprt(arr, left, m-1, id);//第id个数在左边
        }
        else{
            return bfprt(arr, m+1, right, id-cur);         //第id个数在右边
        }


    }

    public static int partition(Integer[] arr, int left, int right, int pivotId){
        swap(arr, pivotId, right);
        int j = left - 1;
        for(int i = left; i < right; i++){
            if(arr[i] <= arr[right]){
                swap(arr, ++j, i);
            }
        }
        swap(arr, ++j, right);
        return j;
    }

    public static void insertionSort(Integer[] arr, int first, int last) {
        for (int i = first + 1; i <= last; i++) {
            Integer temp = arr[i];
            int j;
            for(j = i - 1; j >= first; j--) {
                if(temp < arr[j]) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            if(j != i -1) {
                arr[j + 1] = temp;
            }
        }
    }

    public static void swap(Integer[] arr, int x, int y) {
        Integer temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
}
