package com.todd.code;

/**
 * ***********
 * 把n个骰子扔在地上，所有骰子朝上一面的点数之和为s。输入n，打印出s的所有可能的值出现的概率。
 * 你需要用一个浮点数数组返回答案，其中第 i 个元素代表这 n 个骰子所能掷出的点数集合中第 i 小的那个的概率。
 * 思路： 确定状态转移方程。n个骰子点数和为s的种类数只与n-1个骰子的和有关。因为一个骰子有六个点数，那么第n个骰子可能出现1到6的点数。
 * 所以第n个骰子点数为1的话，f(n,s)=f(n-1,s-1)，当第n个骰子点数为2的话，f(n,s)=f(n-1,s-2)，…，依次类推。
 * 在n-1个骰子的基础上，再增加一个骰子出现点数和为s的结果只有这6种情况！那么有：f(n,s)=f(n-1,s-1)+f(n-1,s-2)+f(n-1,s-3)+f(n-1,s-4)+f(n-1,s-5)+f(n-1,s-6)
 * f(n,s)=f(n-1,s-1)+f(n-1,s-2)+f(n-1,s-3)+f(n-1,s-4)+f(n-1,s-5)+f(n-1,s-6)
 *
 * @Author todd
 * @Date 2020/5/14
 */
public class Offer60 {
    public double[] twoSum(int n) {
        int[][] dp = new int[n + 1][6 * n + 1];
        double[] ans = new double[5 * n + 1];
        double all = Math.pow(6, n);
        for (int i = 0; i <= 6; i++) {
            dp[1][i] = 1;
        }

        //1. 第一个循环代表骰子状态的转移
        //2. 第二个循环代表当前骰子可能存在的数值状态
        //3. 第三个循环代表状态的转移过程
        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= 6 * i; j++) {
                for (int k = 1; k <= 6; k++) {
                    if (j - k <= 0) {
                        break;
                    }
                    dp[i][j] += dp[i - 1][j - k];
                }
            }
        }

        for (int i = n; i <= 6 * n; i++) {
            ans[i - n] = dp[n][i] / all;
        }
        return ans;
    }

    public double[] twoSum2(int n) {
        if (n == 0) {
            return new double[0];
        }
        double[] dp = new double[6 * n + 1];
        double[] ans = new double[5 * n + 1];
        double all = Math.pow(6, n);
        for (int i = 1; i <= 6; i++) {
            dp[i] = 1;
        }
        //第二个循环从后往前遍历，**避免覆盖**
        for (int i = 2; i <= n; i++) {
            for (int j = 6 * n; j >= 1; j--) {
                int temp = 0;
                for (int k = 1; k <= 6; k++) {
                    if (j - k <= 0) {
                        break;
                    }
                    temp += dp[j - k];
                }
                dp[j] = temp;
            }
        }

        for (int i = n; i <= 6 * n; i++) {
            ans[i - n] = dp[i] / all;
        }

        return ans;
    }
}
