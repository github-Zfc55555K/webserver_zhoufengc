package succez.zfc.webserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import succez.zfc.util.logger.Logger;
import succez.zfc.util.parse.ParseXml;

/**
 * 服务器初始化和启动类
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月22日
 */
public class Server {

	public static Map<String, String> map = ParseXml.getConfInfo();//解析conf.xml 获得端口号 和 最大连接数

	public static Map<String, String> webMap = ParseXml.getWebInfo();//解析web.xml 获得servlet映射

	public static final int PORT = Integer.parseInt(map.get("port"));//端口号

	public static final int POOL_SIZE = Integer.parseInt(map.get("poolSize"));//线程池最大连接数

	//webRoot根目录 如果在配置文件中存在则优先读取配置文件中的路径
	public static final String WEB_ROOT = map.get("webRoot") == ""
			? (System.getProperty("user.dir") + File.separator + "webRoot") : map.get("webRoot");

	public static void main(String[] args) {
		try {
			new Server().start(PORT);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger().error("服务器启动失败!!!....");
		}
	}

	/**
	 * 启动服务器方法
	 * @param port
	 * @throws IOException
	 */
	public void start(int port) throws IOException {
		ServerSocket s = new ServerSocket(port);
		Logger.getLogger().info("服务器开始监听   " + port + "端口 ");
		Logger.getLogger().info("服务器根目录为{}", WEB_ROOT);
		//将 连接放入线程池中
		ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
		while (true) {
			executor.submit(new ServerHandle(s.accept()));
		}
	}

}
