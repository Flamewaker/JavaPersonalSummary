package com.todd.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author todd
 * @date 2020/7/28 15:59
 * @description: 进制转换
 * 给定一个十进制数M，以及需要转换的进制数N。将十进制数M转化为N进制数
 */
public class DiDi4 {

    //进制转换
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nums = br.readLine().split(" ");
        int m = Integer.valueOf(nums[0]);
        int n = Integer.valueOf(nums[1]);
        StringBuffer sb = new StringBuffer();
        char[] arr = {'A','B','C','D','E','F'};
        int temp = 0;
        boolean fs = false;
        if(m < 0){
            fs = true;
            m = -m;
        }
        while(m != 0){
            temp = m % n;
            if(temp > 9) {
                sb.append(arr[temp-9-1]);
            } else {
                sb.append(temp);
            }
            m = m / n;
        }
        if(fs) {
            sb.append("-");
        }
        System.out.println(sb.reverse().toString());
    }
}
