package succez.zfc.basicpractice;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import succez.zfc.util.logger.Logger;

/***
 * 文件工具类
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月16日
 */
public class FileUtil {
	/**
	 * 将文件转为字节数组
	 * @param filePath文件路径
	 * @return 返回字节数组 失败 返回 null
	 */
	public static byte[] fileToBuf(String filePath) {
		File file = new File(filePath);
		//如果文件不存在 返回空
		if (!file.exists()) {
			return null;
		}
		InputStream is = null;
		long len = file.length();
		//已读的 记作偏移量
		int offset = 0;
		//本次读的量
		int readNum = 0;
		byte[] b = new byte[(int) len];
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			long startTime = System.currentTimeMillis();
			while (offset < len && (readNum = is.read(b, offset, b.length - offset)) >= 0) {
				offset += readNum;
			}
			long endTime = System.currentTimeMillis();
			return b;
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				is.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将文件转为字节数组输出
	 * 此方法效率较高 设置一此读取最大量为1K
	 * @param filePath 文件路径
	 * @return 字节数组 失败返回空
	 * @throws IOException 
	 */
	public static byte[] fileToBuf2(String filePath) {
		try {
			Logger.getLogger().debug("开始将文件转为字节形式");
			File file = new File(filePath);
			//如果文件不存在 返回空
			if (!file.exists()) {
				Logger.getLogger().debug("文件不存在");
				return null;
			}
			InputStream is = null;
			long len = file.length();
			//已读的 记作偏移量
			int offset = 0;
			//本次读的量
			int readNum = 0;
			byte[] b = new byte[(int) len];
			try {
				is = new BufferedInputStream(new FileInputStream(file));
				long startTime = System.currentTimeMillis();
				while (offset < len && (readNum = is.read(b, offset,
						(b.length - offset) > 1024 ? 1024 : (b.length - offset))) >= 0) {
					offset += readNum;
				}
				long endTime = System.currentTimeMillis();
				Logger.getLogger().debug("文件读取时间为：" + (endTime - startTime));
				return b;
			}
			finally {
				if (is != null) {
					is.close();
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}

	}
}
