package com.bonc.data.file.parse;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.bonc.data.dbconfig.DbField;
import com.bonc.data.dbconfig.FieldType;
import com.bonc.data.util.serialize.JsonSerialIml;

import au.com.bytecode.opencsv.CSVReader;

public class CsvParse {
	
	
	/**
	 * 头信息，Map<字段名，字段类型>
	 */
	private Map<String, FieldType> headerInfo;
	
	/**
	 * 获取表头信息
	 * 
	 * @return 字段数组
	 */
	public DbField[] getDbFields(String filePath) throws Exception {
		FileReader fileReader = new FileReader(new File(filePath));
		CSVReader csvReader = new CSVReader(fileReader);
		String[] strs = csvReader.readNext();
		DbField[] fields = Stream.of(strs).map(str -> {
			DbField field = new DbField();
			field.setName(str);
			field.setType(FieldType.VARCHAR);
			try {
				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return field;
		}).toArray(DbField[] :: new);
		return fields;
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
