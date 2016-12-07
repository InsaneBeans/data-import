package com.bonc.test.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bonc.data.Application;
import com.bonc.data.file.parse.ExcelInsert;
import com.bonc.data.structure.AlteredField;
import com.bonc.data.structure.AlteredTable;
import com.bonc.data.structure.FieldType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestAlteredTableCreate {

	@Test
	public void testInsert() throws Exception {
		AlteredTable table = new AlteredTable();
		table.setFilePath("C:\\Users\\Administrator\\Desktop\\excelTest\\excel\\samestructure.xls");
		table.setTableName("testInsert");

		AlteredField field1 = new AlteredField();
		field1.setFieldType(FieldType.VARCHAR);
		field1.setInsert(false);
		field1.setName("field1");
		field1.setOriginalName("第一列");

		AlteredField field2 = new AlteredField();
		field2.setFieldType(FieldType.VARCHAR);
		field2.setInsert(false);
		field2.setName("field2");
		field2.setOriginalName("第二列");
		table.setFields(new AlteredField[] { field1, field2 });
		ExcelInsert excelInsert = new ExcelInsert();
		excelInsert.tableCreate(table);
	}

}
