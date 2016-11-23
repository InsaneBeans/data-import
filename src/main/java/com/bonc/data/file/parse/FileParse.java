package com.bonc.data.file.parse;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bonc.data.dbconfig.DbField;
import com.bonc.data.file.FileType;

/**
 * 文件解析接口
 * 
 * @author huh
 *
 */
@Component
public interface FileParse {
	
	/**
	 * 获取文件表头信息
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	String getFileHeader(String filePath) throws IOException;
	/**
	 * 获取页签名
	 * @param filePath
	 * @return
	 */
	List<String> getSheetNames(String filePath);
	/**
	 * 获取文件名
	 * @param filePath
	 * @return
	 */
	String getFileName(String filePath);
	/**
	 * 获取文件类型
	 * @return
	 */
	FileType getFileType();
	/**
	 * 获取文件的表头数组形式
	 * @param filePath
	 * @return
	 */
	DbField[] getHeaderArray(MultipartFile file);
}
