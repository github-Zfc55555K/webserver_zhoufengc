package succez.zfc.basicpractice;

/***
 * 转换工具
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月16日
 */
public class ConvertUtil {

	public final static char[] CH = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 将十进制数字 转为16进制字符串 范围支持所有整数 
	 * @param num
	 * @return
	 */
	public static String in2Hex(int num) {
		if(num == 0){
			return "0";
		}
		StringBuffer s = new StringBuffer();
		while (num != 0) {
			s.append(CH[num & 15]);
			num >>>= 4;
		}
		return s.reverse().toString();
	}
}
