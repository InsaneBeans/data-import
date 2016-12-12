package com.bonc.data.file.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bonc.data.file.FileType;
import com.bonc.data.file.FileUtil;
import com.bonc.data.poi.SQLExecutor;
import com.bonc.data.structure.AlteredField;
import com.bonc.data.structure.AlteredTable;

public class ExcelInsertWithIndexNo {

	/**
	 * 数据表的创建和数据的导入
	 * 
	 * @param alteredTable
	 * @throws Exception
	 */
	public void tableCreate(AlteredTable alteredTable) throws Exception {
		SQLExecutor sqlExecutor = new SQLExecutor();
		AlteredField[] alteredFields = alteredTable.getFields();
		String filePath = alteredTable.getFilePath();
		String tableName = alteredTable.getTableName();
		FileUtil fileUtil = new FileUtil();
		List<Integer> indexNos = new ArrayList<Integer>();

		// 构建数据表的创建SQL语句
		StringBuilder createSql = new StringBuilder("CREATE TABLE ");
		StringBuilder insertSql = new StringBuilder("INSERT INTO " + tableName + "(");
		List<String> multiInsertSql = new ArrayList<String>();
		createSql.append(tableName + "( ID INT PRIMARY KEY AUTO_INCREMENT,");

		for (AlteredField alteredField : alteredFields) {
			if (alteredField.isInsert()) { // 判断该列是否要插入数据库
				indexNos.add(alteredField.getIndexNo());
				createSql.append(alteredField.getName() + " " + alteredField.getFieldType() + ",");
				insertSql.append(alteredField.getName() + ",");
			} else {
				continue;
			}
		}
		createSql.deleteCharAt(createSql.length() - 1);
		createSql.append(")");
		insertSql.deleteCharAt(insertSql.length() - 1);
		insertSql.append(") VALUES (");

		// 执行创建数据表语句
		System.out.println(sqlExecutor.execute(createSql.toString()));

		// 开始文件读取
		Workbook wb = null;
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);
		// 判断文件类型
		if (fileUtil.fileTypeJudge(filePath) == FileType.EXCEL_2007) {
			wb = new XSSFWorkbook(is);
		} else if (fileUtil.fileTypeJudge(filePath) == FileType.EXCEL_2003) {
			wb = new HSSFWorkbook(is);
		}
		Sheet[] sheets = new Sheet[wb.getNumberOfSheets()];
		for (int j = 0; j < wb.getNumberOfSheets(); j++) {
			sheets[j] = wb.getSheetAt(j);
		}
		for (Sheet sheet : sheets) {
			boolean isFirstRow = true;
			for (Row row : sheet) {
				if (isFirstRow) {
					isFirstRow = false;
					continue;
				}
				StringBuilder everyLine = new StringBuilder("");
				everyLine.append(insertSql);
				for (int indexNo : indexNos) {
					Cell cell = row.getCell(indexNo);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_FORMULA:
					case Cell.CELL_TYPE_BOOLEAN:
					case Cell.CELL_TYPE_STRING:
						everyLine.append("'" + cell.getStringCellValue() + "',");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						everyLine.append("'" + (int) cell.getNumericCellValue() + "',");
						break;
					default:
						everyLine.append("'" + cell.getStringCellValue() + "',");
					}
				}
				everyLine.deleteCharAt(everyLine.length() - 1);
				everyLine.append(")");
				System.out.println(everyLine.toString());
				multiInsertSql.add(everyLine.toString());
			}
			sqlExecutor.multiExecute(multiInsertSql);
		}
	}

	/**
	 * 一个页签对应一个表的情况
	 * 
	 * @param alteredTables
	 * @throws Exception
	 */
	public void getMultiTableContent(List<AlteredTable> alteredTables) throws Exception {
		SQLExecutor sqlExecutor = new SQLExecutor();
		FileUtil fileUtil = new FileUtil();
		Iterator<AlteredTable> iterator = alteredTables.iterator();
		int sheetNum = 0; // 遍历Sheet使用
		while (iterator.hasNext()) {
			AlteredTable alteredTable = iterator.next();
			AlteredField[] alteredFields = alteredTable.getFields();
			String filePath = alteredTable.getFilePath();
			String tableName = alteredTable.getTableName();
			List<Integer> indexNos = new ArrayList<Integer>();

			// 构建数据表的创建SQL语句
			StringBuilder createSql = new StringBuilder("CREATE TABLE ");
			StringBuilder insertSql = new StringBuilder("INSERT INTO " + tableName + "(");
			List<String> multiInsertSql = new ArrayList<String>();
			createSql.append(tableName + "( ID INT PRIMARY KEY AUTO_INCREMENT,");

			for (AlteredField alteredField : alteredFields) {
				if (alteredField.isInsert()) { // 判断该列是否要插入数据库
					indexNos.add(alteredField.getIndexNo());
					createSql.append(alteredField.getName() + " " + alteredField.getFieldType() + ",");
					insertSql.append(alteredField.getName() + ",");
				} else {
					continue;
				}
			}
			createSql.deleteCharAt(createSql.length() - 1);
			createSql.append(")");
			insertSql.deleteCharAt(insertSql.length() - 1);
			insertSql.append(") VALUES (");

			// 执行创建数据表语句
			System.out.println(sqlExecutor.execute(createSql.toString()));
			Workbook wb = null;
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);
			// 判断文件类型
			if (fileUtil.fileTypeJudge(filePath) == FileType.EXCEL_2007) {
				wb = new XSSFWorkbook(is);
			} else if (fileUtil.fileTypeJudge(filePath) == FileType.EXCEL_2003) {
				wb = new HSSFWorkbook(is);
			}
			Sheet sheet = wb.getSheetAt(sheetNum);
			boolean isFirstRow = true;
			for (Row row : sheet) {
				if (isFirstRow) {
					isFirstRow = false;
					continue;
				}
				StringBuilder everyLine = new StringBuilder("");
				everyLine.append(insertSql);
				for (int indexNo : indexNos) {
					Cell cell = row.getCell(indexNo);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_FORMULA:
					case Cell.CELL_TYPE_BOOLEAN:
					case Cell.CELL_TYPE_STRING:
						everyLine.append("'" + cell.getStringCellValue() + "',");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						everyLine.append("'" + (int) cell.getNumericCellValue() + "',");
						break;
					default:
						everyLine.append("'" + cell.getStringCellValue() + "',");
					}
				}
				everyLine.deleteCharAt(everyLine.length() - 1);
				everyLine.append(")");
				System.out.println(everyLine.toString());
				multiInsertSql.add(everyLine.toString());
			}
			sqlExecutor.multiExecute(multiInsertSql);
			sheetNum++;  //下一个页签
		}
	}
}
