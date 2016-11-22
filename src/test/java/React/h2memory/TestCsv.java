package React.h2memory;

import org.junit.Test;

import React.h2memory.file.parse.GetCsvHeader;
import React.h2memory.util.serialize.JsonSerialIml;

public class TestCsv {
	
	@Test
	public void testCsvHeader() throws Exception {
		String filepath = "C:\\Users\\Administrator\\Desktop\\excelTest\\csv.csv";
		JsonSerialIml jsonSerialIml = new JsonSerialIml();
		String re = jsonSerialIml.serializeToString(new GetCsvHeader().getHeaderArray(filepath));
		System.out.println(re);
	}

}
