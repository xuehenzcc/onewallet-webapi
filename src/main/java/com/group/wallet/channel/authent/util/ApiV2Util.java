package com.group.wallet.channel.authent.util;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.group.wallet.channel.authent.common.Toolkit;
import com.group.wallet.channel.authent.service.AuthentXmlImpl;
import com.group.wallet.channel.authent.service.BaseXml;
import com.group.wallet.channel.authent.service.SimpleXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class ApiV2Util {

	/**
	 * 获取MAC明文字符串
	 * ProcCode AccountNo ProcessCode Amount TransDatetime AcqSsn OrderNo TransData Reference RespCode TerminalNo MerchantNo MerchantOrderNo OrderState
	 * @param xml
	 * @return
	 */
	public static String getMACString(SimpleXml xml) {
		BaseXml msg = (BaseXml)xml;
		String str = msg.getProcCode()
				+ getString(msg.getAccountNo())
				+ getString(msg.getProcessCode())
				+ getString(msg.getAmount())
				+ getString(msg.getTransDatetime())
				+ getString(msg.getAcqSsn())
				+ getString(msg.getOrderNo())
				+ getString(msg.getTransData())
				+ getString(msg.getReference())
				+ getString(msg.getRespCode())
				+ getString(msg.getTerminalNo())
				+ getString(msg.getMerchantNo())
				+ getString(msg.getMerchantOrderNo())
				+ getString(msg.getOrderState());
		return str;
	}

	private static String getString(String src) {
		return (isNullOrEmpty(src) ? "" : (" " + src.trim())).toUpperCase();
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * 将XML格式报文转换成JavaBean
	 * @param xml
	 * @return
	 */
	public static SimpleXml toSimpleXml(String xml) {
		SimpleXml object = new BaseXml();
		try{
			Document doc = toDocument(xml);

//			if ("2.1.5".equals(getElementValue("Version", doc))) {
//				object = new AuthentXmlImpl();
//			} else {
//				object = new SimpleXmlImpl();
//			}
			object = new AuthentXmlImpl();
			return toSimpleXml(xml, object);
		}catch (Exception e) {
			Toolkit.writeLog(xml.getClass().getName(), e.getMessage());
		}
		return object;
	}

	public static Document toDocument(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		return doc;
	}

	private static Object getElementValue(String elemName, Document doc) {
		String elemValue = "";
		if (null != doc) {
			Element elem = null;
			elem = (Element) doc.getElementsByTagName(elemName).item(0);
			if (null != elem && null != elem.getFirstChild()) {
				elemValue = elem.getFirstChild().getNodeValue();
			}
		}
		return elemValue;
	}

	/**
	 * 将XML格式报文转换成JavaBean
	 * @param xml
	 * @param object
	 * @return
	 */
	public static SimpleXml toSimpleXml(String xml, SimpleXml object) {
		if(object == null){
			return null;
		}
		try{
			Document doc = toDocument(xml);
			Class[] cs = new Class[]{String.class};
			for (Method m : object.getClass().getMethods()) {
				try {
					if(m.getName().startsWith("set") && m.toString().contains(object.getClass().getPackage().toString().replace("package ", ""))){
						String suffixName = m.getName().replace("set", "");
						m.invoke(object, new Object[]{ApiV2Util.getElementValue(suffixName, doc)});
					}
				} catch (Exception e) {
					Toolkit.writeLog(xml.getClass().getName(), e.getMessage());
				}
			}
		}catch (Exception e) {
			Toolkit.writeLog(xml.getClass().getName(), e.getMessage());
		}
		return object;
	}
}
