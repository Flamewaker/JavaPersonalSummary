package com.todd.code;

/**
 * @author todd
 * @date 2020/6/15 13:40
 * @description: 实现 int sqrt(int x) 函数。计算并返回 x 的平方根，其中 x 是非负整数。
 *
 * 首先mid取的值是左边的中间值（也就是总是取得相对小的数），比如3和4取3。
 *
 * 搜索区间[left, right), [left, ... , x) left * left > x
 * 搜索区间是 [left, right) 左闭右开，所以当 nums[mid] 被检测之后，下一步的搜索区间应该去掉 mid 分割成两个区间，即 [left, mid) 或 [mid + 1, right)。
 * 会在后面一个区间进行搜素，第一个平方值大于x的数。
 * 最后的值是 left - 1， 结束情况是left == right 此时left * left > x 并且 (left - 1) * (left - 1) < x 比如8就没有直接的返回值。
 * x = 8 假设 mid = 3 则 right = 3 最终能够得到 left = right = 3   left - 1 = 2
 * x = 8 假设 mid = 2 则 left = 3 最终能够得到 left = right = 3   left - 1 = 2
 * 搜索区间[left, left + 1) 最终得到的 left + 1， 但 left 的值才是正确值。
 */
public class LeetCode69 {

    //寻找左侧边界

    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        int left = 1;
        int right = x;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (x / mid == mid) {
                return mid;
            } else if (x / mid > mid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }


}
