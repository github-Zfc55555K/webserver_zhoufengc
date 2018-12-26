package succez.zfc.entity;

/**
 *  HTTP请求的响应状态常量
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月21日
 */
public interface Status {

	String OK = "200 OK"; //请求成功

	String BAD_REQUEST = "400 Bad Request";//错误的请求

	String NOT_FOUND = "404 Not Found";//找不到资源

	String INTERNAL_SERVER_ERROR = "500 Internal Server Error";//系统内部错误
}
