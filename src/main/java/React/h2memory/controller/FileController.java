package React.h2memory.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import React.h2memory.dbconfig.DbOperation;
import React.h2memory.dbconfig.DbField;
import React.h2memory.dbconfig.H2Config;
import React.h2memory.file.parse.FileParse;
import React.h2memory.file.parse.GetCsvHeader;
import React.h2memory.file.parse.GetExcelHeader;
import React.h2memory.file.upload.FileUpload;

@Controller
public class FileController {
	
	@Autowired
	private FileUpload fileUpload;
	@Autowired 
	private FileParse fileParse;
	@Autowired
	private DbOperation dbOperation;
	
	@RequestMapping("/file")
	@ResponseBody
	public String fileUpload(@RequestParam("file") MultipartFile file) {
		String tableStructure = "";
		List<String> sheetStrings = new ArrayList<String>();
		H2Config h2Config = new H2Config();
		String filePath = fileUpload.fileUpload(file);
		if(!filePath.isEmpty()) {
			try{
				tableStructure = fileParse.getFileHeader(filePath);
				sheetStrings = new GetExcelHeader().getFileHeader(filePath);
				sheetStrings.forEach((String sheetString) -> h2Config.createTableByName(sheetString));
			} catch ( Exception e) {
				e.printStackTrace();
			}
			return tableStructure;
		}
		return tableStructure;
	}
	
	@RequestMapping("/h2")
	@ResponseBody
	public String csvH2Test() throws Exception {
		H2Config h2Config = new H2Config();
		return h2Config.insertCsvFile("C:\\Users\\Administrator\\Desktop\\excelTest\\csv.csv");
	}
	
	/**
	 * csv文件上传导入的controller
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping("/csv")
	@ResponseBody
	public void csvTest(@RequestParam("file") MultipartFile file) throws Exception {
		GetCsvHeader getCsvHeader = new GetCsvHeader();
		String filePath = fileUpload.fileUpload(file);
		DbField[] fields = getCsvHeader.getHeaderArray(filePath);
		String tableName = "csv";
		if(!dbOperation.isExist(tableName)){
			if(dbOperation.createTable(tableName, fields)) System.out.println("创建表成功");
		};
		//存入数据库
		H2Config config = new H2Config();
		config.insertCsvFile(filePath);
	}
}
