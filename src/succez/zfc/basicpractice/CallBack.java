package succez.zfc.basicpractice;

/**
 * 树的回调函数通用接口
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年12月9日
 */
public interface CallBack {

	/**
	 * 通用方法
	 * @param t
	 */
	void call(TreeNode<String> t);
}
