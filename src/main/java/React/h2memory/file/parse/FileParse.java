package React.h2memory.file.parse;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

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
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String getFileHeader(String filePath) throws IOException;
	/**
	 * 获取页签名
	 * @param filePath
	 * @return
	 */
	public List<String> getSheetNames(String filePath);
	/**
	 * 获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public String getFileName(String filePath);
}
