package com.bblee.data.service.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.bblee.data.service.util.CommonValue;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 文件工具类
 * 
 * @author huh
 *
 */
public class FileUtil {
	
	private static Logger logger = null;

	/**
	 * 通过路径获取文件后缀
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileSuffix(String filePath) {
		if (filePath == null || CommonValue.EMPTY_CONTENT.equals(filePath.trim())) {
			return CommonValue.EMPTY_CONTENT;
		}
		if (filePath.contains(CommonValue.POINT)) {
			return filePath.substring(filePath.lastIndexOf(CommonValue.POINT) + 1);
		}
		return CommonValue.EMPTY_CONTENT;
	}

	/**
	 * 文件类型判断
	 * 
	 * @param filePath
	 * @return
	 */
	public static FileType fileTypeJudge(String filePath) {
		if (filePath == null || CommonValue.EMPTY_CONTENT.equals(filePath.trim())) {
			return null;
		}
		switch (getFileSuffix(filePath)) {
		case "xlsx":
			return FileType.EXCEL_2007;
		case "xls":
			return FileType.EXCEL_2003;
		case "csv":
			return FileType.CSV;
		}
		return null;
	}
	
	/**
	 * 获取EXCEL工作簿
	 * 
	 * @param filePath
	 * @return
	 */
	public static Workbook getExcelWorkbook(String filePath) {
		Workbook wb = null;
		try{
			InputStream is = new FileInputStream(new File(filePath));
			switch(fileTypeJudge(filePath)){
			case EXCEL_2003:
				wb = new HSSFWorkbook(is);
				break;
			case EXCEL_2007:
				wb = new XSSFWorkbook(is);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return wb;
	}
}
