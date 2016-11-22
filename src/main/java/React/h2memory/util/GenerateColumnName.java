package React.h2memory.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import React.h2memory.dbconfig.DbField;
import React.h2memory.dbconfig.DbTable;

/**
 * 字段名生成类
 * 
 * @author huh
 *
 */
public class GenerateColumnName {
	
	/**
	 * 字段计数
	 */
	private int count = 0;
	/**
	 * 字段名称映射
	 */
	private Map<DbField, String> columnNameMap = new HashMap<DbField, String>();
	
	public List<String> getColumnName(DbTable dbTable){
		DbField[] fields = dbTable.getFields();
		List<String> columnList = new ArrayList<String>();
		for(int i=0, n=fields.length; i < n; i++) {
			if(isChinese(fields[i].getName())){
				columnList.add(generateColumnName(fields[i]));
				columnNameMap.put(fields[i], generateColumnName(fields[i]));
			} else {
				columnList.add(fields[i].getName());
				columnNameMap.put(fields[i], fields[i].getName());
			}
		}
		//下面这么写法怎么不对？
//		Stream.of(fields).map(field -> {
//			if(isChinese(field.getName())) {
//				columnList.add(field.getName());
//			} else {
//				columnList.add(generateColumnName(field));
//			}
//			return field;
//		});
		return columnList;
	}
	
	public String[] getColumnNames(){
		return null;
	}
	
	/**
	 * 判断单个字符的格式
	 * @param c 单个字符
	 * @return
	 */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS 
        		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A 
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION 
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
	
	/**
	 * 判断字符串是否包含中文
	 * @param str
	 * @return
	 */
	private boolean isChinese(String str) {
		 char[] ch = str.toCharArray();
		 int totalChar = ch.length;
		 int trueNum = 0;
	     for (int i = 0; i < ch.length; i++) {
	         char c = ch[i];
	         if (isChinese(c)) {
	             trueNum++;
             } 
	     }
	     if(trueNum == totalChar || trueNum<totalChar && trueNum!=0) {
	    	 return true;
	     } else {
	    	 return false;
	     }
    }
	
	/**
	 * 生成英文字段名
	 * @param columnName
	 * @return
	 */
	public String generateColumnName(DbField dbField){
		String fieldName = columnNameMap.get(dbField);
        if (fieldName == null) {
        	fieldName = "column_" + (++count);
            columnNameMap.put(dbField, fieldName);
        }
        return fieldName;  
	}
	
	/**
	 * 获取字段映射后的对应关系
	 * @return
	 */
	public Map<DbField, String> getFieldMap(){
		return columnNameMap;
	}
}
