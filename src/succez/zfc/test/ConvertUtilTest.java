package succez.zfc.test;

import org.junit.Assert;
import org.junit.Test;

import succez.zfc.basicpractice.ConvertUtil;

/**
 * 10进制数转十六进制测试
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月20日
 */
public class ConvertUtilTest {

	@Test
	public void int2HexTest() {
		//常规测试 正负数 测试
		Assert.assertEquals(ConvertUtil.in2Hex(-123456).toLowerCase(), "fffe1dc0");
		Assert.assertEquals(ConvertUtil.in2Hex(123456).toLowerCase(), "1e240");
		//测试 边界 0 和 最大值 最小值
		Assert.assertEquals(ConvertUtil.in2Hex(0).toLowerCase(), "0");
		Assert.assertEquals(ConvertUtil.in2Hex(Integer.MAX_VALUE + 1).toLowerCase(), "80000000");
		Assert.assertEquals(ConvertUtil.in2Hex(-Integer.MAX_VALUE).toLowerCase(), "80000001");
		//常用颜色代码测试
		//浅粉红
		Assert.assertEquals(ConvertUtil.in2Hex(16758465).toLowerCase(), "ffb6c1");
		//纯蓝
		Assert.assertEquals(ConvertUtil.in2Hex(255).toLowerCase(), "ff");
	}
}
