package com.bonc.test.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bblee.data.service.file.parse.CsvParse;
import com.bblee.data.service.file.parse.ExcelParse;
import com.bblee.data.service.structure.Field;
import com.bblee.data.service.structure.FieldType;
import com.bblee.data.service.structure.Table;

@Controller
public class FileController1 {

	@RequestMapping("/excel/singlefile/import")
	@ResponseBody
	public void fileUpload() throws Exception {
		Table table = new Table();
		table.setFilePath("C:\\Users\\Administrator\\Desktop\\excelTest\\excel\\insert.xlsx");
		table.setTableName("testInsert");

		Field field1 = new Field();
		field1.setFieldType(FieldType.VARCHAR);
		field1.setInsert(true);
		field1.setName("field1");
		field1.setIndexNo(0);

		Field field2 = new Field();
		field2.setFieldType(FieldType.VARCHAR);
		field2.setInsert(false);
		field2.setName("field2");
		field2.setIndexNo(1);
		table.setFields(new Field[] { field1, field2 });
		ExcelParse insert = new ExcelParse();
		insert.getSimpleExcelContent(table);
	}

	@RequestMapping("/csv/import")
	@ResponseBody
	public void insert() throws Exception {
		Table table = new Table();
		table.setFilePath("C:\\Users\\Administrator\\Desktop\\excelTest\\csv1.csv");
		table.setTableName("testInsert");

		Field field1 = new Field();
		field1.setFieldType(FieldType.VARCHAR);
		field1.setInsert(true);
		field1.setName("field1");
		field1.setIndexNo(0);

		Field field2 = new Field();
		field2.setFieldType(FieldType.VARCHAR);
		field2.setInsert(true);
		field2.setName("field2");
		field2.setIndexNo(1);
		table.setFields(new Field[] { field1, field2 });
		CsvParse csvParse = new CsvParse();
		csvParse.csvInsert(table);
	}

	@RequestMapping("/excel/multitabs/import")
	@ResponseBody
	public void multiStructure() {
//		int type = 2; //2表示多页签对应多张表
	}
}
