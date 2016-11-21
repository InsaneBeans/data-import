package React.h2memory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import React.h2memory.dbconfig.H2Config;
import React.h2memory.file.parse.FileParse;
import React.h2memory.file.parse.GetFileHeader;
import React.h2memory.file.upload.FileUpload;

@Controller
public class FileController {
	
	@Autowired
	private FileUpload fileUpload;
	@Autowired 
	private FileParse fileParse;
	
	@RequestMapping("/file")
	@ResponseBody
	public String fileUpload(@RequestParam("file") MultipartFile file) {
		String tableStructure = "";
		List<String> sheetStrings = new ArrayList<String>();
		H2Config h2Config = new H2Config();
		String filePath = fileUpload.fileUpload(file);
		if(filePath.indexOf("\\")>0) {
			try{
				tableStructure = fileParse.getFileHeader(filePath);
				sheetStrings = new GetFileHeader().getFileHeader(filePath);
				for( int i=0; i<sheetStrings.size(); i++ ) {
					h2Config.createTableByName(sheetStrings.get(i));
				}
				System.out.println(h2Config.createTable());
				System.out.println(h2Config.insertData());
				System.out.println(h2Config.selectData());
			} catch ( Exception e) {
				e.printStackTrace();
			}
			return tableStructure;
		}
		return tableStructure;
	}
	
	@RequestMapping("/h2")
	@ResponseBody
	public String csvH2Test() throws Exception{
		H2Config h2Config = new H2Config();
		return h2Config.insertCsvFile("C:\\Users\\Administrator\\Desktop\\excelTest\\csv.csv");
	}
}
