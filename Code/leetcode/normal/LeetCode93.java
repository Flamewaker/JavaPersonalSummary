package com.todd.leetcode.normal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author todd
 * @date 2020/6/27 14:15
 * @description: 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
 * 有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔。
 *
 */
public class LeetCode93 {
    List<String> res = new ArrayList<String>();
    public List<String> restoreIpAddresses(String s) {
        if (s == null || s.length() < 4 || s.length() > 12) {
            return res;
        }
        next(s, 0, 0, new StringBuilder());
        return res;
    }

    void next(String s, int start, int pos, StringBuilder sb) {
        if(pos == 4) {
            if(start == s.length()) {
                res.add(sb.toString().substring(0, sb.length() - 1));
            }
            return;
        }
        int sum = 0;
        for(int i = start; i < start + 3 && i < s.length(); i++) {
            sum *= 10;
            sum += s.charAt(i) - '0';
            if(sum > 255) {
                break;
            }
            sb.append(s.substring(start, i + 1) + ".");
            next(s, i + 1, pos + 1, sb);
            sb.delete(sb.length() - (i - start + 2), sb.length());
            if (s.charAt(start) == '0') {
                break;
            }
        }
    }
}
