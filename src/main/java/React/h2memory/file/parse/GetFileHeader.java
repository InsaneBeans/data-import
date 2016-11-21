package React.h2memory.file.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import React.h2memory.util.CommonValue;
import React.h2memory.util.FileUtil;


/**
 * 获取文件的第一行（即表头信息）工具类，以此得到N个字符名称，用于创建数据库的表
 * 
 * @author huh
 * 
 */
public class GetFileHeader {
    
    /**
     * 获取EXCEL表格中页签名的方法
     * 
     * @param filePath 文件路径
     * @return sheet名称列表
     * @throws IOException
     */
    public List<String> getFileHeader(String filePath) throws IOException{
    	FileUtil util = new FileUtil();
        InputStream is = new FileInputStream(filePath);
        Workbook workbook;  
        Sheet sheet; 
        List<String> listHeader= new ArrayList<String>();
        if(util.fileTypeJudge(filePath).equals(CommonValue.EXCEL_2003_SUFFIX)){
            workbook = new HSSFWorkbook(is);
        } else {
            workbook = new XSSFWorkbook(is);
        } 
        for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){
            sheet = workbook.getSheetAt(sheetNum);
            String sheetnameString = sheet.getSheetName();
            listHeader.add(sheetnameString);
        }
        return listHeader;
    }
}
