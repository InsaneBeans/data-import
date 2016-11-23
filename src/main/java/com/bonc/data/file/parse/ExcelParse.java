package com.bonc.data.file.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bonc.data.file.FileUtil;
import com.bonc.data.util.CellType;
import com.bonc.data.util.CommonValue;
import com.bonc.data.util.serialize.JsonSerialIml;


/**
 * Excel解析工具类
 * 
 * @author huh
 * 
 */
public class ExcelParse {
	
	/**
	 * 序列化后的字符结果
	 */
	private String serialStringResult;
	/**
	 * 工作簿
	 */
	private Workbook workbook;
	/**
	 * 页签对象
	 */
	private Sheet sheet;
	/**
	 * 表头行对象
	 */
    private Row headRow; 
    
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
     * 获取EXCEL表格的表头结构
     * 
     * @param filePath 文件路径
     * @return 表头结构json串
     * @throws IOException
     */
    public String getExcelStructure(String filePath) throws IOException{
    	FileUtil fileUtil = new FileUtil();
        InputStream is = new FileInputStream(filePath);
        Map<String,List<String>> fileHeaderStructure= new HashMap<String,List<String>>();
        if(fileUtil.getFileSuffix(filePath).equals(CommonValue.EXCEL_2003_SUFFIX)){
            workbook = new HSSFWorkbook(is);
        } else {
            workbook = new XSSFWorkbook(is);
        }
        //循环遍历页签
        sheetNullJudge:
        for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){
            sheet = workbook.getSheetAt(sheetNum);
            String sheetName = sheet.getSheetName();
            System.out.println(sheetName);
            if(sheet.getLastRowNum() <= 0){
                continue sheetNullJudge;
            }
            headRow = sheet.getRow(0);
            int firstColumn  = headRow.getFirstCellNum();
            int lastColumn   = headRow.getLastCellNum();
            List<String> metadataList = new ArrayList<String>();
            for(int i = firstColumn; i< lastColumn; i++ ){
                String cellValue = CellType.getStringVal(headRow. getCell(i));
                metadataList.add(cellValue);
            }
            fileHeaderStructure.put(sheetName, metadataList);
        }
        JsonSerialIml serialObject = new JsonSerialIml();
        serialStringResult= serialObject.serializeToString(fileHeaderStructure);
        return serialStringResult;
    }
}
