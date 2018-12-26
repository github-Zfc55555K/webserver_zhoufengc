package succez.zfc.basicpractice;

import java.util.Collections;

/**
 * 二叉树节点
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月20日
 */
public class TreeNode<T>{

	public T value;//节点值

	public TreeNode<T> left;//左子节点

	public TreeNode<T> right;//右子节点

	public TreeNode(T val) {
		this.value = val;
	}
}
