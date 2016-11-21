package React.h2memory.file.upload;

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
	public String fileUpload(MultipartFile file);

}
