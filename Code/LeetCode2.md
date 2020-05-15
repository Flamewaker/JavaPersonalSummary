```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode result = head;
        int carry = 0;
        while(l1 != null || l2 != null){
            int x1 = l1 != null ? l1.val : 0;
            int x2 = l2 != null ? l2.val : 0;
            int sum = x1 + x2 + carry;
            carry = sum / 10;
            result.next = new ListNode(sum % 10);
            result = result.next;
            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;
        }
        if(carry != 0){
            result.next = new ListNode(carry);
        }
        return head.next;
    }
}
```

```py
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution:
    def addTwoNumbers(self, l1: ListNode, l2: ListNode) -> ListNode:
        carry = 0
        head = ListNode(0)
        result = head
        while l1 or l2:
            x1 = l1.val if l1 else 0
            x2 = l2.val if l2 else 0
            num = x1 + x2 + carry
            carry = num // 10
            result.next = ListNode(num % 10)
            result = result.next
            l1 = l1.next if l1 else l1
            l2 = l2.next if l2 else l2
        if carry != 0:
            result.next = ListNode(carry)
        return head.next 
```

```scala
/**
 * Definition for singly-linked list.
 * class ListNode(var _x: Int = 0) {
 *   var next: ListNode = null
 *   var x: Int = _x
 * }
 */
object Solution {
    def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
        var head = new ListNode(0)
        var (result,p,q) = (head,l1,l2)
        var carry = 0
        while(p != null || q != null){
            var sum = carry + (if (p != null) p.x else 0) + (if (q != null) q.x else 0)
            carry = sum / 10
            result.next = new ListNode(sum % 10)
            result = result.next
            if(p != null) p = p.next 
            if(q != null) q = q.next
        }
        if(carry != 0){
            result.next = new ListNode(carry)
        }
        head.next
    }
}

/**
 * Definition for singly-linked list.
 * class ListNode(var _x: Int = 0) {
 *   var next: ListNode = null
 *   var x: Int = _x
 * }
 */
object Solution {
    def mergeTwoLists(l1: ListNode, l2: ListNode): ListNode = {
        if(l1 == null) return l2;
        if(l2 == null) return l1;
        if(l1.x <= l2.x){
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }else{
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }
}
```

```cpp
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        ListNode* head = new ListNode(0);
        ListNode *result = head, *p = l1, *q = l2;
        int carry = 0;
        while(p || q){
            int x1 = p ? p->val : 0;
            int x2 = q ? q->val : 0;
            carry += x1 + x2;
            result->next = new ListNode(carry % 10);
            carry /= 10;
            result = result->next;
            if(p) p = p->next;
            if(q) q = q->next;
        }
        if(carry){
            result->next = new ListNode(carry);
        }
        return head->next;
    }
};
```