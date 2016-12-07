package com.bonc.data.file.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传实现类
 * 
 * @author huh
 *
 */
@Component
public class FileUploadImpl implements FileUpload {

	/**
	 * 文件在服务端存储的目录
	 */
	private String fileCachePath;

	@Override
	public boolean isEmpty(MultipartFile multiFile) {
		if (multiFile.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public String initFileSavePath() {
		fileCachePath = System.getProperty("user.dir") + "\\" + "temp" + "\\";
		return fileCachePath;
	}
	
	@Override
	public String fileUpload(MultipartFile file) {
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
			return fileCachePath + file.getOriginalFilename();
		} else {
			return "文件为空，无法上传！";
		}
	}

	@Override
	public String deleteExistFile(String fileName) {
		File file = new File(this.initFileSavePath() + fileName);
		if (file.exists()) {
			file.delete();
		}
		return null;
	}
}