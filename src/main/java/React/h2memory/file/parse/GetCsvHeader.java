package React.h2memory.file.parse;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import React.h2memory.dbconfig.FieldType;
import React.h2memory.util.serialize.JsonSerialIml;
import au.com.bytecode.opencsv.CSVReader;

/**
 * 获取csv文件的表头信息
 * 
 *@author huh
 *
 */
public class GetCsvHeader {
	
	/**
	 * 头信息，Map<字段名，字段类型>
	 */
	private Map<String, FieldType> headerInfo;
	
	/**
	 * 获取表头信息
	 * @return Map对象
	 */
	public Map<String, FieldType> getHeader(String filePath) throws Exception {
		FileReader fileReader = new FileReader(new File(filePath));
		CSVReader csvReader = new CSVReader(fileReader);
		headerInfo = new HashMap<String, FieldType>();
		String[] strs = csvReader.readNext();
		if(strs != null && strs.length > 0) {  
	            for(String str : strs)  
	                if(null != str && !str.equals("")) {
	                	headerInfo.put(str, FieldType.VARCHAR);
	                }
	    }  
		csvReader.close();
		return headerInfo;
	}
	
	/**
	 * 获取json格式的表头信息
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public String getSerializedHeader(String filePath) throws Exception {
		FileReader fileReader = new FileReader(new File(filePath));
		CSVReader csvReader = new CSVReader(fileReader);
		headerInfo = new HashMap<String, FieldType>();
		String[] strs = csvReader.readNext();
		if(strs != null && strs.length > 0) {  
	            for(String str : strs)  
	                if(null != str && !str.equals("")) {
	                	headerInfo.put(str, FieldType.VARCHAR);
	                }
	    }  
		csvReader.close();
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		return jsonSerialIml.serializeToString(headerInfo);
	}
}