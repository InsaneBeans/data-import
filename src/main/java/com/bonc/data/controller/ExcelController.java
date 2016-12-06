package com.bonc.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bonc.data.file.parse.ExcelParse;
import com.bonc.data.file.upload.FileUpload;
import com.bonc.data.poi.Reader;
import com.bonc.data.structure.Table;

@RestController
public class ExcelController {

	@Autowired
	private Reader reader;
	@Autowired
	private FileUpload fileUpload;

	@RequestMapping(value = "/excel/test")
	public String excelInsert(@RequestParam("file") MultipartFile file) throws Exception {
		String saveAddress = fileUpload.fileUpload(file);
		return reader.excelReader(saveAddress);
	}

	@RequestMapping(value = "/excel/header")
	public Table excelHeader(@RequestParam("file") MultipartFile file) throws Exception {
		String saveAddress = fileUpload.fileUpload(file);
		ExcelParse parse = new ExcelParse();
		return parse.getSimpleExcelStructure(saveAddress);
	}

}
