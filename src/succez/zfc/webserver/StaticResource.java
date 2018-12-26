package succez.zfc.webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import succez.zfc.basicpractice.FileUtil;
import succez.zfc.entity.FileType;
import succez.zfc.entity.Status;
import succez.zfc.util.logger.Logger;
/**
 * 处理静态资源
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年12月14日
 */
public class StaticResource {

	public StaticResource(Request req, Response resp) throws IOException {
		init(req, resp);
	}

	private void init(Request req, Response resp) throws IOException {
		File file = new File(Server.WEB_ROOT + req.getPath());
		File file2 = new File(req.getPath());
		DataOutputStream out = null;
		PrintWriter pw = null;
		try {
			if (file.exists()) {
				//处理webRoot上的静态资源
				Logger.getLogger().debug("访问的文件为:{}", Server.WEB_ROOT + req.getPath());
				//设置文件类型
				String type = req.getPath().substring(req.getPath().indexOf(".") + 1);
				resp.addHeader(FileType.getType(type.toUpperCase()));
				resp.sendHeader();
				Logger.getLogger().debug("开始将文件打包");
				//设置报文体
				byte[] b = FileUtil.fileToBuf2(Server.WEB_ROOT + req.getPath());
				Logger.getLogger().debug("开始向浏览器写入数据.....");
				out = new DataOutputStream(resp.getOs());
				if (null != b) {
					out.write(b);
				}
				out.flush();
			}
			else if (file2.exists()) {
				//处理本地的静态资源 如 D:/
				Logger.getLogger().debug("访问的文件为:{}", req.getPath());
				//设置文件类型
				String type = req.getPath().substring(req.getPath().indexOf(".") + 1);
				resp.addHeader(FileType.getType(type.toUpperCase()));
				resp.sendHeader();
				Logger.getLogger().debug("开始将文件打包");
				//设置报文体
				byte[] b = FileUtil.fileToBuf2(req.getPath());
				Logger.getLogger().debug("开始向浏览器写入数据.....");
				out = new DataOutputStream(resp.getOs());
				if (null != b) {
					out.write(b);
				}
				out.flush();
			}
			else {
				Logger.getLogger().debug("资源不存在！");
				pw = resp.getWriter();
				pw.println(req.getVersion() + " " + Status.NOT_FOUND + "\r\n");
				String str = "<h1>404 File Not Found</h1>";
				pw.println(str);
				pw.flush();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			new RuntimeException(e);
		}
		finally {
			if (out != null) {
				out.close();
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

}
