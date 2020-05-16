package com.todd;

/**
 * 重要*****
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 * 输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
 * 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
 * 思路： 排序数组的查找问题首先考虑使用二分法解决，其可将遍历法的线性级别时间复杂度降低至对数级别 。
 * 设置left, right指针分别指向 numbers 数组左右两端。
 * 当 numbers[mid] > numbers[right]时： mid一定在左排序数组中，即旋转点x一定在 [mid + 1, right]闭区间内，因此执行left = mid + 1；
 * 当 numbers[mid] < numbers[right]时： mid一定在右排序数组中，即旋转点x一定在 [left，mid]闭区间内，因此执行right = mid；
 * 当 numbers[mid] == numbers[right]时：无法判断，但肯定不是right， 此时将right--；
 * 当left == right 循环退出。
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class Offer11 {
    public int minArray(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;
        while(left < right){
            int mid = (left + right)/2;
            if(numbers[mid] > numbers[right]){
                left = mid + 1;
            }else if(numbers[mid] < numbers[right]){
                right = mid;
            }else{
                right--;
            }
        }
        return numbers[left];
    }
}
