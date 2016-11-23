package com.bonc.data.file.parse;

/**
 * 文件解析抽象类
 * 
 *@author huh
 *
 */
public abstract class AbstractFileParse implements FileParse {
	
	@Override
	public String getFileName(String filePath) {
		int lastCode = filePath.lastIndexOf("\\");
		return filePath.substring(lastCode);
	}
}
