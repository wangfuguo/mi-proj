package net.yto;

/**
 * @author 00938658-王富国
 * @date 2017/5/5
 */
public class Knapsack {

    private static int V[][] = new int[200][200];//前i个物品装入容量为j的背包中获得的最大价值

    static int max(int a,int b)  //一个大小比较函数，用于当总重大于第I行时
    {
        if(a>=b)
            return a;
        else return b;
    }

    static int knap(int n,int w[],int v[],int x[],int C)
    {
        int i,j;
        for(i=0;i<=n;i++)
            V[i][0]=0;
        for(j=0;j<=C;j++)
            V[0][j]=0;
        for(i=1;i<=n-1;i++)
            for(j=0;j<=C;j++)
                if(j<w[i])
                    V[i][j]=V[i-1][j];
                else
                    V[i][j]=max(V[i-1][j],V[i-1][j-w[i]]+v[i]);
        j=C;
        for(i=n-1;i>0;i--)
        {
            if(V[i][j]>V[i-1][j])
            {
                x[i]=1;
                j=j-w[i];
            }
            else
                x[i]=0;
        }
        System.out.print("选中的物品是:\n");
        for(i=0;i<n;i++)
            System.out.format("%d ",x[i]);
        System.out.print("\n");
        return V[n-1][C];
    }

    public static int knapsack(int val[], int wt[], int W) {
        //Get the total number of items.
        //Could be wt.length or val.length. Doesn't matter
        int N = wt.length;

        //Create a matrix.
        //Items are in rows and weight at in columns +1 on each side
        int[][] V = new int[N + 1][W + 1];

        //What if the knapsack's capacity is 0 - Set
        //all columns at row 0 to be 0
        for (int col = 0; col <= W; col++) {
            V[0][col] = 0;
        }

        //What if there are no items at home.
        //Fill the first row with 0
        for (int row = 0; row <= N; row++) {
            V[row][0] = 0;
        }

        for (int item=1;item<=N;item++){
            //Let's fill the values row by row
            for (int weight=1;weight<=W;weight++){
                //Is the current items weight less
                //than or equal to running weight
                if (wt[item-1]<=weight){
                    //Given a weight, check if the value of the current
                    //item + value of the item that we could afford
                    //with the remaining weight is greater than the value
                    //without the current item itself
                    V[item][weight]=Math.max(val[item-1]+V[item-1][weight-wt[item-1]], V[item-1][weight]);
                } else {
                    //If the current item's weight is more than the
                    //running weight, just carry forward the value
                    //without the current item
                    V[item][weight]=V[item-1][weight];
                }
            }

        }

        //Printing the matrix
        for (int[] rows : V) {
            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }

        return V[N][W];
    }

    public static void main(String[] args){
//        int n = 4;
//        int[] wight = {5, 4, 6, 3};
//        int[] value = {10, 40, 30, 50};
//        int[] checked = new int[5];
//        int maxWight = 10;
//        System.out.println(knap(n, wight, value, checked, maxWight));

        int val[] = {10, 40, 30, 50};
        int wt[] = {5, 4, 6, 3};
        int W = 10;
        System.out.println(knapsack(val, wt, W));
    }
}
