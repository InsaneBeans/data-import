package React.h2memory.file.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传类
 * 
 * @author huh
 *
 */
@Component
public class FileUploadImpl implements FileUpload{
	
	/**
	 * 文件在服务端存储的目录
	 */
	private String fileFullTempPath;
	
	@Override
	public String fileUpload(MultipartFile file){
		fileFullTempPath =  System.getProperty("user.dir")+"\\"+"temp"+"\\"+file.getOriginalFilename();
		File newFile = new File(fileFullTempPath);
		if (!file.isEmpty()) {
			try {
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(newFile));
				out.write(file.getBytes());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				return "上传失败！" + e.getMessage();
			}
			return fileFullTempPath;
		} else {
			return "文件为空，无法上传！";
		}
	}
}