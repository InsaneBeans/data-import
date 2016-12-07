package com.bonc.data.file.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.bonc.data.poi.SQLExecutor;
import com.bonc.data.structure.AlteredField;
import com.bonc.data.structure.AlteredTable;

/**
 * Excel导入的数据表创建和数据插入工具类
 * 
 * @author huh
 *
 */
@Component
public class ExcelInsert {

	public void tableCreate(AlteredTable alteredTable) throws Exception {

		SQLExecutor sqlExecutor = new SQLExecutor();
		// 得到更改后的列对象数组
		AlteredField[] alteredFields = alteredTable.getFields();
		// 获得对应文件的位置
		String filePath = alteredTable.getFilePath();
		// 获得更改后提交的表格名称，用于数据表的创建
		String tableName = alteredTable.getTableName();

		// 构建数据表的创建SQL语句
		StringBuilder createSql = new StringBuilder("CREATE TABLE ");
		createSql.append(tableName + "( ID INT PRIMARY KEY,");

		// 列名和列类型
		for (AlteredField alteredField : alteredFields) {
			createSql.append(alteredField.getName() + " " + alteredField.getFieldType() + ",");
		}
		createSql.deleteCharAt(createSql.length() - 1);
		createSql.append(")");
		// 创建表格完毕。以下是插入数据的操作。

		// 执行创建表
		System.out.println(sqlExecutor.execute(createSql.toString()));
		ResultSet rs = sqlExecutor.executeQuery("SELECT * FROM " + tableName);

		// 创建数据库字段和文件中字段的映射
		List<Map<String, String>> listNameMaps = new ArrayList<Map<String, String>>();
		List<String> dbFieldNames = new ArrayList<String>(); // 数据库字段名列表

		for (AlteredField alteredField : alteredFields) {
			Map<String, String> maps = new HashMap<String, String>();
			String key1 = alteredField.getName(); // 数据库字段名
			String key2 = alteredField.getOriginalName(); // 文件中的字段名
			maps.put(key1, key2);
			dbFieldNames.add(key1);
			listNameMaps.add(maps);
		}

		String[] dbFields = dbFieldNames.toArray(new String[dbFieldNames.size()]);
		StringBuilder insertSql = new StringBuilder("INSERT INTO ");
		insertSql.append(tableName + "(");
		for (String field : dbFields) {
			insertSql.append(field + ",");
		}
		Iterator<Map<String, String>> iterator = listNameMaps.iterator();
		while (iterator.hasNext()) {
			Map<String, String> nameMap = iterator.next();
		}

		// 文件读取
		Workbook wb;
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);
		wb = new HSSFWorkbook(is);
		Sheet[] sheets = new Sheet[wb.getNumberOfSheets()];
		for (int j = 0; j < wb.getNumberOfSheets(); j++) {
			sheets[j] = wb.getSheetAt(j);
		}
		// 读取第一个页签
		Sheet sheet = sheets[0];
		Row row0 = sheet.getRow(0); // 第一行（如果带有选择的行数，这里的0就代表了表头所在的行数）
		Map<String, Integer> row0Column = new HashMap<String, Integer>();
		
		for (int i = 0; i <= dbFieldNames.size(); i++) {
			String presentValue = dbFieldNames.get(i);
			for (Cell cell : row0) {
				if (cell.getStringCellValue() == presentValue) {
					row0Column.put(presentValue, cell.getColumnIndex());
				}
			}
		}

		insertSql.deleteCharAt(insertSql.length() - 2);
		insertSql.append(") values (");

	}
}
