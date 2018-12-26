package succez.zfc.entity;

/**
 * 文件类型常量
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月21日
 */
public class FileType {

	public static final String CSS = "Content-Type: text/css";

	public static final String HTML = "Content-Type: text/html;charset=utf-8";

	public static final String JPG = "Content-Type: image/jpeg";

	public static final String JPEG = "Content-Type: image/jpeg";

	public static final String PNG = "Content-Type: image/png";

	public static final String TXT = "Content-Type: text/plain;charset=utf-8";

	public static final String JS = "Content-Type: application/javsscript";

	public static final String JSON = "Content-Type: application/json";

	public static final String XML = "Content-Type: text/xml";

	public static final String DOWNLOAD = "Content-Type: application/x-download";

	public static String getType(String t) {
		switch (t) {
			case "CSS":
				return CSS;
			case "HTML":
				return HTML;
			case "JPG":
				return JPG;
			case "JPEG":
				return JPEG;
			case "PNG":
				return PNG;
			case "TXT":
				return TXT;
			case "JS":
				return JS;
			case "JSON":
				return JSON;
			case "DOWNLOAD":
				return DOWNLOAD;
			case "XML":
				return XML;
			default:
				return null;
		}
	}
}
