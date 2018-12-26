package succez.zfc.fileSystem.pojo;

/**
 * 文件系统 实体类
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年12月1日
 */
public class MyFile {

	private String fileName;//文件名

	private long size;//文件大小

	private int isDir;//是否是文件夹: 0为文件 1为文件夹

	private String filePath;//文件真实路径

	public MyFile(String fileName, long size, int isDir, String filePath) {
		this.fileName = fileName;
		this.size = size;
		this.isDir = isDir;
		this.filePath = filePath;
	}

	public MyFile() {

	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getIsDir() {
		return isDir;
	}

	public void setIsDir(int isDir) {
		this.isDir = isDir;
	}

}
