package succez.zfc.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import succez.zfc.util.logger.Logger;

/**
 * 处理服务器发送的socket 接入请求和应答
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月22日
 */
public class ServerHandle implements Runnable {

	private Socket socket;

	public ServerHandle(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			Request req = new Request(is);
			Response resp = new Response(os);

			if (req.getPath().equals("/favicon.ico")) {
				Logger.getLogger().info("/favicon.ico无效的请求！");
				return;
			}
			//这里会从配置文件中读取后缀名
			if (req.getPath().endsWith(Server.webMap.get("action"))) {
				//处理动态资源 或者特定请求
				new Servlet(req, resp);
			}
			else {
				//处理 静态资源请求 如.html .png 
				new StaticResource(req, resp);
			}
			socket.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger().error("连接超时！....");
		}
	}

}
