package com.bonc.data.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 字符形式获取单元格的内容
 * 
 * @author huh
 *
 */
public class CellType {
    public static String getStringVal(Cell cell){
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_BOOLEAN:
            return cell.getBooleanCellValue()?"true":"false";
        case Cell.CELL_TYPE_NUMERIC:
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getStringCellValue();
        case Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
        case Cell.CELL_TYPE_FORMULA:
            return cell.getCellFormula();
        default:
            return null;
        }
    }
}
