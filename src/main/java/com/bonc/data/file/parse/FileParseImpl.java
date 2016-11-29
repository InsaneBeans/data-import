package com.bonc.data.file.parse;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bonc.data.dbconfig.DbField;
import com.bonc.data.file.FileType;
import com.bonc.data.file.FileUtil;

/**
 * 文件解析实现类
 * 
 * @author huh
 *
 */
@Component
public class FileParseImpl extends AbstractFileParse{
	
	/**
	 * 头信息结果
	 */
	private String headerResult;
	/**
	 * 文件类型
	 */
	private FileType fileType;
	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 上传的文件
	 */
	private MultipartFile file;
	
	public FileType getFielType() {
		return fileType;
	}

	public void setFielType(FileType fileType) {
		this.fileType = fileType;
	}
	
	@Override
	public FileType getFileType() {
		FileUtil util = new FileUtil();
		return util.fileTypeJudge(filePath);
	}
	
	@Override
	public String getFileHeader(String filePath) throws IOException{
		headerResult = new ExcelParse().getExcelStructure(filePath);
		return headerResult;
	}

	@Override
	public List<String> getSheetNames(String filePath) {
		FileUtil util = new FileUtil();
		if(util.getFileSuffix(filePath).equals(FileType.CSV)) {
			return null; //CSV文件的获取sheetname的方法
		} else if (util.getFileSuffix(filePath).equals(FileType.EXCEL_2003)) {
			return null; //excel2003的获取sheetnames方法
		} else {
			return null; //excel2007的获取sheetnames方法
		}
	}

	@Override
	public DbField[] getHeaderArray(MultipartFile multifile) {
		this.file = multifile;
		String fileName = file.getOriginalFilename();
		FileUtil util = new FileUtil();
		try{
			if(util.fileTypeJudge(fileName).equals(FileType.CSV)) {
				return new CsvParse().getDbFields(fileName);
			} else if (util.fileTypeJudge(fileName).equals(FileType.EXCEL_2003)){
				
			} else if (util.fileTypeJudge(fileName).equals(FileType.EXCEL_2007)){
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
