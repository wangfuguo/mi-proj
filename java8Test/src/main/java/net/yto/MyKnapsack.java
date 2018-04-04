package net.yto;

/**
 * @author 00938658-王富国
 * @date 2017/5/9
 */
public class MyKnapsack {
    /**
      * w[i] : 第i个物体的重量；
      * p[i] : 第i个物体的价值；
      * c[i][m] ：前i个物体放入容量为m的背包的最大价值；
      * c[i-1][m] ：前i-1个物体放入容量为m的背包的最大价值；
      * c[i-1][m-w[i]] ： 前i-1个物体放入容量为m-w[i]的背包的最大价值；
      * 由此可得：
      * c[i][m]=max{c[i-1][m-w[i]]+pi , c[i-1][m]}（此时用到递归）
      * 令V(i,j)表示在前i(1<=i<=n)个物品中能够装入容量为就j(1<=j<=C)的背包中的物品的最大价值，则可以得到如下的动态规划函数:
      * (1)   V(i,0)=V(0,j)=0
      * (2)   V(i,j)=V(i-1,j)  j<wi
      * V(i,j)=max{V(i-1,j) ,V(i-1,j-wi)+vi) }j>wi
     */
    public static int knapsack(int[] wt, int[] val, int maxWt) {
        int itemLength = wt.length;
        int[][] maxVal = new int[itemLength + 1][maxWt + 1];
        for (int i = 0; i <= itemLength; i++) {
            maxVal[i][0] = 0;
        }
        for (int i = 0; i <= maxWt; i++) {
            maxVal[0][i] = 0;
        }
        for (int item = 1; item <= itemLength; item++) {
            for (int weight = 1; weight <= maxWt; weight++) {
                if(wt[item - 1] <= weight) {
                    maxVal[item][weight] = Math.max(maxVal[item - 1][weight - wt[item - 1]] + val[item - 1],
                            maxVal[item - 1][weight]);
                } else {
                    maxVal[item][weight] = maxVal[item - 1][weight];
                }
            }
        }
        for (int[] rows : maxVal) {
            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }
        return maxVal[itemLength][maxWt];
    }

    public static void testMatriexLength() {
        int[][] matriex = new int[10][8];
        System.out.println(matriex.length);
    }

    public static void main(String[] args){
//        testMatriexLength();
//        int val[] = {10, 40, 30, 50};
//        int wt[] = {5, 4, 6, 3};
//        int W = 10;
//        System.out.println(knapsack(wt, val, W));
        int wt[] = {1024, 1024, 2048, 1024, 4096, 3072, 2048, 1024};
        int val[] = new int[wt.length];
        int sum = 0;
        for (int i = 0; i < wt.length; i++) {
            val[i] = wt[i]/1024;
            sum += val[i];
        }
        int W = (int)Math.ceil(((double) sum) / 2);
        System.out.println(knapsack(val, val, W));
    }
}
