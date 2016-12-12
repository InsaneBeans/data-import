package com.bonc.data.controller;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bonc.data.file.parse.ExcelInsertWithIndexNo;
import com.bonc.data.file.parse.ExcelParse;
import com.bonc.data.file.upload.FileUpload;
import com.bonc.data.poi.SQLExecutor;
import com.bonc.data.structure.AlteredTable;
import com.bonc.data.structure.Table;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ExcelController {

	@Autowired
	private FileUpload fileUpload;

	@RequestMapping(value = "/excel/test")
	@ResponseBody
	public String excelInsert(@RequestParam("table")AlteredTable table) throws Exception {
		ExcelInsertWithIndexNo excel = new ExcelInsertWithIndexNo();
		excel.tableCreate(table);
		String sqlString = "SELECT * FROM " + table.getTableName();
		SQLExecutor executor = new SQLExecutor();
		ResultSet set = executor.executeQuery(sqlString);
		String result = new ObjectMapper().writeValueAsString(set);
		System.out.println(result);
		return null;
	}

	@RequestMapping(value = "/excel/header")
	public Table excelHeader(@RequestParam("file") MultipartFile file) throws Exception {
		String saveAddress = fileUpload.fileUpload(file);
		ExcelParse parse = new ExcelParse();
		return parse.getSimpleExcelStructure(saveAddress);
	}

}
