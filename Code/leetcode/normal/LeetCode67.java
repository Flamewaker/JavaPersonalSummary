package com.todd.leetcode.normal;

/**
 * @author todd
 * @date 2020/7/3 18:37
 * @description: TODO
 */
public class LeetCode67 {
    public String addBinary(String a, String b) {
        int lengthA = a.length();
        int lengthB = b.length();
        StringBuilder ans = new StringBuilder();
        int indexA = lengthA - 1;
        int indexB = lengthB - 1;
        int carry = 0;
        while (indexA >= 0 && indexB >= 0) {
            int sum = a.charAt(indexA) - '0' + b.charAt(indexB) - '0' + carry;
            carry =  sum >= 2 ? 1 : 0;
            char cur = sum == 1 || sum == 3 ? '1' : '0';
            ans.append(cur);
            indexA--;
            indexB--;
        }
        if (indexA >= 0) {
            while(indexA >= 0) {
                int sum = a.charAt(indexA) - '0' + carry;
                carry =  sum == 2 ? 1 : 0;
                char cur = sum == 1 ? '1' : '0';
                ans.append(cur);
                indexA--;
            }
        } else if (indexB >= 0) {
            while(indexB >= 0) {
                int sum = b.charAt(indexB) - '0' + carry;
                carry =  sum == 2 ? 1 : 0;
                char cur = sum == 1 ? '1' : '0';
                ans.append(cur);
                indexB--;
            }
        }
        if (carry == 1) {
            ans.append('1');
        }
        return ans.reverse().toString();
    }
}
