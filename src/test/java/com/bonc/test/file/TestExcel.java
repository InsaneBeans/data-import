package com.bonc.test.file;

import org.junit.Test;

import com.bonc.data.file.parse.ExcelParse;
import com.bonc.data.util.serialize.JsonSerialIml;

public class TestExcel {
	
	@Test
	public void testExcel() throws Exception {
		ExcelParse excelParse = new ExcelParse();
		String filePathString = "C:\\Users\\Administrator\\Desktop\\excelTest\\user.xlsx";
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		String reString = jsonSerialIml.serializeToString(excelParse.getExcelSheetNames(filePathString));
		String re = jsonSerialIml.serializeToString(excelParse.getExcelStructure(filePathString));
		System.out.println(reString);
		System.out.println(re);
	}
}
