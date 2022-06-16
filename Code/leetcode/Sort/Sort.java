package com.todd.leetcode.Sort;

/**
 * 各种排序的实现 LeetCode912
 *
 * @Author todd
 * @Date 2020/4/4
 */
public class Sort {
    public int[] sortArray(int[] nums) {
        if (nums.length <= 1) {
            return nums;
        }
        //qSort(nums,0,nums.length-1);
        //selectSort(nums);
        // insertSort(nums);
        // shellSort(nums);
        // bucketSort(nums);
        // countSort(nums);
        // mergeSort(nums,0,nums.length-1);
        heapSort(nums);
        return nums;
    }

    /**
     * 快速排序
     * 基本思路：快速排序每一次都排定一个元素（这个元素呆在了它最终应该呆的位置），然后递归地去排它左边的部分和右边的部分，依次进行下去，直到数组有序；
     * 算法思想：分而治之（分治思想），与「归并排序」不同，「快速排序」在「分」这件事情上不想「归并排序」无脑地一分为二，而是采用了 partition 的方法，因此就没有「合」的过程。
     * 实现细节（注意事项）：（针对特殊测试用例：顺序数组或者逆序数组）一定要随机化选择切分元素（pivot），否则在输入数组是有序数组或者是逆序数组的时候，快速排序会变得非常慢（等同于冒泡排序或者「选择排序」）；
     **/
    void qSort(int[] arr, int s, int e) {
        int l = s, r = e;
        if (l >= r) {
            return;
        }
        int temp = arr[l];
        while (l < r) {
            while (l < r && arr[r] >= temp) {
                r--;
            }
            if (l < r) {
                arr[l] = arr[r];
            }
            while (l < r && arr[l] < temp) {
                l++;
            }
            if (l < r) {
                arr[r] = arr[l];
            }
        }
        arr[l] = temp;
        qSort(arr, s, l - 1);
        qSort(arr, l + 1, e);
    }

