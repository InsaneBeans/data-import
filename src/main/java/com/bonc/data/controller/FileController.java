package com.bonc.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bonc.data.dboperation.DbOperation;
import com.bonc.data.file.parse.CsvParse;
import com.bonc.data.file.parse.ExcelInsert;
import com.bonc.data.file.upload.FileUpload;
import com.bonc.data.structure.AlteredField;
import com.bonc.data.structure.AlteredTable;
import com.bonc.data.structure.Field;
import com.bonc.data.structure.FieldType;
import com.bonc.data.structure.Table;

@Controller
public class FileController {

	@Autowired
	private FileUpload fileUpload;
	@Autowired
	private DbOperation dbOperation;

	@RequestMapping("/excel/simple")
	@ResponseBody
	public Table fileUpload(@RequestParam("file") MultipartFile file) {
		Table table = null;
		return table;
	}

	@RequestMapping("/excel/insert")
	public void excelIsnert() throws Exception {
		AlteredTable table = new AlteredTable();
		table.setFilePath("C:\\Users\\Administrator\\Desktop\\excelTest\\excel\\insert.xlsx");
		table.setTableName("testInsert");

		AlteredField field1 = new AlteredField();
		field1.setFieldType(FieldType.VARCHAR);
		field1.setInsert(false);
		field1.setName("field1");
		field1.setOriginalName("姓名");

		AlteredField field2 = new AlteredField();
		field2.setFieldType(FieldType.VARCHAR);
		field2.setInsert(false);
		field2.setName("field2");
		field2.setOriginalName("年龄");
		table.setFields(new AlteredField[] { field1, field2 });
		ExcelInsert excelInsert = new ExcelInsert();
		excelInsert.tableCreate(table);
	}

	/**
	 * csv文件上传导入的controller
	 * 
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping("/csv")
	@ResponseBody
	public void csvTest(@RequestParam("file") MultipartFile file) throws Exception {
		CsvParse getCsvHeader = new CsvParse();
		String filePath = fileUpload.fileUpload(file);
		Field[] fields = getCsvHeader.getFields(filePath);
		String tableName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
		Table table = new Table();
		table.setName(tableName);
		table.setFields(fields);
		if (!dbOperation.isExist(tableName)) {
			dbOperation.createTable(table);
		}
		dbOperation.insertData(filePath);
	}
}
