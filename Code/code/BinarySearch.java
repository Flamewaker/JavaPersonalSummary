package com.todd.code;

import org.w3c.dom.html.HTMLTableRowElement;

/**
 * @author todd
 * @date 2020/7/22 12:20
 * @description: 二分查找及其各类变体
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6};

    }


    /**
     * 经典二分：顺序排列，数字不重复，需要注意的是此时的 while 条件是 left <= right，需要注意区间的开闭。
     * @param nums
     * @param target
     * @return target index
     */
    public static int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }


    /**
     * 实现 int sqrt(int x) 函数。返回 x 的平方根，其中 x 是非负整数。
     * @param x
     * @return x 的平方根
     */
    public int mySqrt(int x) {
        if (x < 2) {
            return x;
        }
        int left = 1, right = x / 2;
        while (left <= right) {
            int pivot = left + ((right - left) >> 1);
            if (pivot <= x / pivot && (pivot + 1) >  x / (pivot + 1)) {
                return pivot;
            } else if (pivot > x / pivot){
                right = pivot - 1;
            } else if (pivot < x / pivot){
                left = pivot + 1;
            }
        }
        return left;
    }


    /**
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
     * @param nums
     * @param target
     * @return
     * 1. 先判断单调区间
     * 2. 再判断单调区间上是否包含所要查询的数
     * 3. 如果单调区间上包含所要查找的数，那么就转换成了单调区间上查找值的问题
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while(left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {
                if (target < nums[mid] && target >= nums[left]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 变体1： 有重复值的数组，找到第一个 = target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch2(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] >= target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        //left 和 right 相等的时候，其索引位置理应位于第一个小于target的位置。left + 1 则得到答案。
        //由于 while 的退出条件是 left == right + 1，所以当 target 比 nums 中所有元素都大时，有可能出现索引越界的情况


        if (left >= nums.length || nums[left] != target) {
            return -1;
        }
        return left;
    }

    /**
     * 变体2： 有重复值的数组，找到最后一个 = target的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch3(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] <= target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        //left 和 right 相等的时候，其索引位置理应位于第一个大于target的位置。right - 1 则得到答案。
        //当 target 比所有元素都小时，right 会被减到 -1，有可能出现索引越界的情况

        if (right < 0 || nums[right] != target) {
            return -1;
        }
        return right;
    }

    /**
     * 变体3：有重复值的数组，找到第一个大于 target 的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch4(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] <= target) {
                left = mid + 1;
            }
        }

        if (left >= nums.length) {
            return -1;
        }
        return left;
    }

    /**
     * 驼峰数组，先增后减，寻找峰值
     * @param nums
     * @return
     */
    public static int binarySearch4(int[] nums) {
        if (nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = left + ((right - left) >> 1);
        while (mid > 0 && mid < nums.length - 1) {
            if (nums[mid] > nums[mid + 1] && nums[mid] > nums[mid - 1]) {
                return mid;
            } else if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
                mid = left + ((right - left) >> 1);
            } else {
                right = mid - 1;
                mid = left + ((right - left) >> 1);
            }
        }
        return -1;
    }
}
