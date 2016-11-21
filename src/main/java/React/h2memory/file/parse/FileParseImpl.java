package React.h2memory.file.parse;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 文件解析实现类
 * 
 * @author huh
 *
 */
@Component
public class FileParseImpl implements FileParse{
	
	private String result;
	
	@Override
	public String getFileHeader(String filePath) throws IOException{
		result = new GetFileStructure().getFileHeader(filePath);
		return result;
	}

	@Override
	public List<String> getSheetNames(String filePath) {
		return null;
	}

	@Override
	public String getFileName(String filePath) {
		return null;
	}
}
