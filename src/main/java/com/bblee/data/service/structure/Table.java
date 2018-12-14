package com.bblee.data.service.structure;

/**
 * 更改后提交的表
 * 
 * @author huh
 *
 */
public class Table {

	/**
	 * 操作文件的路径
	 */
	private String filePath;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 文件类型（指多页签对应同一个表格或一个页签对应一个表格，前者为1，后者为2）
	 */
	private int type;
	/**
	 * 表头行数
	 */
	private int headerRow;
	
	public int getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(int headerRow) {
		this.headerRow = headerRow;
	}

	/**
	 * 更改后的字段
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
