package com.bonc.data.structure;

/**
 * 原始表
 * 
 * @author huh
 *
 */
public class Table {
	
	/**
	 * 当前表的文件路径
	 */
	private String filePath;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 表字段
	 */
	private Field[] fields;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
