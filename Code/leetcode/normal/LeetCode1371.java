package com.todd.leetcode.normal;

import java.util.HashMap;

/**
 * @author todd
 * @date 2020/5/20 8:32
 * @description: 每个元音包含偶数次的最长子字符串
 * 给你一个字符串 s ，请你返回满足以下条件的最长子字符串的长度：每个元音字母，即 'a'，'e'，'i'，'o'，'u' ，在子字符串中都恰好出现了偶数次。
 * 状态压缩+哈希表
 * 思路：条件，我们需要找的子串中，每个元音字母都恰好出现了偶数次。
 * 偶数这个条件其实告诉了我们，对于满足条件的子串而言，两个前缀的奇偶性一定是相同的
 * 因此我们可以对前缀和稍作修改，从维护元音字母出现的次数改作维护元音字母出现次数的奇偶性。
 * 我们就可以利用哈希表存储每一种奇偶性（即考虑所有的元音字母）对应最早出现的位置，边遍历边更新答案。
 *
 * 我们还可以进一步优化我们的编码方式，如果直接以每个元音字母出现次数的奇偶性为哈希表中的键，难免有些冗余，我们可能需要额外定义一个状态：
 * 考虑到出现次数的奇偶性其实无非就两个值，0 代表出现了偶数次，1 代表出现了奇数次，我们可以将其压缩到一个二进制数中。
 * 因此我们也不再需要使用哈希表，直接用一个长度为 32的数组来存储对应状态出现的最早位置即可。
 *
 * aeiou每个元音用一个bit一共5个bit，32种奇偶次数组合状态，比如10101可以表示aiu出现奇数次数
 * oe则出现偶数次数，每当遍历一个字符，就可以改变当前的aeiou出现的奇偶次数，也即是改变状态
 * 显然，如果两次出现了同样的状态，假设第一次出现在i处
 * 第二次出现在j处，那么i+1-j之间的字符串肯定是满足aeiou出现均为偶数次数的
 * 因为只有经历了偶数个aeiou，才能回到之前的状态，为了使得合理的字符串最长
 * 那么第一次出现此状态时，就需要记录到下标，然后下次遇到相同状态，计算最大长度
 */
public class LeetCode1371 {
    public int findTheLongestSubstring(String s) {
        HashMap<Integer, Integer> index = new HashMap<>();
        char[] str = s.toCharArray();
        int ans = 0;
        int state = 0;
        index.put(0, -1);
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'a') {
                state ^= 1;
            }
            if (str[i] == 'e') {
                state ^= 1 << 1;
            }
            if (str[i] == 'i') {
                state ^= 1 << 2;
            }
            if (str[i] == 'o') {
                state ^= 1 << 3;
            }
            if (str[i] == 'u') {
                state ^= 1 << 4;
            }
            Integer lastIndex = index.get(state);
            if (lastIndex == null) {
                index.put(state, i);
            } else {
                ans = Math.max(ans, i - lastIndex);
            }
        }
        return ans;
    }
}
