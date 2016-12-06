package com.bonc.test.dbconfig;

import org.junit.Test;

import com.bonc.data.structure.Field;
import com.bonc.data.structure.FieldType;
import com.bonc.data.structure.Table;
import com.bonc.data.util.NameUtil;
import com.bonc.data.util.serialize.JsonSerialIml;

public class TestDbColumn {
	
	@Test
	public void testNameGenerate(){
		Field dbField1 = new Field();
		dbField1.setName("数量");
		dbField1.setFieldType(FieldType.INT);
		Field dbField2 = new Field();
		dbField2.setName("time");
		dbField2.setFieldType(FieldType.TIMESTAMP);
		Field[] fields = {dbField1, dbField2};
		
		Table dbTable = new Table();
		dbTable.setFields(fields);
		dbTable.setTableName("test");
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		System.out.println(jsonSerialIml.serializeToString(
				new NameUtil().getColumnName(dbTable)));
	}
	
	@Test
	public void testGenerateName(){
		Field dbField1 = new Field();
		dbField1.setName("数量");
		dbField1.setFieldType(FieldType.INT);
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		System.out.println(jsonSerialIml.serializeToString(
				new NameUtil().generateColumnName(dbField1)));
	}
}
