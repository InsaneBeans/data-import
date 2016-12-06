package com.bonc.data.file.parse;

import com.bonc.data.util.serialize.JsonSerialIml;

public class Test {

	public static void main(String[] args) throws Exception {
		String filePath = "C:\\Users\\Administrator\\Desktop\\excelTest\\csv.csv";
		CsvParse parse = new CsvParse();
		System.out.println(new JsonSerialIml().serializeToString(parse.getHeaderInfo(filePath)));
	}
}
