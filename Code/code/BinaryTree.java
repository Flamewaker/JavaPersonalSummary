package com.todd.code;

import java.util.*;

/**
 * @author todd
 * @date 2020/5/16 9:30
 * @description: 二叉树专题
 * 树是一种经常用到的数据结构，用来模拟具有树状结构性质的数据集合。树里的每一个节点有一个根植和一个包含所有子节点的列表。从图的观点来看，树也可视为一个拥有N个节点和N-1条边的一个有向无环图。
 * 二叉树是一种更为典型的树树状结构。如它名字所描述的那样，二叉树是每个节点最多有两个子树的树结构，通常子树被称作“左子树”和“右子树”。
 * 其中在遍历二叉树中，俩种写法的不同，一种直接将root入栈，一种在循环中入栈。
 */
public class BinaryTree {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * 给定一个二叉树，返回它的前序遍历。先访问根节点，然后前序遍历左子树，再前序遍历右子树。
     * @param root
     * @return List<Integer>
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> preorderList = new ArrayList<>();
        preorderRecursively(root, preorderList);
        return preorderList;
    }

    /**
     * 前序遍历：递归实现
     * @param root
     * @param ans
     */
    private void preorderRecursively(TreeNode root, ArrayList<Integer> ans){
        if (root != null) {
            ans.add(root.val);
            preorderRecursively(root.left, ans);
            preorderRecursively(root.right, ans);
        }
    }

    /**
     * 前序遍历：非递归实现，推荐
     * @param root
     * @param ans
     */
    private void preorderIteratively(TreeNode root, ArrayList<Integer> ans){
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.offerLast(cur);
                ans.add(cur.val);
                cur = cur.left;
            }
            cur = stack.pollLast();
            cur = cur.right;
        }
    }

    /**
     * 前序遍历：非递归实现，另一种写法
     * @param root
     * @param ans
     */
    private void preorderIteratively2(TreeNode root, ArrayList<Integer> ans){
        if(root == null) {
            return ;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        stack.offerLast(root);
        while(!stack.isEmpty()) {
            TreeNode cur = stack.pollLast();
            ans.add(cur.val);
            //注意出栈的顺序
            if (cur.right != null) {
                stack.offerLast(cur.left);
            }
            if (cur.left != null) {
                stack.offerLast(cur.left);
            }
        }
    }


    /**
     * 给定一个二叉树，返回它的中序遍历。中序遍历根节点的左子树，然后访问根节点，最后中序遍历右子树。
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> inorderList = new ArrayList<>();
        inorderRecursively(root, inorderList);
        return inorderList;
    }

    /**
     * 中序遍历：递归实现
     * @param root
     * @param ans
     */
    private void inorderRecursively(TreeNode root, ArrayList<Integer> ans) {
        if (root != null) {
            inorderRecursively(root.left, ans);
            ans.add(root.val);
            inorderRecursively(root.right, ans);
        }
    }

    /**
     * 中序遍历：非递归实现，推荐
     * @param root
     * @param ans
     */
    private void inorderIteratively(TreeNode root, ArrayList<Integer> ans) {
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.left;
            }
            cur = stack.pollLast();
            ans.add(cur.val);
            cur = cur.right;
        }
    }

    /**
     * 中序遍历：非递归实现，另一种写法
     * @param root
     * @param ans
     */
    private void inorderIteratively2(TreeNode root, ArrayList<Integer> ans) {
        if(root == null) {
            return ;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        while(root != null) {
            stack.offerLast(root);
            root = root.left;
        }
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pollLast();
            ans.add(cur.val);
            TreeNode temp = cur.right;
            while (temp != null) {
                stack.offerLast(temp);
                temp = temp.left;
            }
        }
    }

    /**
     * 给定一个二叉树，返回它的后序遍历。后序遍历根节点的左子树，后序遍历根节点的右子树，最后访问根节点。
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> postorderList = new ArrayList<>();
        postorderRecursively(root, postorderList);
        return postorderList;
    }

    /**
     * 后序遍历：递归实现
     * @param root
     * @param ans
     */
    private void postorderRecursively(TreeNode root, ArrayList<Integer> ans) {
        if (root != null) {
            postorderRecursively(root.left, ans);
            postorderRecursively(root.right, ans);
            ans.add(root.val);
        }
    }

    /**
     * 后序遍历：非递归实现
     * 迭代写法，利用pre记录上一个访问过的结点，与当前结点比较，如果是当前结点的子节点，说明其左右结点均已访问，将当前结点出栈，更新pre记录的对象。
     * 变量pre用于保存当前栈顶所弹出的元素，判断 cur.right == pre是为了避免重复访问同一个元素而陷入死循环当中。
     * @param root
     * @param ans
     */
    private void postorderIteratively(TreeNode root, ArrayList<Integer> ans) {
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode cur = root;
        TreeNode pre = null;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.offerLast(cur);
                cur = cur.left;
            }
            cur = stack.peekLast();
            //当右子树遍历完后，pre记录右子树的根节点，如果与当前根节点的右节点相同或右节点为null，则代表右子树已经访问完毕，直接访问根节点，而再避免访问右子树。
            if(cur.right == null || cur.right == pre) {
                ans.add(cur.val);
                stack.pollLast();
                pre = cur;
                //将cur设置为null，使得下次不会重复遍历左子树。
                cur = null;
            } else {
                cur = cur.right;
            }
        }
    }

    /**
     * 后序遍历：非递归实现
     * 取巧方法，该写法的访问顺序并不是后序遍历，而是利用先序遍历“根左右”的遍历顺序，将先序遍历顺序更改为“根右左”，反转结果List，得到结果顺序为“左右根”。
     * @param root
     * @param ans
     */
    private void postorderIteratively2(TreeNode root, LinkedList<Integer> ans) {
        if(root == null) {
            return ;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        stack.offerLast(root);
        while(!stack.isEmpty()) {
            TreeNode cur = stack.pollLast();
            //在前面插入结果
            ans.addFirst(cur.val);
            if (cur.left != null) {
                stack.offerLast(cur.left);
            }
            if (cur.right != null) {
                stack.offerLast(cur.right);
            }
        }
    }

    /**
     * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。（即逐层地，从左到右访问所有节点）。
     * 队列实现
     * @param root
     * @return List<List<Integer>>
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> ans = new LinkedList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()) {
            LinkedList<Integer> levelAns = new LinkedList<>();
            for(int i = q.size(); i > 0; i--) {
                TreeNode cur = q.poll();
                levelAns.add(cur.val);
                if(cur.left != null) {
                    q.offer(cur.left);
                }
                if(cur.right != null) {
                    q.offer(cur.right);
                }
            }
            ans.add(levelAns);
        }

        return ans;
    }

    /**
     * 自顶向下思路（前序遍历的一种）
     * 给定一个二叉树，请寻找它的最大深度
     * 1. return if root is null
     * 2. if root is a leaf node:
     * 3.      answer = max(answer, depth)         // update the answer if needed (answer is a global variant)
     * 4. maximum_depth(root.left, depth + 1)      // call the function recursively for left child
     * 5. maximum_depth(root.right, depth + 1)     // call the function recursively for right child
     *
     * 自底向上思路（后序遍历的一种）
     * 给定一个二叉树，请寻找它的最大深度
     * 1. return 0 if root is null                 // return 0 for null node
     * 2. left_depth = maximum_depth(root.left)
     * 3. right_depth = maximum_depth(root.right)
     * 4. return max(left_depth, right_depth) + 1  // return depth of the subtree rooted at root
     */

    /**
     * 给定一个二叉树，找出其最大深度。二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * @param root
     * @return int
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return left > right ? left + 1 : right + 1;
    }


    /**
     * 给定一个二叉树，检查它是否是镜像对称的。
     * @param root
     * @return boolean
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return judgeSymmetric(root.left, root.right);
    }

    public boolean judgeSymmetric(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null || a.val != b.val) {
            return false;
        }
        return judgeSymmetric(a.left, b.right) && judgeSymmetric(a.right, b.left);
    }

    /**
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     * @param root
     * @param sum
     * @return boolean
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        int nextSum = sum - root.val;
        if (root.left == null && root.right == null) {
            return nextSum == 0;
        }
        return hasPathSum(root.left, nextSum) || hasPathSum(root.right, nextSum);
    }



    public ArrayList<Integer> path = new ArrayList<>();
    public List<List<Integer>> lists = new LinkedList<>();

    /**
     * 给定一个二叉树和一个目标和，找到所有从根节点到叶子节点路径总和等于给定目标和的路径。
     * @param root
     * @param sum
     * @return List<List<Integer>>
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if(root == null){
            return lists;
        }
        findPath(root, sum);
        return lists;
    }

    public void findPath(TreeNode root, int sum){
        path.add(root.val);
        int nextSum = sum - root.val;
        if(nextSum == 0 && root.left == null && root.right == null) {
            lists.add(new ArrayList<>(path));
        }
        if(root.left != null) {
            findPath(root.left, nextSum);
        }
        if(root.right != null) {
            findPath(root.right, nextSum);
        }
        path.remove(path.size() - 1);
    }

    /**
     * 模板题，记住
     * 根据一棵树的中序遍历与后序遍历构造二叉树。 反正必须要有中序才能构建，因为没有中序，你没办法确定树的形状。
     * 通常从先序序列或者后序序列开始，根据不同遍历方法的规律，选择合适的节点构造树。例如：先序序列的第一个节点是根节点，然后是它的左孩子，右孩子等等。
     * 从先序/后序序列中找到根节点，根据根节点将中序序列分为左子树和右子树。从中序序列中获得的信息是：如果当前子树为空（返回 None），否则继续构造子树。
     *
     * @param inorder
     * @param postorder
     * @return TreeNode
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return helper(inorder, postorder, postorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode helper(int[] inorder, int[] postorder, int postEnd, int inStart, int inEnd) {
        //子树为空, 返回null
        if (inStart > inEnd) {
            return null;
        }

        //选择后序遍历的最后一个节点作为根节点
        int currentVal = postorder[postEnd];
        TreeNode current = new TreeNode(currentVal);

        int inIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == currentVal) {
                inIndex = i;
            }
        }

        //假设根节点在中序遍历中索引为 index。从 in_left 到 index - 1 属于左子树，从 index + 1 到 in_right 属于右子树。
        //根据后序遍历逻辑，递归创建右子树 helper(index + 1, in_right) 和左子树 helper(in_left, index - 1)。
        //返回根节点 root。
        //inEnd - inIndex 计算的是右子树节点的个数
        TreeNode left = helper(inorder, postorder, postEnd - (inEnd- inIndex) - 1, inStart, inIndex - 1);
        TreeNode right = helper(inorder, postorder, postEnd - 1, inIndex + 1, inEnd);
        current.left = left;
        current.right = right;
        return current;
    }

    /**
     * 根据一棵树的前序遍历与中序遍历构造二叉树。
     *
     * @param inorder
     * @param preorder
     * @return TreeNode
     */
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        return helper2(inorder, preorder, 0, 0, inorder.length - 1);
    }

    public TreeNode helper2(int[] inorder, int[] preorder, int preStart, int inStart, int inEnd) {
        //子树为空, 返回null
        if (inStart > inEnd) {
            return null;
        }

        //选择后序遍历的最后一个节点作为根节点
        int currentVal = preorder[preStart];
        TreeNode current = new TreeNode(currentVal);

        int inIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == currentVal) {
                inIndex = i;
            }
        }

        TreeNode left = helper2(inorder, preorder, preStart + 1, inStart, inIndex - 1);
        TreeNode right = helper2(inorder, preorder, preStart + (inIndex - inStart) + 1, inIndex + 1, inEnd);
        current.left = left;
        current.right = right;
        return current;
    }


    /**
     * 重要，记住***************************************************
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * 1. 利用深度优先搜索记录路径，找链表的第一个公共节点。
     * 2. 递归查询两个节点p，q，如果某个节点等于节点p或节点q，则返回该节点的值给父节点。
     * 如果当前节点的左右子树分别包括p和q节点，那么这个节点必然是所求的解。
     * 如果当前节点有一个子树的返回值为p或q节点，则返回该值。（告诉父节点有一个节点存在其子树中）
     * 如果当前节点的两个子树返回值都为空，则返回空指针。
     *
     * @param root
     * @param p
     * @param q
     * @return TreeNode
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left == null? right : left;
    }

    /**
     * 重要*******
     * 请实现两个函数，分别用来序列化和反序列化二叉树。基于层次遍历。
     * // Your Codec object will be instantiated and called as such:
     * // Codec codec = new Codec();
     * // codec.deserialize(codec.serialize(root));
     */

    /**
     * Encodes a tree to a single string.
     * @param root
     * @return
     */
    public String serialize(TreeNode root) {
        if(root == null) {
            return "[]";
        }
        StringBuilder res = new StringBuilder("[");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if(node != null){
                res.append(node.val + ",");
                queue.add(node.left);
                queue.add(node.right);
            } else {
                res.append("null,");
            }
        }
        res.deleteCharAt(res.length() - 1);
        res.append("]");

        return res.toString();
    }

    /**
     * Decodes your encoded data to tree.
     * @param data
     * @return
     */
    public TreeNode deserialize(String data) {
        if("[]".equals(data)) {
            return null;
        }

        String[] vals = data.substring(1, data.length()-1).split(",");
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while(!queue.isEmpty()){
            TreeNode node = queue.poll();
            if(!"null".equals(vals[i])) {
                node.left = new TreeNode(Integer.parseInt(vals[i]));
                queue.add(node.left);
            }
            i++;
            if(!"null".equals(vals[i])) {
                node.right = new TreeNode(Integer.parseInt(vals[i]));
                queue.add(node.right);
            }
            i++;
        }

        return root;
    }

}
