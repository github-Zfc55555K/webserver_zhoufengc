package succez.zfc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import succez.zfc.basicpractice.CallBack;
import succez.zfc.basicpractice.TreeLevel;
import succez.zfc.basicpractice.TreeNode;

/**
 * 数的按层输出 测试
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月20日
 */
public class TreeLevelTest {

	TreeNode<String> root = null;

	TreeNode<String> root2 = null;

	static int sum;

	static List<TreeNode<String>> list = new ArrayList<>();

	@Before
	public void creatTree() {
		//节点初始化 设置值
		this.root = new TreeNode<String>("AA");
		TreeNode<String> node1 = new TreeNode<String>("BB");
		TreeNode<String> node2 = new TreeNode<String>("CC");
		TreeNode<String> node3 = new TreeNode<String>("DD");
		TreeNode<String> node4 = new TreeNode<String>("EE");
		TreeNode<String> node5 = new TreeNode<String>("FF");
		TreeNode<String> node6 = new TreeNode<String>("GG");
		root.left = node1;
		root.right = node2;
		node1.left = node3;
		node1.right = node4;
		node2.left = node5;
		node2.right = node6;
		this.root2 = new TreeNode<String>("1");
		TreeNode<String> node11 = new TreeNode<String>("2");
		TreeNode<String> node12 = new TreeNode<String>("3");
		TreeNode<String> node13 = new TreeNode<String>("4");
		TreeNode<String> node14 = new TreeNode<String>("5");
		TreeNode<String> node15 = new TreeNode<String>("6");
		TreeNode<String> node16 = new TreeNode<String>("7");
		root2.left = node11;
		root2.right = node12;
		node11.left = node13;
		node11.right = node14;
		node12.left = node15;
		node12.right = node16;
	}

	@Test
	public void treeLevelTest() {
		TreeLevel t = new TreeLevel();
		Assert.assertEquals(t.showBylevel(root), "AA|BBCC|DDEEFFGG|");
		//		Assert.assertEquals(t.showAll(root), "AABBCCDDEEFFGG");
		Assert.assertEquals(t.showBylevel(root, 0), "AA");
		Assert.assertEquals(t.showBylevel(root, 1), "BBCC");
		Assert.assertEquals(t.showBylevel(root, 2), "DDEEFFGG");
		Assert.assertEquals(t.showBylevel(root, 3), null);
	}

	/**
	 * 测试回调函数
	 */
	@Test
	public void testCallBack() {
		TreeLevel t2 = new TreeLevel();
		t2.showAll(root2, new CallBack() {

			@Override
			public void call(TreeNode<String> t) {
				// TODO Auto-generated method stub
				System.out.print(t.value);
			}
		});
		System.out.println();
		t2.showAll(root2, new CallBack() {
			@Override
			public void call(TreeNode<String> t) {
				// TODO Auto-generated method stub
				sum += Integer.parseInt(t.value);
			}

		});
		System.out.println(sum);
		t2.showAll(root2, new CallBack() {
			@Override
			public void call(TreeNode<String> t) {
				// TODO Auto-generated method stub
				list.add(t);
			}
		});
		for (TreeNode<String> n : list) {
			System.out.print(n.value);
		}
	}

	@Test
	public void testRe() {
		TreeLevel t3 = new TreeLevel();
		TreeMap<Integer, String> map = new TreeMap<>();
		t3.showAllRe(root, map, 1);
		for (String s : map.values()) {
			System.out.print(s);
		}
	}
}
