package com.bonc.data.file.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import com.bonc.data.structure.Field;
import com.bonc.data.structure.FieldType;
import com.bonc.data.structure.Table;
import com.bonc.data.util.CellType;
import com.bonc.data.util.CommonValue;

/**
 * Excel解析工具类
 * 
 * @author huh
 * 
 */
public class ExcelParse {

	/**
	 * 获取多结构EXCEL表格的表头结构，即每个页签的表结构是不一样的，对应类型Type=2
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 表对象列表
	 * @throws IOException
	 */
	public List<Table> getMultiExcelStructure(String filePath) throws IOException {
		Workbook workbook = null;
		FileUtil fileUtil = new FileUtil();
		InputStream is = new FileInputStream(filePath);
		List<Table> tables = new ArrayList<Table>();
		if (fileUtil.getFileSuffix(filePath).equals(CommonValue.EXCEL_2003_SUFFIX)) {
			workbook = new HSSFWorkbook(is);
		} else {
			workbook = new XSSFWorkbook(is);
		}
		// 循环遍历页签
		sheetReader: for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
			Table table = new Table();
			Sheet sheet = workbook.getSheetAt(sheetNum);
			table.setName(sheet.getSheetName());
			if (sheet.getLastRowNum() <= 0) {
				continue sheetReader;
			}
			Row headRow = sheet.getRow(0);
			int firstColumn = headRow.getFirstCellNum();
			int lastColumn = headRow.getLastCellNum();
			List<Field> fields = new ArrayList<Field>();
			for (int i = firstColumn; i < lastColumn; i++) {
				Field field = new Field();
				field.setName(CellType.getStringVal(headRow.getCell(i)));
				field.setFieldType(FieldType.VARCHAR);
				fields.add(field);
			}
			table.setFields(fields.toArray(new Field[fields.size()]));
			table.setFilePath(filePath);
			tables.add(table);
		}
		return tables;
	}

	/**
	 * 获取单结构EXCEL表格的表头结构，所有页签对应相同的表，对应文件Type=1
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 表结构对象
	 * @throws IOException
	 */
	public Table getSimpleExcelStructure(String filePath) throws IOException {
		Workbook workbook = null;
		FileUtil fileUtil = new FileUtil();
		InputStream is = new FileInputStream(filePath);
		Table table = new Table();
		if (fileUtil.getFileSuffix(filePath).equals(CommonValue.EXCEL_2003_SUFFIX)) {
			workbook = new HSSFWorkbook(is);
		} else {
			workbook = new XSSFWorkbook(is);
		}
		Sheet sheet = workbook.getSheetAt(0);
		table.setName(sheet.getSheetName());
		Row headRow = sheet.getRow(0);
		List<Field> fields = new ArrayList<Field>();
		int firstColumn = headRow.getFirstCellNum();
		int lastColumn = headRow.getLastCellNum();
		for (int i = firstColumn; i < lastColumn; i++) {
			String cellValue = CellType.getStringVal(headRow.getCell(i));
			Field field = new Field();
			field.setIndexNo(i);
			field.setName(cellValue);
			field.setFieldType(FieldType.VARCHAR);
			fields.add(field);
		}
		table.setFields(fields.toArray(new Field[fields.size()]));
		table.setFilePath(filePath);
		return table;
	}

	/**
	 * 获取Excel表格内容并读取存储
	 * 
	 * @param alteredTable
	 * @throws Exception
	 */
	public void getSimpleExcelContent(AlteredTable alteredTable) throws Exception {
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
}
