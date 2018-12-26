package succez.zfc.webserver;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import succez.zfc.entity.Status;
import succez.zfc.util.logger.Logger;

/**
 * Servlet规范 
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月29日
 */
public class Servlet {

	public Servlet() {
	}
	
	public Servlet(Request req, Response resp) {
		init(req, resp);
		service(req,resp);
	}

	public void service(Request req, Response resp) {
		
	}

	private void init(Request req, Response resp) {
		Logger.getLogger().debug("动态资源请求！");
		String _class = Server.webMap.get(req.getPath());
		PrintWriter pw = null;
		if (_class == null) {
			Logger.getLogger().debug("无效的action");
			pw = resp.getWriter();
			pw.println(req.getVersion() + " " + Status.NOT_FOUND + "\r\n");
			String str = "<h1>404 File Not Found</h1>";
			pw.println(str);
			pw.flush();
			return;
		}
		try {
			Class<?> clz = Class.forName(_class);
			Method method = clz.getMethod("service", Request.class, Response.class);
			method.invoke(clz.newInstance(), req, resp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
