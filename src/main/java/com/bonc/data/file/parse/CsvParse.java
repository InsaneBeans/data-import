package com.bonc.data.file.parse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.bonc.data.dboperation.SQLExecutor;
import com.bonc.data.structure.Field;
import com.bonc.data.structure.FieldType;
import com.bonc.data.structure.Table;

/**
 * Csv文件解析类
 * 
 * @author huh
 *
 */
public class CsvParse {

	/**
	 * 获取Csv文件表头信息,用于返回到前台显示并更改字段信息等
	 * 
	 * @param filePath
	 * @return 表结构对象
	 * @throws Exception
	 */
	public Table getHeaderInfo(String filePath) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = null;
		Table table = new Table();
		String tableName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.indexOf("."));
		table.setTableName(tableName);
		table.setFilePath(filePath);
		List<Field> fields = new ArrayList<Field>();
		boolean isFirstLine = true;
		int fieldIndex = 0;
		while ((line = reader.readLine()) != null) {
			if (isFirstLine) {
				String items[] = line.split(",");
				for (String item : items) {
					Field field = new Field();
					field.setName(item);
					field.setFieldType(FieldType.VARCHAR);
					field.setIndexNo(fieldIndex);
					field.setInsert(true);
					fields.add(field);
					fieldIndex++;
				}
			} else {
				reader.close();
				break;
			}
		}
		table.setFields(fields.toArray(new Field[fields.size()]));
		return table;
	}

	/**
	 * 读取Csv文件内容, 生成多条SQL语句，包括创建表和数据插入的语句
	 * 
	 * @param alteredTable
	 *            更改后的表结构对象
	 */
	public void csvInsert(Table alteredTable) throws Exception {
		SQLExecutor sqlExecutor = new SQLExecutor();
		StringBuilder createSql = new StringBuilder("CREATE TABLE ");
		if (sqlExecutor.isTableExist(alteredTable.getTableName())) {
			String newTableName = alteredTable.getTableName() + "_1";
			createSql.append(newTableName);
			alteredTable.setTableName(newTableName);
			// TODO 是否需要判断一下表结构
		} else {
			createSql.append(alteredTable.getTableName());
		}
		StringBuilder insertSql = new StringBuilder("INSERT INTO " + alteredTable.getTableName() + "(");
		createSql.append("( ID INT PRIMARY KEY AUTO_INCREMENT,");
		Field[] alteredFields = alteredTable.getFields();
		List<Integer> indexNos = new ArrayList<Integer>();
		for (Field field : alteredFields) {
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
		sqlExecutor.execute(createSql.toString()); // 执行创建表语句。

		// 开始读取文件
		String filePath = alteredTable.getFilePath();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = null;
		boolean isFirstLine = true;
		List<String> multiInsertSql = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			if (isFirstLine) {
				isFirstLine = false;
				continue;
			}
			StringBuilder everyLine = new StringBuilder("");
			everyLine.append(insertSql);
			String item[] = line.split(","); // CSV格式文件为逗号分隔符文件，这里根据逗号切分
			for (int indexNo : indexNos) {
				String str = item[indexNo];
				everyLine.append("'" + str + "'" + ",");
			}
			everyLine.deleteCharAt(everyLine.length() - 1);
			everyLine.append(")");
			multiInsertSql.add(everyLine.toString());
		}
		reader.close();
		sqlExecutor.multiExecute(multiInsertSql);
	}
}
