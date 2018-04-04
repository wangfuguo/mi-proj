package net.yto;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author 00938658-王富国
 * @date 2017/5/9
 */
public class Test170509 {
    public static void main(String[] args){

        boolean userRequested = LegacyMergeSort.userRequested;
        System.out.println(userRequested);

        Integer[] arr = {10, 8, 5, 6, 2, 3};
        Collections.sort(Arrays.asList(arr));
        System.out.println(Arrays.asList(arr).toString());

    }

    static class LegacyMergeSort {
        private static final boolean userRequested =
                java.security.AccessController.doPrivileged(
                        new sun.security.action.GetBooleanAction(
                                "java.util.Arrays.useLegacyMergeSort")).booleanValue();
    }
}
