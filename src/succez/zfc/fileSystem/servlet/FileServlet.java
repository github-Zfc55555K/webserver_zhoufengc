package succez.zfc.fileSystem.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import succez.zfc.basicpractice.FileUtil;
import succez.zfc.entity.FileType;
import succez.zfc.entity.Status;
import succez.zfc.fileSystem.pojo.MyFile;
import succez.zfc.fileSystem.service.FileService;
import succez.zfc.util.logger.Logger;
import succez.zfc.webserver.Request;
import succez.zfc.webserver.Response;
import succez.zfc.webserver.Server;
import succez.zfc.webserver.Servlet;

/**
 * 文件管理系统servlet
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年12月1日
 */
public class FileServlet extends Servlet {

	private FileService service;

	public FileServlet() {
		// TODO Auto-generated constructor stub
		Logger.getLogger().debug("动态加载succez.zfc.fileSystem.servlet.FileServlet");
	}

	/**
	 * 重写 父类的 service 方法
	 */
	@Override
	public void service(Request req, Response resp) {
		// TODO Auto-generated method stub
		super.service(req, resp);
		String from = req.getParameter("from");
		if (from.equals("fileSystem")) {
			Logger.getLogger().debug("开始初始化文件管理系统");
			this.execute(req, resp);
		}
		else if (from.equals("getFile")) {
			Logger.getLogger().debug("开始获取目录下的文件");
			this.getFile(req, resp);
		}
		else if (from.equals("downLoad")) {
			Logger.getLogger().debug("开始处理文件下载请求");
			this.executeFileDownLoad(req, resp);
		}
		else if (from.equals("editFile")) {
			Logger.getLogger().debug("开始处理文件编辑请求");
			this.executeEditFile(req, resp);
		}
		else if (from.equals("saveFile")) {
			Logger.getLogger().debug("开始处理保存文件请求");
			this.executeSaveFile(req, resp);
		}
		else {
			Logger.getLogger().error("不存在的URL参数");
		}
	}

	/**
	 * 处理保存文件请求
	 * @param req
	 * @param resp
	 */
	private void executeSaveFile(Request req, Response resp) {
		// TODO Auto-generated method stub
		this.service = new FileService();
		String content = req.getParameter("content");
		String filePath = req.getParameter("filePath");
		try {
			content = URLDecoder.decode(content, "utf-8");
			service.writeFile(content, filePath);
			PrintWriter pw = resp.getWriter();
			pw.println(req.getVersion() + " " + Status.OK + "\r\n");
			pw.print("ok");
			pw.flush();
			if (pw != null) {
				pw.close();
			}
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 处理文件编辑请求
	 * @param req
	 * @param resp
	 */
	private void executeEditFile(Request req, Response resp) {
		// TODO Auto-generated method stub
		service = new FileService();
		PrintWriter pw = resp.getWriter();
		pw.println(req.getVersion() + " " + Status.OK + "\r\n");
		String result = service.getTxtByPath(req.getParameter("filePath"));
		pw.println(result);
		pw.flush();
		if (pw != null) {
			pw.close();
		}
	}

	/**
	 * 处理文件下载
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void executeFileDownLoad(Request req, Response resp) {
		// TODO Auto-generated method stub
		//设置下载的报文头
		resp.addHeader(FileType.DOWNLOAD);
		resp.addHeader("Content-Disposition:attachment;filename=" + req.getParameter("path"));
		resp.sendHeader();
		DataOutputStream out = new DataOutputStream(resp.getOs());
		try {
			try {
				byte[] b = FileUtil.fileToBuf2(req.getParameter("path"));
				if (b != null) {
					out.write(b);
				}
				out.flush();
			}
			finally {
				if (out != null) {
					out.close();
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			new RuntimeException(e);
		}

	}

	/**
	 * 根据路径获得路径下的文件
	 * @param req
	 * @param resp
	 */
	private void getFile(Request req, Response resp) {
		// TODO Auto-generated method stub
		service = new FileService();
		PrintWriter pw = resp.getWriter();
		List<MyFile> list = service.getFilesByPath(req.getParameter("filePath"));
		pw.println(req.getVersion() + " " + Status.OK + "\r\n");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(list);
			pw.println(json);
			pw.flush();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			new RuntimeException(e);
		}
		finally {
			if (pw != null) {
				pw.close();
			}
		}

	}

	/**
	 * 初始化文件管理系统
	 * @param req
	 * @param resp
	 */
	private void execute(Request req, Response resp) {
		// TODO Auto-generated method stub
		service = new FileService();
		PrintWriter pw = resp.getWriter();
		List<MyFile> list = service.getFilesByPath(Server.map.get("path"));
		try {
			if (null == list) {
				pw.println(req.getVersion() + " " + Status.OK + "\r\n");
				String str = "fail";
				pw.print(str);
				pw.flush();
			}
			else {
				pw.println(req.getVersion() + " " + Status.OK + "\r\n");
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(list);
				pw.println(json);
				pw.flush();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			new RuntimeException(e);
		}
		finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
