package succez.zfc.fileSystem.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import succez.zfc.fileSystem.pojo.MyFile;
import succez.zfc.util.logger.Logger;

/**
 * 文件系统服务层 
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年12月1日
 */

public class FileService {

	/**
	 * 根据路径获得 当前路径下所有文件或目录
	 * @param path
	 * @return
	 */
	public List<MyFile> getFilesByPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			Logger.getLogger().debug("文件或路径不存在...请检查conf.xml配置!");
			return null;
		}
		Logger.getLogger().debug("获取" + path + "目录下文件或目录");
		File[] files = file.listFiles();
		List<MyFile> list = new ArrayList<>();
		for (File f : files) {
			if (!f.isHidden()) {
				if (f.isDirectory()) {
					list.add(new MyFile(f.getName(), f.length(), 1, path + f.getName() + File.separator));
				}
				if (f.isFile()) {
					list.add(new MyFile(f.getName(), f.length(), 0, path + f.getName()));
				}
			}
		}
		return list;
	}

	/**
	 * 根据路径获得文件内容
	 * @param path
	 * @return
	 */
	public String getTxtByPath(String path) {
		StringBuffer stringBuffer = new StringBuffer(1024);
		BufferedReader br = null;
		try {
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
				String line = "";
				//一行一行的读取文本 并加上换行
				while ((line = br.readLine()) != null) {
					stringBuffer.append(line + "\n");
				}
			}
			finally {
				if (br != null) {
					br.close();
				}
			}
		}
		catch (Exception e) {
			new RuntimeException(e);
		}
		return stringBuffer.toString();
	}

	/**
	 * 将字符串写入文件中 覆盖写
	 * @param context 文件内容
	 * @param filePath 文件路径
	 */
	public void writeFile(String content, String filePath) {
		FileWriter fw = null;
		try {
			try {
				fw = new FileWriter(filePath);
				fw.write(content);
				fw.flush();
			}
			finally {
				if (fw != null) {
					fw.close();
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			new RuntimeException(e);
		}

	}
}
