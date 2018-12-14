package com.bblee.data.service.util.serialize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.bblee.data.service.util.CommonValue;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON序列化和反序列化实现类
 * 
 * @author huh
 */
@Component
@ConfigurationProperties
public class JsonSerialIml implements Serial {

	private String defaultFilePath = CommonValue.DEFAULT_JSONFILE_SAVE_PATH;
	private OutPutFile outPutJsonFile = new OutPutFile();

	@Override
	public String serializeToString(Object object) {
		return serializeToJsonString(object);
	}

	@Override
	public String serializeToFile(Object object) {
		String randomJsonFileName = outPutJsonFile.generateFileName(); // 随机文件名
		String fullFilePath = defaultFilePath + randomJsonFileName + ".json";
		serializeToJsonFile(object, fullFilePath);
		return fullFilePath;
	}

	@Override
	public String serializeToFile(Object object, String fileName) {
		serializeToJsonFile(object, defaultFilePath + fileName);
		return defaultFilePath + fileName;
	}

	@Override
	public String serializeToFile(Object object, String pathName, String fileName) {
		serializeToJsonFile(object, pathName + fileName);
		return pathName + fileName;
	}

	@Override
	public <T> T deserializeFromFile(String fulleFileName, Class<T> clazz) {
		return deserializeFromJsonFile(fulleFileName, clazz);
	}

	@Override
	public <T> T deserializeFromFile(String pathName, String fileName, Class<T> clazz) {
		return deserializeFromJsonFile(pathName + fileName, clazz);
	}

	@Override
	public <T> T deserializeFromString(String jsonStringContent, Class<T> clazz) {
		return deserializeFromJsonString(jsonStringContent, clazz);
	}

	/**
	 * 序列化到JSON文件
	 * 
	 * @param object
	 *            序列化对象
	 * @param fullFileName
	 *            文件存储完整路径
	 */
	private void serializeToJsonFile(Object object, String fullFileName) {
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			String jsonFileContent = jsonMapper.writeValueAsString(object);
			outPutJsonFile.outputFolderCheck(jsonFileContent, fullFileName);
			outPutJsonFile.outputFile(jsonFileContent, fullFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对象序列化为字符串
	 * 
	 * @param object
	 *            序列化的对象
	 * @return 序列化生成的字符串
	 */
	private String serializeToJsonString(Object object) {
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			List<Object> listObject = new ArrayList<Object>();
			listObject.add(object);
			String jsonString = jsonMapper.writeValueAsString(listObject);
			return jsonString;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反序列化JSON文件
	 * 
	 * @param fullFileName
	 *            完整文件名，包含文件路径
	 * @param clazz
	 *            反序列化类型对象
	 * @return 反序列化结果对象
	 */
	private <T> T deserializeFromJsonFile(String fullFileName, Class<T> clazz) {
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			T jsonEntityObject = (T) jsonMapper.readValue(new File(fullFileName), clazz);
			return jsonEntityObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * JSON字符串反序列化
	 * 
	 * @param jsonStringContent
	 *            JSON字符串内容
	 * @param clazz
	 *            反序列化对象类型
	 */
	public static <T> T deserializeFromJsonString(String jsonStringContent, Class<T> clazz) {
		try {
			ObjectMapper jsonMapper = new ObjectMapper();
			T deserialObject = (T) jsonMapper.readValue(jsonStringContent, clazz);
			return deserialObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}