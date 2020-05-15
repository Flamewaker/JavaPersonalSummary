package com.todd;

/**
 * 各种排序的实现
 *
 * @Auther todd
 * @Date 2020/4/4
 */
public class LeetCode912 {
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
     **/
    void qSort(int[] arr, int s, int e) {
        int l = s, r = e;
        if (l < r) {
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
            qSort(arr, s, l);
            qSort(arr, l + 1, e);
        }
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
     * <p>
     * 时间复杂度：O(NlogN) 　　稳定性：不稳定
     */
    void heapSort(int[] nums) {
        int size = nums.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            adjust(nums, size, i);
        }
        for (int i = size - 1; i >= 1; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;
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
            int temp = nums[maxIndex];
            nums[maxIndex] = nums[index];
            nums[index] = temp;
            adjust(nums, len, maxIndex);
        }
    }
}
