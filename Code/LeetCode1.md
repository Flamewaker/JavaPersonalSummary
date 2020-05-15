```cpp
class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        map<int, int> index;
        vector<int> ans;
        for(int i=0; i<nums.size();i++){
            if(index.count(target - nums[i])){
                ans.push_back(index[target - nums[i]]);
                ans.push_back(i);
                return ans;
            }
            index[nums[i]] = i;
        }
        return ans;
    }
};
```
```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> index = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            index.put(nums[i], i);
        }
        for(int i=0; i<nums.length; i++){
            int another = target - nums[i];
            if(index.containsKey(another) && index.get(another) != i){
                return new int[] {i, index.get(another)};
            }
        }
        return new int[2];
    }
}

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> index = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            int another = target - nums[i];
            if(index.containsKey(another)){
                return new int[] {index.get(another), i};
            }
            index.put(nums[i], i);
        }
        return new int[2];
    }
}
```

```py
class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        index = {}
        for i, num in enumerate(nums):
            if target - num in index:
                return [ index[target - num], i]
            index[num] = i
        return None
```
```scala
object Solution {
    def twoSum(nums: Array[Int], target: Int): Array[Int] = {
        val index = new scala.collection.mutable.HashMap[Int, Int]()
        for(i <- 0 until nums.length){ 
            if(index.contains(target - nums(i))){
                return Array(index(target - nums(i)),i)
            }
            index(nums(i)) = i
        }
        Array(-1, -1) 
    }
}
```