package succez.zfc.test;

import org.junit.Assert;
import org.junit.Test;

import succez.zfc.basicpractice.FileUtil;

/**
 * 字节形式读取文件测试
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月20日
 */
public class FileUtilTest {

	public static final String EXIST_PATH = "D:/Users/test.txt"; //存在的路径

	public static final String NOT_EXIST_PATH = "D:/Users/test2.txt";//不存在的路径

	byte[] b;

	@Test
	public void fileToBufTest() {
		b = FileUtil.fileToBuf2(EXIST_PATH);
		StringBuffer s = new StringBuffer(100);
		for (byte temp : b) {
			s.append(temp);
		}
		Assert.assertEquals(s.toString(), "6566676869707172737475");
	}

	@Test
	public void fileToBufTest2() {
		b = FileUtil.fileToBuf2(NOT_EXIST_PATH);
		Assert.assertEquals(b, null);
	}
}
