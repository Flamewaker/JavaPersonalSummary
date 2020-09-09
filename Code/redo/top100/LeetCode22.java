package com.todd.redo.top100;

import java.util.ArrayList;
import java.util.List;

/**
 * @author todd
 * @date 2020/8/7 10:51
 * @description: 22. 括号生成
 */
public class LeetCode22 {
    ArrayList<String> ans = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        if(n == 0){
            return ans;
        }
        nextString(0, 0, "", n);
        return ans;
    }
    public void nextString(int index, int finish, String candidate, int n){
        if(index  == n && finish == n){
            ans.add(candidate);
        }
        else if(index  == n && index > finish){
            String nextCandidate = candidate + ")";
            nextString(index, finish + 1, nextCandidate, n);
        }
        else if(index > finish){
            String nextCandidate1 = candidate + ")";
            nextString(index, finish + 1, nextCandidate1, n);
            String nextCandidate2 = candidate + "(";
            nextString(index + 1, finish, nextCandidate2, n);
        }else{
            String nextCandidate = candidate + "(";
            nextString(index + 1, finish, nextCandidate, n);
        }
    }
}
