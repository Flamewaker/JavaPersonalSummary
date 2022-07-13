package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/6/8 9:46
 * @description: TODO
 * 条件1 ： 所有站里的油总量要>=车子的总耗油量。总能行驶完全程。
 * 条件2 ： 可以看做，起始点将当前路径分为A、B两部分。其中，必然有A部分剩余油量<0。B部分剩余油量>0。
 * 无论多少个站，都可以抽象为两个站点（A、B）。(1)从B站加满油出发，(2)开往A站，车加油，(3)再开回B站的过程。
 * index->end => B 站点，后缀和大于0。且均能满足通行条件。
 *
 * a要是到不了b但是能到a和b之间的点，那么a和b之间的所有点都到不了b，所以可以直接从b开始重新计算能到哪
 *
 * 计算出每个加油站剩余的油量，并计算总剩余量
 * 如果总剩余量 < 0，说明汽油不足以绕一圈，无解，否则一定有解
 * 从start位置开始出发，记录从出发位置积累的剩余汽油油量，如果在为i处，油箱为负数，说明从位置0不足以到位置i。哪下一个出发位置应当在哪？
 * 解会不会在start之前？不会，因为是从前边遍历过来的，如果start前边的位置start'能到本加油站，start会是start'，而不是当前值。
 * 解会不会是start到i位置中的某个加油站？假如存在这样一个加油站start'，start能到start'，然后到了i，而start还没有切换，说明start到start'时，sum>=0的，有足够的汽油到start'，如果从start'出发到i，sum只会比当前的汽油更少，可能都不足以到达i。
 * 根据排除法，所以解只能在i或者i右边，那就从i开始找。
 * base case：start=0，即从0号位置出发。
 * 当遍历数组结束时，start必然是解。
 * 时间O(N)，不用数组保存每个加油站的汽油余量则为O(1)，使用则O(N)。
 */
public class LeetCode0134 {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int rest = 0;
        int run = 0;
        int index = 0;
        for (int i = 0; i < gas.length; i++) {
            rest += gas[i] - cost[i];
            run += gas[i] - cost[i];
            if (run < 0) {
                run = 0;
                index = i + 1;
            }
        }
        return rest >= 0 ? index : -1;
    }
}
