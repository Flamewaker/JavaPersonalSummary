给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。

```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> st = new Stack();
        int len = s.length();
        for(int i = 0; i < len; i++){
            if(!st.empty()){
                if(s.charAt(i) == ']'){
                    if(st.peek() == '['){
                        st.pop();
                    }else return false;
                }else if(s.charAt(i) == ')'){
                    if(st.peek() == '('){
                        st.pop();
                    }else return false;
                }else if(s.charAt(i) == '}'){
                    if(st.peek() == '{'){
                        st.pop();
                    }else return false;
                }else st.push(s.charAt(i));
            }else st.push(s.charAt(i));
        }
        return st.empty();
    }
}
```
```python
class Solution:
    def isValid(self, s: str) -> bool:
        s_map = {')': '(', '}': '{', ']': '['}
        stack = []
        for i in s:
            if i in s_map:
                if stack and stack[-1] == s_map[i]:
                    stack.pop()
                else:
                    return False
            else:
                stack.append(i)
        return not stack
```
```cpp
class Solution {
public:
    bool isValid(string s) {
        stack<char> st;
        int len = s.length();
        for(int i=0; i<len; i++){
            if(st.empty()){
                st.push(s[i]);
            }
            else{
                if(s[i] == ']'){
                    if(st.top() == '['){
                        st.pop();
                    }
                    else return false;
                }else if(s[i] == '}'){
                    if(st.top() == '{'){
                        st.pop();
                    }
                    else return false;
                }else if(s[i] == ')'){
                    if(st.top() == '('){
                        st.pop();
                    }
                    else return false;
                }else st.push(s[i]);
            }
        }
        if(!st.empty()) return false;
        else return true;
    }
};

class Solution {
public:
    bool isValid(string s) {
        map<char,char> mp{{'(',')'},{'{','}'},{'[',']'}};
        stack<char> st;
        int len = s.length();
        for(int i=0; i < len; i++){
            if(mp.count(s[i])) st.push(s[i]);
            else{
                if(st.empty()) return false;
                if(mp[st.top()] == s[i]) st.pop(); 
                else return false;
            }
        }
        return st.empty();
    }
};

class Solution {
public:
    bool isValid(string s) {
        map<char,char> mp{{')','('},{'}','{'},{']','['}};
        stack<char> st;
        int len = s.length();
        for(int i=0; i < len; i++){
            if(mp.count(s[i])){
                if(!st.empty() && mp[s[i]] == st.top()) st.pop();
                else return false;
            } 
            else{
               st.push(s[i]);
            }
        }
        return st.empty();
    }
};
```