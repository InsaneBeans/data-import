package com.bonc.data.file.parse;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.bonc.data.structure.AlteredTable;
import com.bonc.data.structure.Field;
import com.bonc.data.structure.FieldType;
import com.bonc.data.structure.Table;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Csv文件解析类
 * 
 * @author huh
 *
 */
public class CsvParse {

	/**
	 * firstTime，获取字段信息，实现维度和度量的读取
	 * 
	 * @return 字段数组
	 */
	public Field[] getFields(String filePath) throws Exception {
		FileReader fileReader = new FileReader(new File(filePath));
		CSVReader csvReader = new CSVReader(fileReader);
		String[] strs = csvReader.readNext();
		Field[] fields = Stream.of(strs).map(str -> {
			Field field = new Field();
			field.setName(str);
			field.setFieldType(FieldType.VARCHAR);
			try {
				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return field;
		}).toArray(Field[]::new);
		return fields;
	}

	/**
	 * firstTime，获取Csv文件表头信息,用于返回到前台显示并更改字段信息等
	 * 
	 * @param filePath
	 * @return 表结构对象
	 * @throws Exception
	 */
	public Table getHeaderInfo(String filePath) throws Exception {
		FileReader fileReader = new FileReader(new File(filePath));
		CSVReader csvReader = new CSVReader(fileReader);
		Table table = new Table();
		String tableName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.indexOf("."));
		table.setName(tableName);
		List<Field> fields = new ArrayList<Field>();
		String[] strs = csvReader.readNext();
		if (strs != null && strs.length > 0) {
			for (String str : strs) {
				if (null != str && !str.equals("")) {
					Field field = new Field();
					field.setName(str);
					field.setFieldType(FieldType.VARCHAR);
					fields.add(field);
				}
			}
		}
		// 关闭Csv解读器
		csvReader.close();
		Field[] fieldss = fields.toArray(new Field[fields.size()]);
		table.setFields(fieldss);
		return table;
	}

	/**
	 * 读取Csv文件内容
	 * 
	 * @param alteredTable
	 *            更改后的表结构对象
	 */
	public void readCsvContent(AlteredTable alteredTable) {

	}
}
