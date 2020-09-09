package com.todd.redo.nowcoder;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author todd
 * @date 2020/8/6 7:07
 * @description: 那些插队的人
 * 对于插队序列中的每一个人，我们只需要考虑其最后一次插队即可。
 * 接下来求解答案，插队序列中最大值为10， 那么大于等于10的部分队伍序列肯定都是对的，所以我们要找就是[1,10]这个区间有多少个人不在自己的位置上，
 * 可以反过来求[1,10]里有多少人在正确的位置上，因为能在正确的位置上出现的人，一定是属于插队序列里的人，所以我们可以比较插队后的位置和实际位置判断这个人是否在正确的位置。
 * （不插队的话，肯定会被从原来的位置挤开，那么就不可能出出现在正确的位置）
 */
public class NC507 {
    public static void main(String[] args) {
        int[] nums = new int[]{10, 4, 6, 5, 9, 7, 3, 3, 9, 10};
        System.out.println(countDislocation(10, nums));
    }

    public static int countDislocation(int n, int[] cutIn) {
        // write code here
        int maxm = 0;
        int cutnum = cutIn.length;
        int count = 0;
        int index = 1;
        HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
        for(int i=cutIn.length-1;i>=0;i--){
            if(!map.containsKey(cutIn[i])){
                map.put(cutIn[i],index++);
                if(cutnum - i==cutIn[i]) {
                    count++;
                }
            }
            else {
                cutnum--;
            }
            if(cutIn[i]>maxm) {
                maxm = cutIn[i];
            }

        }
        return maxm-count;
    }
}
