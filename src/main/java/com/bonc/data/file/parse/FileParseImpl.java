package com.bonc.data.file.parse;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bonc.data.file.FileType;
import com.bonc.data.file.FileUtil;
import com.bonc.data.structure.Field;
import com.bonc.data.structure.Table;

/**
 * 文件解析实现类
 * 
 * @author huh
 *
 */
@Component
public class FileParseImpl extends AbstractFileParse {

	@Override
	public FileType getFileType(String filePath) {
		FileUtil util = new FileUtil();
		return util.fileTypeJudge(filePath);
	}
	
	@Override
	public List<Table> getMultiFileStructure(String filePath) throws IOException {
		List<Table> tables = new ExcelParse().getMultiExcelStructure(filePath);
		return tables;
	}

	@Override
	public Table getSimpleFileStructure(String filePath) throws IOException {
		Table table = new ExcelParse().getSimpleExcelStructure(filePath);
		return table;
	}

	@Override
	public List<String> getSheetNames(String filePath) {
		FileUtil util = new FileUtil();
		if (util.getFileSuffix(filePath).equals(FileType.CSV)) {
			return null; // CSV文件的获取sheetname的方法
		} else if (util.getFileSuffix(filePath).equals(FileType.EXCEL_2003)) {
			return null; // excel2003的获取sheetnames方法
		} else {
			return null; // excel2007的获取sheetnames方法
		}
	}

	@Override
	public Field[] getFileHeaderArray(MultipartFile multifile) {
		String fileName = multifile.getOriginalFilename();
		FileUtil util = new FileUtil();
		try {
			if (util.fileTypeJudge(fileName).equals(FileType.CSV)) {
				return null;
			} else if (util.fileTypeJudge(fileName).equals(FileType.EXCEL_2003)) {
				// 03版本的解析
			} else if (util.fileTypeJudge(fileName).equals(FileType.EXCEL_2007)) {
				// 07版本的解析
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
