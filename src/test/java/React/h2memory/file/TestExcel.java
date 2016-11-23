package React.h2memory.file;

import org.junit.Test;

import React.h2memory.file.parse.ExcelParse;
import React.h2memory.util.serialize.JsonSerialIml;

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
