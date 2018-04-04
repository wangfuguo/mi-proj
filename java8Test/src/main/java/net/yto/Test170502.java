package net.yto;

import java.util.*;

/**
 * @author 00938658-王富国
 * @date 2017/5/2
 */
public class Test170502 {

    public static void main(String[] args){
        List<Integer> header = new ArrayList<>();
        List<Long> body = new ArrayList<>();
        List<Integer> list = null;
        for (int i = 10; i <= 10000000; i = i * 10) {
            header.add(i);
            list = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                list.add(new Random().nextInt(1000000));
            }
            body.add(getTimeFor(list));
            body.add(getTimeFor2(list));
            body.add(getTimeForeach(list));
            body.add(getTimeStream(list));
            body.add(getTimePStream(list));
        }

        StringBuilder stringBuilder = new StringBuilder(2000);
        stringBuilder.append("数量        ");
        int len = 12;
        for (Integer i : header) {
//            System.out.print(i);
            stringBuilder.append(i);
            for (int j = 0; j < len - i.toString().length(); j++) {
//                System.out.print(" ");
                stringBuilder.append(" ");
            }
        }
//        System.out.println();
        stringBuilder.append("\n");
        for (int i = 0, sLen = stringBuilder.length(); i < sLen - 3; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\n");
//        int k = 0;
//        for (Long i : body) {
//            stringBuilder.append("优化for");
//            k++;
//            System.out.print(i);
//            for (int j = 0; j < len - i.toString().length(); j++) {
//                System.out.print(" ");
//            }
//            if(k % header.size() == 0) {
//                System.out.println();
//            }
//        }

        for (int i = 0; i < 5; i++) {
            if(i == 0) {
                stringBuilder.append("优化for     ");
            }
            if(i == 1) {
                stringBuilder.append("for         ");
            }
            if(i == 2) {
                stringBuilder.append("foreach     ");
            }
            if(i == 3) {
                stringBuilder.append("stream      ");
            }
            if(i == 4) {
                stringBuilder.append("pStream     ");
            }
            int n = 5;
            for (int j = 0, headerLen = header.size(); j < headerLen; j++) {
                stringBuilder.append(body.get(j * n + i));
                for(int k = 0; k < len - body.get(j * n + i).toString().length(); k++) {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    public static long getTimeFor(List<Integer> list) {
        long startTime = System.nanoTime();
        for (int i = 0, len = list.size(); i < len; i++) {
        }
        long endTime = System.nanoTime();
//        System.out.print(endTime - startTime + "     ");
        return  endTime - startTime;
    }

    public static long getTimeFor2(List<Integer> list) {
        long startTime = System.nanoTime();
        for (int i = 0; i < list.size(); i++) {
        }
        long endTime = System.nanoTime();
//        System.out.print(endTime - startTime + "     ");
        return  endTime - startTime;
    }

    public static long getTimeForeach(List<Integer> list) {
        long startTime = System.nanoTime();
        for (Integer i : list) {
        }
        long endTime = System.nanoTime();
//        System.out.print(endTime - startTime + "     ");
        return  endTime - startTime;
    }

    public static long getTimeStream(List<Integer> list) {
        long startTime = System.nanoTime();
        list.stream().forEach(integer -> {});
        long endTime = System.nanoTime();
//        System.out.print(endTime - startTime + "     ");
        return  endTime - startTime;
    }

    public static long getTimePStream(List<Integer> list) {
        long startTime = System.nanoTime();
        list.parallelStream().forEach(integer -> {});
        long endTime = System.nanoTime();
//        System.out.print(endTime - startTime + "     ");
        return  endTime - startTime;
    }
}
