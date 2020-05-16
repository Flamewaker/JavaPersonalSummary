package com.todd;

import java.util.*;

/**
 * 单调栈 + 对象排序
 * @Author todd
 * @Date 2020/4/13
 */
public class Alibaba43_2 {
    static class Pair{
        int index;
        long val;
        Pair(int index,long val){
            this.index=index;
            this.val=val;
        }
    }
    static int cnt=0;
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        //输入的数组
        long[] array=new long[n];
        //将array数组的索引和数组的值进行封装，组成的数组
        Pair[] pairs=new Pair[n];
        //记录array数组每个数的右边比它小的数的最大值，若不存在则先标记为-1
        long[] right=new long[n];
        for(int i=0;i<n;i++){
            array[i]=scanner.nextLong();
            pairs[i]=new Pair(i,array[i]);
        }
        //对array数组的值进行排序
        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override public int compare(Pair o1, Pair o2) {
                return (int)(o1.val-o2.val);
        }});
        getValue(pairs,right,array);
        System.out.println(cnt);
    }

    public static void getValue(Pair[] pairs,long[] right,long[] array){
        Stack<Integer> stack1=new Stack<>();
        //找出数组中该数右边比它小的数的最大值，即对排序后的pair数组去找单调栈中左边第一个比它索引大的索引所对应的值
        for(int i=0;i<pairs.length;i++){
            while (!stack1.isEmpty()&&pairs[i].index>stack1.peek()){
                stack1.pop();
            }
            if(!stack1.isEmpty()){
                right[pairs[i].index]=array[stack1.peek()];
            }else {//不存在这样的值，标记为-1；肯定不会是有价值的数
                right[pairs[i].index]=-1;
            }
            stack1.push(pairs[i].index);
        }

        long left;
        Stack<Integer> stack2=new Stack<>();
        //找出数组中该数左边比它大的数的最小值，即对排序后的pair数组去找单调栈中右边第一个比它索引小的索引所对应的值
        for(int i=pairs.length-1;i>=0;i--){
            while (!stack2.isEmpty()&&pairs[i].index<stack2.peek()){
                stack2.pop();
            }
            if(!stack2.isEmpty()){
                left=array[stack2.peek()];
                if(right[pairs[i].index]!=-1){
                    if(left%right[pairs[i].index]==0){
                        cnt++;
                    }
                }
            }
            stack2.push(pairs[i].index);
        }

    }
}