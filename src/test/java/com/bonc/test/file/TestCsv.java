package com.bonc.test.file;

import org.junit.Test;

import com.bonc.data.file.parse.CsvParse;
import com.bonc.data.util.serialize.JsonSerialIml;

public class TestCsv {
	
	@Test
	public void testCsvHeader() throws Exception {
		String filepath = "C:\\Users\\Administrator\\Desktop\\excelTest\\csv.csv";
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		String re = jsonSerialIml.serializeToString(new CsvParse().getHeaderArray(filepath));
		System.out.println(re);
	}

}
