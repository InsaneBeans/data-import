package React.h2memory.file;

import org.junit.Test;

import React.h2memory.file.parse.CsvParse;
import React.h2memory.util.serialize.JsonSerialIml;

public class TestCsv {
	
	@Test
	public void testCsvHeader() throws Exception {
		String filepath = "C:\\Users\\Administrator\\Desktop\\excelTest\\csv.csv";
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		String re = jsonSerialIml.serializeToString(new CsvParse().getHeaderArray(filepath));
		System.out.println(re);
	}

}
