package com.bonc.data.file.parse;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bonc.data.file.FileType;
import com.bonc.data.structure.Field;
import com.bonc.data.structure.Table;

/**
 * 文件解析接口
 * 
 * @author huh
 *
 */
@Component
public interface FileParse {
	
	/**
	 * 获取复杂文件表头信息
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	List<Table> getMultiFileStructure(String filePath) throws IOException;
	/**
	 * 获取简单文件表头信息
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	Table getSimpleFileStructure(String filePath) throws IOException;
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
	FileType getFileType(String filePath);
	/**
	 * 获取文件的表头数组形式
	 * @param filePath
	 * @return
	 */
	Field[] getFileHeaderArray(MultipartFile file);
}
