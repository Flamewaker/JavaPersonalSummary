package com.todd;

/**
 * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
 * 没啥好说的，一行代码的事。不过可以看下反编译后的代码：(new StringBuilder()).append(s.substring(i)).append(s.substring(0, i)).toString();
 *
 * @Auther todd
 * @Date 2020/5/14
 */
public class Offer58_2 {
    public String reverseLeftWords(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }
}
