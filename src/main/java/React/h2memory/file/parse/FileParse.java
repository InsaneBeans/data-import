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
	
	public String getFileHeader(String filePath) throws IOException;
	
	public List<String> getSheetNames(String filePath);

}
