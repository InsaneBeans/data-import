package com.bonc.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bonc.data.file.parse.CsvParse;
import com.bonc.data.file.parse.ExcelInsertWithIndexNo;
import com.bonc.data.structure.AlteredField;
import com.bonc.data.structure.AlteredTable;
import com.bonc.data.structure.FieldType;

@Controller
public class FileController {
	
	@RequestMapping("/excel/simple")
	@ResponseBody
	public void fileUpload() throws Exception {
		AlteredTable table = new AlteredTable();
		table.setFilePath("C:\\Users\\Administrator\\Desktop\\excelTest\\excel\\insert.xlsx");
		table.setTableName("testInsert");

		AlteredField field1 = new AlteredField();
		field1.setFieldType(FieldType.VARCHAR);
		field1.setInsert(true);
		field1.setName("field1");
		field1.setOriginalName("姓名");
		field1.setIndexNo(0);

		AlteredField field2 = new AlteredField();
		field2.setFieldType(FieldType.VARCHAR);
		field2.setInsert(false);
		field2.setName("field2");
		field2.setOriginalName("年龄");
		field2.setIndexNo(1);
		table.setFields(new AlteredField[] { field1, field2 });
		ExcelInsertWithIndexNo insert = new ExcelInsertWithIndexNo();
		insert.tableCreate(table);
	}

	@RequestMapping("/csv/insert")
	@ResponseBody
	public void insert() throws Exception {
		AlteredTable table = new AlteredTable();
		table.setFilePath("C:\\Users\\Administrator\\Desktop\\excelTest\\csv1.csv");
		table.setTableName("testInsert");

		AlteredField field1 = new AlteredField();
		field1.setFieldType(FieldType.VARCHAR);
		field1.setInsert(true);
		field1.setName("field1");
		field1.setOriginalName("姓名");
		field1.setIndexNo(0);

		AlteredField field2 = new AlteredField();
		field2.setFieldType(FieldType.VARCHAR);
		field2.setInsert(true);
		field2.setName("field2");
		field2.setOriginalName("年龄");
		field2.setIndexNo(1);
		table.setFields(new AlteredField[] { field1, field2 });
		CsvParse csvParse = new CsvParse();
		csvParse.csvInsert(table);
	}
}
