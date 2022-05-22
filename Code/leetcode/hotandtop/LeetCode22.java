package com.todd.leetcode.hotandtop;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tongchengdong
 * @description 22. 括号生成
 * 整体思路：
 * 1. 深搜遍历所有情况，分成待完成的括号数和已完成的括号数
 * 2. 终止条件，finish = n && index == n
 * @date 2:29 PM 2022/5/21
 */
public class LeetCode22 {

    ArrayList<String> ans = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        if (n == 0) {
            return ans;
        }
        nextString("", 0, 0, n);
        return ans;
    }

    private void nextString(String str, int index, int finish, int n) {
        // 1. 终止条件
        if (index == n && finish == n) {
            ans.add(str);
            // 2. 左括号已添加完毕，右括号没添加完, 注意一定是要满足 index >= finish
        } else if (index == n && finish < n) {
            String candidate = str + ')';
            nextString(candidate, index, finish + 1, n);
            // 3. 均未添加完毕，存在两种可能性, 但是必须先添加左括号再添加右括号
        } else {
            String candidate1 = str + '(';
            nextString(candidate1, index + 1, finish, n);
            if (index > finish) {
                String candidate2 = str + ')';
                nextString(candidate2, index, finish + 1, n);
            }
        }
    }

}
