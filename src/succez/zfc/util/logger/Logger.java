package succez.zfc.util.logger;

import org.slf4j.LoggerFactory;
/**
 * 日志统一类 在logback.xml中进行配置
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月21日
 */
public class Logger {

	/**
	 * 获得logger
	 * @return logback的log接口
	 */
	public static org.slf4j.Logger getLogger(){
		org.slf4j.Logger logger = LoggerFactory.getLogger(getInvokingClassName());
		return logger;
	}
	/**
	 * 通过异常栈的形式获得  类 
	 * @return
	 */
	private static String getInvokingClassName(){
		return new Throwable().getStackTrace()[2].getClassName();
	}
}
