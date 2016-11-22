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
	/**
	 * 上传的文件
	 */
	private MultipartFile multiFile;
	/**
	 * 保存的文件对象
	 */
	private File savedFile;
	
	@Override
	public boolean isEmpty(){
		if(multiFile.isEmpty()){
			return true;
		}
		return false;
	}
	
	@Override
	public File initFile() {
		savedFile = new File(fileFullTempPath);
		return savedFile;
	};
	
	@Override
	public String initFileSavePath() {
		fileFullTempPath = System.getProperty("user.dir")+"\\"+"temp"+"\\"+multiFile.getOriginalFilename();
		return fileFullTempPath;
	}
	
	@Override
	public String initFileSavePath(String path) {
		fileFullTempPath = path+multiFile.getOriginalFilename();
		return fileFullTempPath;
	}
	
	@Override
	public String fileUpload(MultipartFile file){
		multiFile = file;
		File newFile = new File(this.initFileSavePath());
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

	@Override
	public String deleteExistFile() {
		File file = new File(this.initFileSavePath());
		if(file.exists()) {
			file.delete();
		}
		return null;
	}

	@Override
	public boolean isExist() {
		if(savedFile.exists()) {
			return true;
		}
		return false;
	}
}