package com.bonc.data.file.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.bonc.data.poi.SQLExecutor;
import com.bonc.data.structure.AlteredField;
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

//	@Autowired
//	private SQLExecutor sqlExecutor;

	/**
	 * firstTime，获取字段信息，实现维度和度量的读取
	 * 
	 * @return 字段数组
	 */
	public Field[] getFields(String filePath) throws Exception {
		FileReader fileReader = new FileReader(new File(filePath));
		CSVReader csvReader = new CSVReader(fileReader);
		String[] strs = csvReader.readNext(); // readNext表示读取下一行
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
			int i = 0;
			for (String str : strs) {
				if (null != str && !str.equals("")) {
					Field field = new Field();
					field.setName(str);
					field.setIndexNo(i);
					field.setInsert(true);
					field.setFieldType(FieldType.VARCHAR);
					fields.add(field);
					i++;
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
	 * 读取Csv文件内容, 生成SQL语句
	 * 
	 * @param alteredTable
	 *            更改后的表结构对象
	 */
	public void getCsvInsert(AlteredTable alteredTable) throws Exception {
		// 首先生成一个创建数据表的语句
		SQLExecutor sqlExecutor = new SQLExecutor();
		StringBuilder createSql = new StringBuilder("CREATE TABLE " + alteredTable.getTableName());
		StringBuilder insertSql = new StringBuilder("INSERT INTO " + alteredTable.getTableName() + "(");
		createSql.append("( ID INT PRIMARY KEY AUTO_INCREMENT,");
		AlteredField[] alteredFields = alteredTable.getFields();
		List<Integer> indexNos = new ArrayList<Integer>();
		for (AlteredField field : alteredFields) {
			if (field.isInsert()) {
				createSql.append(field.getName() + " " + field.getFieldType() + ",");
				insertSql.append(field.getName() + ",");
				indexNos.add(field.getIndexNo());
			} else {
				continue;
			}
		}
		createSql.deleteCharAt(createSql.length() - 1);
		createSql.append(")");
		insertSql.deleteCharAt(insertSql.length() - 1);
		insertSql.append(") VALUES (");
		sqlExecutor.execute(createSql.toString()); // 执行创建表的语句。

		// 开始读取文件
		String filePath = alteredTable.getFilePath();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = null;
		boolean isFirstLine = true;
		List<String> multiInsertSql = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			if(isFirstLine){
				isFirstLine = false;
				continue;
			}
			StringBuilder everyLine = new StringBuilder("");
			everyLine.append(insertSql);
			String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
			for (int indexNo : indexNos) {
				String str = item[indexNo];
				everyLine.append("'" + str + "'" + ",");
			}
			everyLine.deleteCharAt(everyLine.length()-1);
			everyLine.append(")");
			multiInsertSql.add(everyLine.toString());
		}
		reader.close();
		sqlExecutor.multiExecute(multiInsertSql);
	}

	public List<String> getListStrings(String buff) {
		List<String> lists = new ArrayList<String>();
		lists.add(buff.substring(0, buff.indexOf(",")));
		return lists;
	}
}
