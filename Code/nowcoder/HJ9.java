package com.todd.nowcoder;

import java.util.HashSet;
import java.util.Scanner;

/**
 * @author tongchengdong
 * @description HJ9 提取不重复的整数
 * @date 9:44 PM 2022/7/2
 */
public class HJ9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        char[] chars = (num + "").toCharArray();
        String str = "";
        for (int i = chars.length - 1; i >= 0; i--) {
            if (!str.contains(chars[i] + "")) {
                str += chars[i];
            }
        }
        System.out.println(Integer.valueOf(str));
    }

//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        while(sc.hasNext()){
//            // 使用HashSet来判断是否是不重复的
//            HashSet<Integer> hs = new HashSet<>();
//            int target = sc.nextInt();
//            while(target != 0){
//                int temp = target % 10;
//                if(hs.add(temp)) {
//                    System.out.print(temp);
//                }
//                target /= 10;
//            }
//            System.out.println();
//        }
//    }
}
