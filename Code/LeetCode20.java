package com.todd;

import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * @Author todd
 * @Date 2020/5/15
 */
public class LeetCode20 {
    public boolean isValid(String s) {
        Stack<Character> st = new Stack();
        int len = s.length();
        for(int i = 0; i < len; i++){
            if(!st.empty()){
                if(s.charAt(i) == ']'){
                    if(st.peek() == '['){
                        st.pop();
                    }else {
                        return false;
                    }
                }else if(s.charAt(i) == ')'){
                    if(st.peek() == '('){
                        st.pop();
                    }else {
                        return false;
                    }
                }else if(s.charAt(i) == '}'){
                    if(st.peek() == '{'){
                        st.pop();
                    }else {
                        return false;
                    }
                }else {
                    st.push(s.charAt(i));
                }
            }else {
                st.push(s.charAt(i));
            }
        }
        return st.empty();
    }
}
