package com.todd.exam;

import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/25 13:10
 * @description: 毕业旅行问题
 * 小明目前在做一份毕业旅行的规划。打算从北京出发，分别去若干个城市，然后再回到北京，每个城市之间均乘坐高铁，且每个城市只去一次。
 * 由于经费有限，希望能够通过合理的路线安排尽可能的省一些路上的花销。给定一组城市和每对城市之间的火车票的价钱，找到每个城市只访问一次并返回起点的最小车费花销。
 *
 * 放弃。。。。未来再看。。。
 */
public class ByteDance05 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int cityNum = in.nextInt();// 城市数目
        int[][] dist = new int[cityNum][cityNum];// 距离矩阵，距离为欧式空间距离
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < cityNum; j++) {
                dist[i][j] = in.nextInt();
            }
        }
        in.close();

        int V = 1 << (cityNum - 1);// 对1进行左移n-1位，值刚好等于2^(n-1)
        // dp表，n行，2^(n-1)列
        int[][] dp = new int[cityNum][V];
        // 初始化dp表第一列
        for (int i = 0; i < cityNum; i++) {
            dp[i][0] = dist[i][0];
        }

        //设想一个数组城市子集V[j]，长度为V,且V[j] = j,对于V[j]即为压缩状态的城市集合
        //从1到V-1  用二进制表示的话，刚好可以映射成除了0号城市外的剩余n-1个城市在不在子集V[j]，1代表在，0代表不在
        //若有总共有4个城市的话，除了第0号城市，对于1-3号城市
        //111 = V-1 = 2^3 - 1  = 7 ，从高位到低位表示3到1号城市都在子集中
        //而101 = 5 ，表示3,1号城市在子集中，而其他城市不在子集中
        //这里j不仅是dp表的列坐标值，如上描述，j的二进制表示城市相应城市是否在子集中
        for (int j = 1; j < V; j++) {
            for (int i = 0; i < cityNum; i++) { //这个i不仅代表城市号，还代表第i次迭代
                dp[i][j] = Integer.MAX_VALUE; //为了方便求最小值,先将其设为最大值
                if (((j >> (i - 1)) & 1) == 0) {
                    // 因为j就代表城市子集V[j],((j >> (i - 1))是把第i号城市取出来
                    //并位与上1，等于0，说明是从i号城市出发，经过城市子集V[j]，回到起点0号城市
                    for (int k = 1; k < cityNum; k++) { // 这里要求经过子集V[j]里的城市回到0号城市的最小距离
                        if (((j >> (k - 1)) & 1) == 1) { //遍历城市子集V[j]
                            //设s=j ^ (1 << (k - 1))
                            //dp[k][j ^ (1 << (k - 1))，是将dp定位到，从k城市出发，经过城市子集V[s]，回到0号城市所花费的最小距离
                            //怎么定位到城市子集V[s]呢，因为如果从k城市出发的，经过城市子集V[s]的话
                            //那么V[s]中肯定不包含k了，那么在j中把第k个城市置0就可以了，而j ^ (1 << (k - 1))的功能就是这个
                            dp[i][j] = Math.min(dp[i][j], dist[i][k] + dp[k][j ^ (1 << (k - 1))]); //^异或
                            //还有怎么保证dp[k][j ^ (1 << (k - 1))]的值已经得到了呢，
                            //注意所有的计算都是以dp表为准，从左往右从上往下的计算的，每次计算都用到左边列的数据
                            //而dp表是有初试值的，所以肯定能表格都能计算出来
                        }
                    }
                }
            }
        }
        System.out.println(dp[0][V - 1]);
    }
}
