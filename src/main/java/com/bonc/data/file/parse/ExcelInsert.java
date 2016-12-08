package com.bonc.data.file.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bonc.data.file.FileType;
import com.bonc.data.file.FileUtil;
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

	@Autowired
	private SQLExecutor sqlExecutor;

	public void tableCreate(AlteredTable alteredTable) throws Exception {

		AlteredField[] alteredFields = alteredTable.getFields();
		String filePath = alteredTable.getFilePath();
		String tableName = alteredTable.getTableName();
		FileUtil fileUtil = new FileUtil();
		
		// 构建数据表的创建SQL语句
		StringBuilder createSql = new StringBuilder("CREATE TABLE ");
		createSql.append(tableName + "( ID INT PRIMARY KEY AUTO_INCREMENT,");

		// 添加列名和列类型
		for (AlteredField alteredField : alteredFields) {
			//如果要插入改行语句，则把SQL语句加上该行的创建语句中
			if (alteredField.isInsert()) {  
				createSql.append(alteredField.getName() + " " + alteredField.getFieldType() + ",");
			} else {
				continue;
			}
		}
		createSql.deleteCharAt(createSql.length() - 1);
		createSql.append(")");
		
		System.out.println(sqlExecutor.execute(createSql.toString()));

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
		// 以上生成的语句是："insert into tableName (field1,field2,field3...) values ("
		// 开始填充字段的值，填充字段根据字段的顺序和字段的映射值
		// Iterator<Map<String, String>> iterator = listNameMaps.iterator();
		// while (iterator.hasNext()) {
		// Map<String, String> nameMap = iterator.next();
		// }

		// 文件读取
		Workbook wb = null;
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);
		// 判断文件类型
		if (fileUtil.fileTypeJudge(filePath) == FileType.EXCEL_2007) {
			wb = new XSSFWorkbook(is);
		} else if (fileUtil.fileTypeJudge(filePath) == FileType.EXCEL_2003) {
			wb = new HSSFWorkbook(is);
		}
		// 这里的
		Sheet[] sheets = new Sheet[wb.getNumberOfSheets()];
		for (int j = 0; j < wb.getNumberOfSheets(); j++) {
			sheets[j] = wb.getSheetAt(j);
		}
		for (Sheet sheet : sheets) {
			boolean isFirstRow = true;
			for (Row row : sheet) {
				//
				// 此处添加表头在第几行的选择
				//
				if (isFirstRow) { // 判断是否在第一行
					isFirstRow = false;
					continue;
				}
				StringBuilder insertSql = new StringBuilder("INSERT INTO ");
				insertSql.append(tableName + "(");
				for (String field : dbFields) {
					insertSql.append(field + ",");
				}
				insertSql.deleteCharAt(insertSql.length() - 1); // 删除最后一个逗号
				insertSql.append(") values (");

				for (Cell cell : row) {
					if (cell == null) {
						continue;
					}
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_FORMULA:
					case Cell.CELL_TYPE_STRING:
						insertSql.append("'" + cell.getStringCellValue() + "',");
						break;
					case Cell.CELL_TYPE_NUMERIC:
					case Cell.CELL_TYPE_BOOLEAN:
						insertSql.append("'" + (int) cell.getNumericCellValue() + "',");
						break;
					default:
						insertSql.append("'" + cell.getStringCellValue() + "',");
					}
				}
				insertSql.deleteCharAt(insertSql.length() - 1);
				insertSql.append(");");
				sqlExecutor.execute(insertSql.toString());
			}
		}
	}
}
