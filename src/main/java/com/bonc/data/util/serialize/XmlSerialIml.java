package com.bonc.data.util.serialize;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.bonc.data.util.CommonValue;
import com.fasterxml.jackson.xml.XmlMapper;

/**
 * XML序列化和反序列化实现类
 * 
 * @author huh
 */
@Component
@ConfigurationProperties
public class XmlSerialIml implements Serial {

	private String defaultFilePath = CommonValue.DEFAULT_XMLFILE_SAVE_PATH;
	private OutPutFile outPutXmlFile = new OutPutFile();

	@Override
	public String serializeToString(Object object) {
		return serializeToXmlString(object);
	}

	@Override
	public String serializeToFile(Object object) {
		String randomXmlFileName = outPutXmlFile.generateFileName(); // 随机文件名
		String fullFilePath = defaultFilePath + randomXmlFileName + ".xml";
		serializeToXmlFile(object, fullFilePath);
		return fullFilePath;
	}

	@Override
	public String serializeToFile(Object object, String fileName) {
		serializeToXmlFile(object, defaultFilePath + fileName);
		return defaultFilePath + fileName;
	}

	@Override
	public String serializeToFile(Object object, String pathName, String fileName) {
		serializeToXmlFile(object, pathName + fileName);
		return pathName + fileName;
	}

	@Override
	public <T> T deserializeFromString(String stringContent, Class<T> clazz) {
		return deserializeXmlString(stringContent, clazz);
	}

	@Override
	public <T> T deserializeFromFile(String fulleFileName, Class<T> clazz) {
		return deserializeXmlFile(fulleFileName, clazz);
	}

	@Override
	public <T> T deserializeFromFile(String pathName, String fileName, Class<T> clazz) {
		return deserializeXmlFile(pathName + fileName, clazz);
	}

	/**
	 * 序列化为XML
	 * 
	 * @param object
	 *            序列化对象
	 * @param fullFileName
	 *            完整文件名（包含文件路径）
	 */
	private void serializeToXmlFile(Object object, String fullFileName) {
		try {
			XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.writeValue(new StringWriter(), object);
			outPutXmlFile.outputFolderCheck(xmlStringWriter.toString(), fullFileName);
			outPutXmlFile.outputFile(xmlStringWriter.toString(), fullFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 序列化为XML字符串
	 * 
	 * @param object
	 *            序列化对象
	 * @return 字符串结果
	 */
	private String serializeToXmlString(Object object) {
		try {
			StringWriter xmlStringWriter = new StringWriter();
			XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.writeValue(xmlStringWriter, object);
			String xmlStringResult = xmlStringWriter.toString();
			return xmlStringResult;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反序列化XML文件
	 * 
	 * @param fullFileName
	 *            完整文件名(包含了文件路径和文件名)
	 * @param clazz
	 *            反序列化结果类型对象
	 */
	private <T> T deserializeXmlFile(String fullFileName, Class<T> clazz) {
		try {
			XmlMapper xmlMapper = new XmlMapper();
			T xmlEntityObject = (T) xmlMapper.readValue(new File(fullFileName), clazz);
			return xmlEntityObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反序列化XML字符串为JAVA对象
	 * 
	 * @param xmlStringContent
	 *            字符串内容
	 * @param clazz
	 *            反序列化结果对象
	 * @return 反序列化结果对象
	 */
	private static <T> T deserializeXmlString(String xmlStringContent, Class<T> clazz) {
		try {
			XmlMapper xmlMapper = new XmlMapper();
			T xmlEntityObject = (T) xmlMapper.readValue(xmlStringContent, clazz);
			return xmlEntityObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}