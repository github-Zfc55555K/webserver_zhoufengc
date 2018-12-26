package succez.zfc.util.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import succez.zfc.util.logger.Logger;

/**
 * 解析所有xml
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author zhoufengc
 * @createdate 2018年11月22日
 */
public class ParseXml {
	/**
	 * 解析conf.xml(包含服务器设置参数)
	 * @return map映射
	 */
	public static Map<String, String> getConfInfo() {
		Logger.getLogger().debug("开始解析conf.xml...");
		Map<String, String> map = new HashMap<>();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(ParseXml.class.getResourceAsStream("/conf.xml"));
			Element root = document.getRootElement();
			map.put("port", root.element("port").getText());
			map.put("poolSize", root.element("poolSize").getText());
			map.put("path", root.element("path").getText());
			map.put("address", root.element("address").getText());
			map.put("webRoot", root.element("webRoot").getText());
		}
		catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger().debug("conf.xml解析异常...");
		}
		return map;
	}

	/**
	 * 解析web.xml 包括 action映射、 servlet
	 * @return
	 */
	public static Map<String, String> getWebInfo() {
		Logger.getLogger().debug("开始解析web.xml...");
		Map<String, String> map = new HashMap<>();
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(ParseXml.class.getResourceAsStream("/web.xml"));
			Element root = document.getRootElement();
			List<Element> list = root.elements();
			for (Element e : list) {
				if (e.getName().equals("servlet")) {
					String name = e.elementText("name");
					String _class = e.elementText("class");
					map.put(name, _class);
					Logger.getLogger().debug("{}的servlet映射为{}", _class, name);
					continue;
				}
				map.put(e.getName(), e.getText());
				if (e.getName().equals("display")) {
					Logger.getLogger().debug("默认路径为:{}", e.getText());
				}
				else if (e.getName().equals("action")) {
					Logger.getLogger().debug("servlet后缀为:{}", e.getText());
				}
			}
			Logger.getLogger().debug("web.xml解析完成！");
		}
		catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger().debug("web.xml解析异常...");
		}
		return map;
	}
}
