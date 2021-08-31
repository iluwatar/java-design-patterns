# BSTIterator

An implementation of the Iterator design pattern, for the Binary Search Tree data structure. A great 
explanation of BSTs can be found in this 
[video tutorial](https://www.youtube.com/watch?v=i_Q0v_Ct5lY).

一个迭代器设计模式的实现，用于二叉搜索树的数据结构。在这个视频教程中可以找到关于bst的很好的解释。

### What It Does

This iterator assumes that the given binary search tree inserts nodes of smaller 
value to the left, and nodes of larger value to the right of current node. Accordingly, 
this iterator will return nodes according to "In Order" binary tree traversal. 
This means that given a binary search tree like the following, the iterator would 
return values in order: 1, 3, 4, 6, 7, 8, 10, 13, 14. 

这个迭代器假设给定的二叉搜索树在左边插入值较小的节点，在当前节点的右边插入值较大的节点。
相应地，此迭代器将返回节点按“In Order”顺序遍历二叉树。这意味着，给定如下二叉搜索树，迭代器将按顺序返回值:1、3、4、6、7、8、10、13、14。

![BST](../../../../../../../etc/bst.jpg "Binary Search Tree")  

### How It's Done

**The trivial solution** to a binary search tree iterator would be to construct a List (or similar 
linear data structure) when you construct the BSTIterator. This would require traversing the entire
BST, adding each node value to your list as you go. The downside to the trivial solution is twofold.
You're front loading the work by requiring the BSTIterator's constructor to traverse the entire tree, 
and you're taking up more memory by maintaining (worst case) every node in the tree in a separate 
data structure. In Big O terms, here are the costs, where n is the number of nodes in the tree:

二叉搜索树迭代器的简单解决方案是在构造BSTIterator时构造一个List(或类似的线性数据结构)。这需要遍历整个BST，将每个节点值添加到列表中。
平凡解的缺点有两个。您通过要求BSTIterator的构造函数遍历整个树来预先加载工作，并且通过在单独的数据结构中维护(最坏情况下)树中的每个节点来占用更多内存。
用大O表示，这里是代价，n是树中的节点数:

* Constructor Run Time: O(n) 
* `next()` Run Time: O(1)
* `hasNext()` Run Time: O(1)
* Extra Space: O(n)

**A better solution** is to maintain _only_ the path to the next smallest node. For instance, given 
the BST above, when you first create your BSTIterator, instead of traversing the entire tree, you 
would navigate to the next smallest node (in this case, 1), pushing nodes onto a stack along the way. 
Your BSTIterator Constructor would look like:

更好的解决方案是只维护到下一个最小节点的路径。例如，给定上面的BST，当您第一次创建BSTIterator时，不需要遍历整个树，
而是导航到下一个最小的节点(在本例中为1)，一路上将节点压入堆栈。你的BSTIterator构造函数看起来像这样:
```
private ArrayDeque<TreeNode> pathStack;

public BSTIterator(TreeNode root) {
  pathStack = new ArrayDeque<>();
  pushPathToNextSmallest(root);
}

private void pushPathToNextSmallest(TreeNode node) {
  while (node != null) {
    pathStack.push(node);
    node = node.getLeft();
  }
}
```

After the constructor is called our BST, your `pathStack` would look like this:

在构造函数被调用我们的BST后，你的pathStack看起来像这样:

1\
3\
8

This way, you're certain of what the next smallest node is because it lives on top of your path 
stack. In order to maintain the integrity of this path stack, when you call `next()` and pop a 
node off the stack, you must check to see if it has a right child. If it does, then you must follow the right 
child's path to the next smallest node (pushing onto your path stack as you go). Given our above example, 
calling `next()` on our BSTIterator twice would return node "3". Node "3" has a right child, indicating 
a path to a node smaller than 3's parent. In this case, you would push node "6" onto the stack, 
and node "4" onto the stack. `next()` would look like this:

这样，您就可以确定下一个最小的节点是什么，因为它位于路径堆栈的顶部。为了保持这个路径堆栈的完整性，当您调用next()并将一个节点从堆栈中取出时，
您必须检查它是否有一个正确的子节点。如果是，那么您必须沿着正确的子节点路径到达下一个最小的节点(在运行时压入路径堆栈)。
根据上面的例子，在BSTIterator上调用next()两次将返回节点“3”。节点“3”有一个右子节点，表示到比3的父节点小的节点的路径。
在这种情况下，将节点“6”压入堆栈，将节点“4”压入堆栈。Next()看起来像这样:

```
public TreeNode next() throws IllegalStateException {
  // If the user calls next() and hasNext() is false
  if (pathStack.isEmpty()) {
    throw new IllegalStateException();
  }
  var next = pathStack.pop();
  // follow right child to next smallest node
  pushPathToNextSmallest(next.getRight());
  return next;
}
```

**Key Concept:** The path to the smallest node of a given subtree is navigating straight to the 
leftmost node of that subtree.

关键概念:到给定子树最小节点的路径是直接导航到该子树的最左节点。

In Big O terms, here are the costs for our improved solution, where h is the height of the tree:

用大O表示，这是我们改进的解决方案的成本，其中h是树的高度:
* Constructor Run Time: O(h) 
* `next()` Amortized Run Time: O(1)
* `hasNext()` Run Time: O(1)
* Extra Space: O(h)

As you can see, this solution more evenly distributes the work. It yields the same amortized 
runtime for `next()`, reduces the run time of the constructor, and uses less extra space.

如您所见，此解决方案更均匀地分配了工作。它为next()产生相同的平摊运行时，减少了构造函数的运行时间，并使用更少的额外空间。
