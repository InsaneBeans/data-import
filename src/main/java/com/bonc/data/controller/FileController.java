package com.bonc.data.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.data.file.parse.CsvParse;
import com.bonc.data.file.parse.ExcelParse;
import com.bonc.data.structure.Table;

/**
 * 文件数据源解析HTTP接口
 * 
 * @author huh
 *
 */
@RestController
public class FileController {

	/**
	 * 多页签一张表读取接口,Type=1
	 * 
	 * @param fileUrl
	 *            文件路径
	 * @return
	 */
	@RequestMapping(value = "/excel/table")
	public Table getSingleTable(String fileUrl) {
		ExcelParse parse = new ExcelParse();
		return parse.getSimpleExcelStructure(fileUrl);
	}

	/**
	 * 读取Csv文件结构
	 * 
	 * @param fileUrl
	 *            文件路径
	 * @return
	 */
	@RequestMapping(value = "/csv/table")
	public Table getCsvTable(String fileUrl) {
		ExcelParse parse = new ExcelParse();
		return parse.getSimpleExcelStructure(fileUrl);
	}

	/**
	 * 一页签一张表读取接口
	 * 
	 * @param fileUrl
	 * @return
	 */
	@RequestMapping(value = "/excel/tables")
	public List<Table> getMultiTables(String fileUrl, int type) {
		ExcelParse parse = new ExcelParse();
		return parse.getMultiExcelStructure(fileUrl);
	}

	/**
	 * 一页签一张表的数据导入
	 * 
	 * @param table
	 */
	@RequestMapping(value = "/excel/singletabs/import")
	public void importSimpleExcelContent(Table table) throws Exception {
		ExcelParse parse = new ExcelParse();
		parse.getSimpleExcelContent(table);
	}

	/**
	 * 多页签一张表的数据导入
	 * 
	 * @param tables
	 */
	@RequestMapping(value = "/excel/multitabs/import")
	public void importMultiExcelContent(List<Table> tables) throws Exception {
		ExcelParse parse = new ExcelParse();
		parse.getMultiTableContent(tables);
	}

	/**
	 * 一个页签一张表的数据导入
	 * 
	 * @param table
	 */
	@RequestMapping(value = "/csv/import")
	public void importCsvContent(Table table) throws Exception {
		CsvParse parse = new CsvParse();
		parse.csvInsert(table);
	}
}
