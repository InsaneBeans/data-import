package com.bonc.data.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {
	public static void main(String[] args) throws Exception {

		String pathname = "C:\\Users\\Administrator\\Desktop\\excelTest\\users.xlsx";
		File file = new File(pathname);
		InputStream in = new FileInputStream(file);
		// 得到整个excel对象
		XSSFWorkbook excel = new XSSFWorkbook(in);
		// 获取整个excel有多少个sheet
		int sheetnum = excel.getNumberOfSheets();
		// 遍历第一个sheet
		Map<String, String> colMap = new HashMap<String, String>();
		for (int i = 0; i < sheetnum; i++) {
			XSSFSheet sheet = excel.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			int mergedRegions = sheet.getNumMergedRegions();
			XSSFRow row2 = sheet.getRow(0);
			Map<Integer, String> category = new HashMap<Integer, String>();
			for (int j = 0; j < mergedRegions; j++) {
				CellRangeAddress rangeAddress = sheet.getMergedRegion(j);
				int firstRow = rangeAddress.getFirstColumn();
				int lastRow = rangeAddress.getLastColumn();
				category.put(rangeAddress.getFirstColumn(),
						rangeAddress.getLastColumn() + "-" + row2.getCell(firstRow).toString());
			}
			// 便利每一行
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				System.out.println();
				XSSFRow row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				short lastCellNum = row.getLastCellNum();
				String cate = "";
				Integer maxIndex = 0;
				for (int col = row.getFirstCellNum(); col < lastCellNum; col++) {
					XSSFCell cell = row.getCell(col);
					if (cell == null) {
						continue;
					}
					if ("".equals(cell.toString())) {
						continue;
					}
					int columnIndex = cell.getColumnIndex();
					String string = category.get(columnIndex);
					if (string != null && !string.equals("")) {
						String[] split = string.split("-");
						cate = split[1];
						maxIndex = Integer.parseInt(split[0]);
						System.out.println(cate + "<-->" + cell.toString());
					} else {
						// 如果当前便利的列编号小于等于合并单元格的结束,说明分类还是上面的分类名称
						if (columnIndex <= maxIndex) {
							System.out.println(cate + "<-->" + cell.toString());
						} else {
							System.out.println("分类未知" + "<-->" + cell.toString());
						}
					}
				}
			}
		}
	}
}
