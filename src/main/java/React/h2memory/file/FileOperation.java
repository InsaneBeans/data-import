package React.h2memory.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作接口
 * 
 * @author huh
 *
 */
public interface FileOperation {
	/**
	 * 多个文件上传
	 * @param file
	 * @return
	 */
	public String fileUpload(MultipartFile file);
	/**
	 * 根据文件路径上传文件
	 * @param filePath
	 * @return
	 */
	public String fileUpload(String filePath);
}
