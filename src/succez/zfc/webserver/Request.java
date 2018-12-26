package succez.zfc.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import succez.zfc.util.logger.Logger;

/**
 * 处理请求
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月26日
 */
public class Request {

	private String uri;//资源目录

	private String method;//请求的方法

	private String version;//请求的版本号

	private URL url;//将请求的地址封装成URL，方便取数

	private Map<String, String> param;//请求的参数

	/**
	 * 获得请求的方法
	 * @return
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 获得请求的版本号
	 * @return
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 获得请求的路径 可以是webroot下的的静态资源 也可以是Servlet
	 * @return
	 */
	public String getPath() {
		return this.url.getPath();
	}

	/**
	 * 获得url中'?'后的参数
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		return this.param.get(key);
	}

	/**
	 * 接收socket传入的输入流
	 * @param is
	 * @throws IOException 
	 */
	public Request(InputStream is) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			String str = br.readLine();
			this.parseRequest(str);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//此处不能关闭 否则无法发送请求
			//			if (br != null) {
			//				br.close();
			//			}
		}
	}

	/**
	 * 解析请求的报文
	 * @param requestStr
	 */
	private void parseRequest(String requestStr) {

		String[] s = requestStr.split("\\s+");
		this.method = s[0];
		try {
			this.uri = URLDecoder.decode(s[1], "utf-8");
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		this.uri = s[1].replaceAll("%20", " ");
		this.version = s[2];
		//如果直接访问 localhost:端口号 将跳转到 web.xml中配置的默认主页中
		if (this.uri.equals("/")) {
			this.uri = Server.webMap.get("display");
		}
		this.parseUri();
		Logger.getLogger().info("请求的方法为{},资源目录为{},版本号为{}", method, uri, version);
	}

	/**
	 * 解析req 穿过来的uri
	 */
	private void parseUri() {
		// TODO Auto-generated method stub
		//构造一个完整的url便于取数
		try {
			this.url = new URL("http://" + this.uri);
			this.toMap(url.getQuery());
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 从url中获取参数 放至map中
	 * @param url
	 */
	private void toMap(String url) {
		this.param = new HashMap<>();
		if (url != null) {
			String[] arrTemp = url.split("&");
			for (String str : arrTemp) {
				String[] qs = str.split("=");
				param.put(qs[0], qs[1]);
			}
		}
	}

}
