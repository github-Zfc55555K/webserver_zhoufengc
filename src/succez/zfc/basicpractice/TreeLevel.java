package succez.zfc.basicpractice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * 按层遍历树 
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月20日
 */
public class TreeLevel {
	
	/**
	 * 按层次输出二叉树
	 * @param 根节点：root(TreeNode)
	 * @return 
	 */
	public String showBylevel(TreeNode<String> root) {
		//队列 用于放 处于同一层的节点
		LinkedList<TreeNode<String>> queue = new LinkedList<>();
		//输出结果
		StringBuffer result = new StringBuffer(100);
		// 队列中 弹出的 节点
		TreeNode<String> queueNode = root;
		// 当前访问树的 节点
		TreeNode<String> treeNode = null;
		//记录上一层最后一个节点
		TreeNode<String> lastNode = root;
		//初始时根节点进队列
		queue.add(root);
		//循环访问队列 直到队列为空
		while (queue.size() != 0) {
			queueNode = queue.poll();
			result.append(queueNode.value);

			if (queueNode.left != null) {
				queue.add(queueNode.left);
				treeNode = queueNode.left;
			}

			if (queueNode.right != null) {
				queue.add(queueNode.right);
				treeNode = queueNode.right;
			}
			//读到上一层的最后一个节点 
			if (queueNode == lastNode) {
				result.append("|");
				//最后节点 指向 当前树中的标记节点
				lastNode = treeNode;
			}
		}
		return result.toString();
	}

	/**
	 * 重载方法 可以根据层数 单层输出
	 * @param root 根节点
	 * @param level 层数
	 * 
	 * @return  如果Level 超过最大层数返回null 否则返回改层
	 */
	public String showBylevel(TreeNode<String> root, int level) {
		String[] r = this.showBylevel(root).split("\\|");
		//如果Level 超过最大层数返回null
		if (level > r.length - 1) {
			return null;
		}
		return r[level];
	}

	/**
	 * 按层遍历所有节点
	 * @param root
	 * @return
	 */
	public void showAll(TreeNode<String> root, CallBack cb) {
		StringBuffer result = new StringBuffer(100);
		//队列 用于放 处于同一层的节点
		LinkedList<TreeNode<String>> queue = new LinkedList<>();
		// 队列中 弹出的 节点
		TreeNode<String> queueNode = root;
		queue.add(root);
		//循环访问队列 直到队列为空
		while (queue.size() != 0) {
			queueNode = queue.poll();
			cb.call(queueNode);
			result.append(queueNode.value);
			if (queueNode.left != null) {
				queue.add(queueNode.left);
			}
			if (queueNode.right != null) {
				queue.add(queueNode.right);
			}
		}
	}

	/**
	 * 层次遍历递归实现
	 * @param root
	 * @param result
	 * @param i
	 */
	public void showAllRe(TreeNode<String> root, TreeMap<Integer, String> result, int i) {
		if (root.left != null) {
			showAllRe(root.left, result, 2 * i);
		}
		if (root.right != null) {
			showAllRe(root.right, result, 2 * i + 1);
		}
		result.put(i, root.value);
	}
}