    /**
     * 选择排序
     **/
    void selectSort(int[] arr) {
        int min;
        for (int i = 0; i < arr.length; i++) {
            min = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
    }

    /**
     * 插入排序：数列前面部分看为有序，依次将后面的无序数列元素插入到前面的有序数列中，初始状态有序数列仅有一个元素，即首元素。在将无序数列元素插入有序数列的过程中，采用了逆序遍历有序数列，相较于顺序遍历会稍显繁琐，但当数列本身已近排序状态效率会更高。
     * <p>
     * 时间复杂度：O(N2) 　　稳定性：稳定
     *
     * @param arr
     */
    public void insertSort(int arr[]) {
        for (int i = 1; i < arr.length; i++) {
            int rt = arr[i];
            for (int j = i - 1; j >= 0; j--) {
                if (rt < arr[j]) {
                    arr[j + 1] = arr[j];
                    arr[j] = rt;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 希尔排序 - 插入排序的改进版。为了减少数据的移动次数，在初始序列较大时取较大的步长，通常取序列长度的一半，此时只有两个元素比较，交换一次；之后步长依次减半直至步长为1，即为插入排序，由于此时序列已接近有序，故插入元素时数据移动的次数会相对较少，效率得到了提高。
     * <p>
     * 时间复杂度：通常认为是O(N3/2) ，未验证　　稳定性：不稳定
     *
     * @param arr
     */
    void shellSort(int arr[]) {
        int d = arr.length >> 1;
        while (d >= 1) {
            for (int i = d; i < arr.length; i++) {
                int rt = arr[i];
                for (int j = i - d; j >= 0; j -= d) {
                    if (rt < arr[j]) {
                        arr[j + d] = arr[j];
                        arr[j] = rt;
                    } else {
                        break;
                    }
                }
            }
            d >>= 1;
        }
    }

    /**
     * 桶排序 - 实现线性排序，但当元素间值得大小有较大差距时会带来内存空间的较大浪费。首先，找出待排序列中得最大元素max，申请内存大小为max + 1的桶（数组）并初始化为0；然后，遍历排序数列，并依次将每个元素作为下标的桶元素值自增1；
     * 最后，遍历桶元素，并依次将值非0的元素下标值载入排序数列（桶元素>1表明有值大小相等的元素，此时依次将他们载入排序数列），遍历完成，排序数列便为有序数列。
     * <p>
     * 时间复杂度：O(x*N) 　　稳定性：稳定
     *
     * @param arr
     */
    void bucketSort(int[] arr) {
        int[] bk = new int[50000 * 2 + 1];
        for (int i = 0; i < arr.length; i++) {
            bk[arr[i] + 50000] += 1;
        }
        int ar = 0;
        for (int i = 0; i < bk.length; i++) {
            for (int j = bk[i]; j > 0; j--) {
                arr[ar++] = i - 50000;
            }
        }
    }

    /**
     * 基数排序 - 桶排序的改进版，桶的大小固定为10，减少了内存空间的开销。首先，找出待排序列中得最大元素max，并依次按max的低位到高位对所有元素排序；
     * 桶元素10个元素的大小即为待排序数列元素对应数值为相等元素的个数，即每次遍历待排序数列，桶将其按对应数值位大小分为了10个层级，桶内元素值得和为待排序数列元素个数。
     *
     * @param arr
     */
    void countSort(int[] arr) {
        int[] bk = new int[19];
        Integer max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (max < Math.abs(arr[i])) {
                max = arr[i];
            }
        }
        if (max < 0) {
            max = -max;
        }
        max = max.toString().length();
        int[][] bd = new int[19][arr.length];
        for (int k = 0; k < max; k++) {
            for (int i = 0; i < arr.length; i++) {
                int value = (int) (arr[i] / (Math.pow(10, k)) % 10);
                bd[value + 9][bk[value + 9]++] = arr[i];
            }
            int fl = 0;
            for (int l = 0; l < 19; l++) {
                if (bk[l] != 0) {
                    for (int s = 0; s < bk[l]; s++) {
                        arr[fl++] = bd[l][s];
                    }
                }
            }
            bk = new int[19];
            fl = 0;
        }
    }

    /**
     * 归并排序 - 采用了分治和递归的思想，递归&分治-排序整个数列如同排序两个有序数列，依次执行这个过程直至排序末端的两个元素，再依次向上层输送排序好的两个子列进行排序直至整个数列有序（类比二叉树的思想，from down to up）。
     * <p>
     * 时间复杂度：O(NlogN) 　　稳定性：稳定
     *
     * @param arr
     */
    void mergeSortInOrder(int[] arr, int bgn, int mid, int end) {
        int l = bgn, m = mid + 1, e = end;
        int[] arrs = new int[end - bgn + 1];
        int k = 0;
        while (l <= mid && m <= e) {
            if (arr[l] <= arr[m]) {
                arrs[k++] = arr[l++];
            } else {
                arrs[k++] = arr[m++];
            }
        }
        while (l <= mid) {
            arrs[k++] = arr[l++];
        }
        while (m <= e) {
            arrs[k++] = arr[m++];
        }
        for (int i = 0; i < arrs.length; i++) {
            arr[i + bgn] = arrs[i];
        }
    }

    void mergeSort(int[] arr, int bgn, int end) {
        if (bgn >= end) {
            return;
        }
        int mid = (bgn + end) >> 1;
        mergeSort(arr, bgn, mid);
        mergeSort(arr, mid + 1, end);
        mergeSortInOrder(arr, bgn, mid, end);
    }

    /**
     * 堆排序 - 堆排序的思想借助于二叉堆中的最大堆得以实现。首先，将待排序数列抽象为二叉树，并构造出最大堆；然后，依次将最大元素（即根节点元素）与待排序数列的最后一个元素交换（即二叉树最深层最右边的叶子结点元素）；
     * 每次遍历，刷新最后一个元素的位置（自减1），直至其与首元素相交，即完成排序。
     * 堆排序的基本思想是：将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。将其与末尾元素进行交换，此时末尾就为最大值。然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。如此反复执行，便能得到一个有序序列了
     * 1. 构造初始堆。将给定无序序列构造成一个大顶堆（一般升序采用大顶堆，降序采用小顶堆)。
     * 1.1 我们从最后一个非叶子结点开始（叶结点自然不用调整，第一个非叶子结点 length/2-1），从左至右，从下至上进行调整。
     * 2. 将堆顶元素与末尾元素进行交换，使末尾元素最大。然后继续调整堆，再将堆顶元素与末尾元素交换，得到第二大元素。如此反复进行交换、重建、交换。
     * 时间复杂度：O(NlogN) 　　稳定性：不稳定
     */
    void heapSort(int[] nums) {
        int size = nums.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            adjust(nums, size, i);
        }
        for (int i = size - 1; i >= 1; i--) {
            swap(nums, 0, i);
            adjust(nums, i, 0);
        }
    }


    void adjust(int[] nums, int len, int index) {
        int l = 2 * index + 1;
        int r = 2 * index + 2;
        int maxIndex = index;
        if (l < len && nums[l] > nums[maxIndex]) {
            maxIndex = l;
        }
        if (r < len && nums[r] > nums[maxIndex]) {
            maxIndex = r;
        }
        if (maxIndex != index) {
            swap(nums, index, maxIndex);
            adjust(nums, len, maxIndex);
        }
    }

    void adjustHeap(int[] nums, int len, int index) {
        int temp = nums[index];
        for (int k = index * 2 + 1; k < len; k = k * 2 + 1) {
            if (k + 1 < len && nums[k] < nums[k +1]) {
                k++;
            }
            if (nums[k] > temp) {
                nums[index] = nums[k];
                index = k;
            } else {
                break;
            }
        }
        nums[index] = temp;
    }

    void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
