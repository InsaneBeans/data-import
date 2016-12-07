package com.bonc.data.file.upload;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 * 
 * @author huh
 *
 */
@Component
public interface FileUpload {
	/**
	 * 多个文件上传
	 * @param file
	 * @return 返回完整的文件存储路径
	 */
	String fileUpload(MultipartFile multiFile);
	/**
	 * 删除已存在的文件
	 * @return
	 */
	String deleteExistFile(String fileName);
	/**
	 * 文件是否为空
	 */
	boolean isEmpty(MultipartFile multiFile);
	/**
	 * 初始化文件路径
	 */
	String initFileSavePath();
}
