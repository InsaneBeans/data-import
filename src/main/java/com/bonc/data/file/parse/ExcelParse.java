package com.bonc.data.file.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bonc.data.file.FileUtil;
import com.bonc.data.structure.Field;
import com.bonc.data.structure.FieldType;
import com.bonc.data.structure.Table;
import com.bonc.data.util.CellType;
import com.bonc.data.util.CommonValue;


/**
 * Excel解析工具类
 * 
 * @author huh
 * 
 */
public class ExcelParse {
	
    /**
     * 获取EXCEL表格中页签名的方法，用于数据表的创建
     * 
     * @param filePath 文件路径
     * @return sheet名称列表
     * @throws IOException
     */
    public List<String> getExcelSheetNames(String filePath) throws IOException{
    	FileUtil util = new FileUtil();
        InputStream is = new FileInputStream(filePath);
        Workbook workbook;  
        Sheet sheet; 
        List<String> listNames = new ArrayList<String>();
        if(util.fileTypeJudge(filePath).equals(CommonValue.EXCEL_2003_SUFFIX)){
            workbook = new HSSFWorkbook(is);
        } else {
            workbook = new XSSFWorkbook(is);
        } 
        for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){
            sheet = workbook.getSheetAt(sheetNum);
            String sheetnameString = sheet.getSheetName();
            listNames.add(sheetnameString);
        }
        return listNames;
    }
    
    /**
     * 获取多结构EXCEL表格的表头结构，即每个页签的表结构是不一样的
     * 
     * @param filePath 文件路径
     * @return 表头结构json串
     * @throws IOException
     */
    public List<Table> getMultiExcelStructure(String filePath) throws IOException{
    	Workbook workbook = null;
    	FileUtil fileUtil = new FileUtil();
        InputStream is = new FileInputStream(filePath);
        List<Table> tables = new ArrayList<Table>();
        if(fileUtil.getFileSuffix(filePath).equals(CommonValue.EXCEL_2003_SUFFIX)){
            workbook = new HSSFWorkbook(is);
        } else {
            workbook = new XSSFWorkbook(is);
        }
        //循环遍历页签
        sheetReader:
        for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){
        	Table table = new Table();
            Sheet sheet = workbook.getSheetAt(sheetNum);
            table.setTableName(sheet.getSheetName());
            if(sheet.getLastRowNum() <= 0){
                continue sheetReader;
            }
            Row headRow = sheet.getRow(0);
            int firstColumn  = headRow.getFirstCellNum();
            int lastColumn   = headRow.getLastCellNum();
            List<Field> fields = new ArrayList<Field>();
            for(int i = firstColumn; i< lastColumn; i++ ){
            	Field field = new Field();
                field.setName(CellType.getStringVal(headRow. getCell(i)));
                field.setFieldType(FieldType.VARCHAR);
                fields.add(field);
            }
            table.setFields(fields.toArray(new Field[fields.size()]));
            table.setFilePath(filePath);
            tables.add(table);
        }
        return tables;
    }
    
    
    /**
     * 获取单结构EXCEL表格的表头结构，所有页签对应相同的表
     * 
     * @param filePath 文件路径
     * @return 表头结构json串
     * @throws IOException
     */
    public Table getSimpleExcelStructure(String filePath) throws IOException{
    	Workbook workbook = null;
    	FileUtil fileUtil = new FileUtil();
        InputStream is = new FileInputStream(filePath);
        Table table = new Table();
        if(fileUtil.getFileSuffix(filePath).equals(CommonValue.EXCEL_2003_SUFFIX)){
            workbook = new HSSFWorkbook(is);
        } else {
            workbook = new XSSFWorkbook(is);
        }
        Sheet sheet = workbook.getSheetAt(0);
        table.setTableName(sheet.getSheetName());
        Row headRow = sheet.getRow(0);
        List<Field> fields = new ArrayList<Field>();
        int firstColumn = headRow.getFirstCellNum();
        int lastColumn = headRow.getLastCellNum();
        for(int i = firstColumn; i< lastColumn; i++ ){
            String cellValue = CellType.getStringVal(headRow. getCell(i));
            Field field = new Field();
            field.setName(cellValue);
            field.setFieldType(FieldType.VARCHAR);
            fields.add(field);
        }
        table.setFields(fields.toArray(new Field[fields.size()]));
        table.setFilePath(filePath);
        return table;
    }
}
