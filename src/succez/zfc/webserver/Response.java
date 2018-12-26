package succez.zfc.webserver;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import succez.zfc.entity.Status;

/**
 * 对 请求 做出应答
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月22日
 */
public class Response {

	private OutputStream os;

	public List<String> head;

	public Response(OutputStream os) {
		head = new ArrayList<>();
		//设置版本、状态码
		head.add("HTTP/1.1" + " " + Status.OK);
		//设置连接状态
		head.add("Connection: close");
		//设置服务器名称
		head.add("Server: zfc'sWebServer");
		this.os = os;
	}

	public OutputStream getOs() {
		return os;
	}

	/**
	 * 增加报文头部
	 * @param s
	 */
	public void addHeader(String s) {
		this.head.add(s);
	}

	/**
	 * 发送报文头部
	 */
	public void sendHeader() {
		DataOutputStream out = new DataOutputStream(this.os);
		try {
			try {
				for (String s : this.head) {
					out.writeBytes(s + "\r\n");
				}
				out.writeBytes("\r\n");
			}
			finally {
				if (out != null) {
//					关流则页面无法显示
//					out.close();
				}
			}
		}
		catch (Exception e) {
			new RuntimeException(e);
		}
	}

	/**
	 * 封装outputStream 
	 * @return
	 */
	public PrintWriter getWriter() {
		PrintWriter pw = new PrintWriter(os);
		return pw;
	}
}
