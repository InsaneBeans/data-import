package com.bonc.test.dbconfig;

import org.junit.Test;

import com.bonc.data.dbconfig.DbField;
import com.bonc.data.dbconfig.DbTable;
import com.bonc.data.dbconfig.FieldType;
import com.bonc.data.util.GenerateColumnName;
import com.bonc.data.util.serialize.JsonSerialIml;

public class TestDbColumn {
	
	@Test
	public void testNameGenerate(){
		DbField dbField1 = new DbField();
		dbField1.setName("数量");
		dbField1.setType(FieldType.INT);
		DbField dbField2 = new DbField();
		dbField2.setName("time");
		dbField2.setType(FieldType.TIMESTAMP);
		DbField[] fields = {dbField1, dbField2};
		
		DbTable dbTable = new DbTable();
		dbTable.setFields(fields);
		dbTable.setTableName("test");
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		System.out.println(jsonSerialIml.serializeToString(
				new GenerateColumnName().getColumnName(dbTable)));
	}
	
	@Test
	public void testGenerateName(){
		DbField dbField1 = new DbField();
		dbField1.setName("数量");
		dbField1.setType(FieldType.INT);
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		System.out.println(jsonSerialIml.serializeToString(
				new GenerateColumnName().generateColumnName(dbField1)));
	}
}
