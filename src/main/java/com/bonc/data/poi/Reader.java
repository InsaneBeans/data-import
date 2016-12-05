package com.bonc.data.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Reader {

	@Autowired
	private SQLExecutor sqlExecutor;

	public String excelReader(String filePath) {
		HSSFWorkbook wb = null; // 工作簿，Schema
		HSSFSheet[] sheets = null; // 页签，Table
		try {
			POIFSFileSystem exlf = new POIFSFileSystem(new FileInputStream(filePath));
			wb = new HSSFWorkbook(exlf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "文件不存在";
		} catch (IOException e) {
			e.printStackTrace();
			return "读取文件错误";
		}
		int n = wb.getNumberOfSheets(); // 页签数量
		sheets = new HSSFSheet[n];
		for (int j = 0; j < n; j++) {
			sheets[j] = wb.getSheetAt(j);
		}
		try {
			// 遍历每个页签
			for (HSSFSheet sheet : sheets) {
				HSSFRow row0 = sheet.getRow(0);
				// 根据前两行内容建表
				HSSFCell cel, refcell;
				HSSFRow row1 = sheet.getRow(1);
				String sheetName = sheet.getSheetName(); // 获取页签名

				// 生成建表语句
				String createSql = "create table " + sheetName + " (";
				for (int i = row0.getFirstCellNum(); i < row0.getLastCellNum(); i++) {
					cel = row0.getCell(i);
					refcell = row1.getCell(i);
					if (refcell == null) {
						createSql = createSql.concat(cel.getStringCellValue() + " VARCHAR(64)");
					} else {
						switch (refcell.getCellType()) {
						case Cell.CELL_TYPE_FORMULA:
						case Cell.CELL_TYPE_STRING:
							createSql = createSql.concat(cel.getStringCellValue() + " VARCHAR(64)");
							break;
						case Cell.CELL_TYPE_NUMERIC:
							createSql = createSql.concat(cel.getStringCellValue() + " INT");
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							createSql = createSql.concat(cel.getStringCellValue() + " TINYINT");
							break;
						default:
							createSql = createSql.concat(cel.getStringCellValue() + " VARCHAR(64)");
						}
					}
					if (i < row0.getLastCellNum() - 1) {
						if (i == 0) {
							createSql = createSql.concat(" NOT NULL PRIMARY KEY");
						}
						createSql = createSql.concat(", ");
					} else {
						createSql = createSql.concat(");");
					}
				}
				sqlExecutor.execute(createSql);
				ResultSet rs = sqlExecutor.executeQuery("select * from " + sheetName);
				// int minColIdx = row0.getFirstCellNum();
				// int maxColIdx = row0.getLastCellNum();
				int count = 0;
				// 是否在第一行
				boolean infirstrow = true;
				for (Row row : sheet) {
					if (infirstrow) {
						infirstrow = false;
						continue;
					}
					rs.moveToInsertRow();
					System.out.println("insert " + count++);
					for (Cell cell : row) {
						if (cell == null) {
							continue;
						}
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_FORMULA:
						case Cell.CELL_TYPE_STRING:
							rs.updateString(cell.getColumnIndex() + 1, cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							rs.updateInt(cell.getColumnIndex() + 1, (int) cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							rs.updateShort(cell.getColumnIndex() + 1, (short) cell.getNumericCellValue());
							break;
						default:
							rs.updateString(cell.getColumnIndex() + 1, cell.getStringCellValue());
						}
					}
					rs.insertRow();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String querySelect(String sql) {
		String resultString = null;
		try {
			ResultSet resultSet = sqlExecutor.executeQuery(sql);
			resultString = new ObjectMapper().writeValueAsString(resultSet.getObject(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
}
