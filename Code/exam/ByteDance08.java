package com.todd.exam;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author todd
 * @date 2020/6/25 22:28
 * @description: 特征提取
 * 这里需要注意的是，使用俩个HashMap的原因是，对于某个特征，当上一帧有值的时候，可以在上一帧的基础上得到当前连续的次数。
 * 当上一帧没有值的时候，则重新计数。
 */
public class ByteDance08 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            int m = sc.nextInt();
            HashMap<Feature, Integer> last = new HashMap<>();
            HashMap<Feature, Integer> current = new HashMap<>();
            int ans = 0;
            for (int j = 0; j < m; j++) {
                int featureNums = sc.nextInt();
                for (int k = 0; k < featureNums; k++) {
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    Feature feature = new Feature(x, y);
                    current.put(feature, last.getOrDefault(feature, 0) + 1);
                }
                for (Integer value : current.values()) {
                    ans = Math.max(ans, value);
                }
                last = current;
                current = new HashMap<>();
            }

            System.out.println(ans);
        }
    }

    private static class Feature {
        int x;
        int y;

        public Feature(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Feature feature = (Feature) o;
            return x == feature.x && y == feature.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
