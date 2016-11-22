package React.h2memory.file;

import React.h2memory.util.CommonValue;

/**
 * 文件工具类
 * 
 * @author huh
 *
 */
public class FileUtil {

	/**
	 * 通过路径获取文件后缀
	 * 
	 * @param filePath
	 * @return
	 */
	public String getFileSuffix(String filePath) {
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
	 * @param filePath
	 * @return
	 */
	public FileType fileTypeJudge(String filePath) {
		if (filePath == null || CommonValue.EMPTY_CONTENT.equals(filePath.trim())) {
			return null;
		}
		switch(getFileSuffix(filePath)) {
		case "xlsx": return FileType.EXCEL_2007; 
		case "xls": return FileType.EXCEL_2003;
		case "csv": return FileType.CSV;
		}
		return null;
	}	
}
