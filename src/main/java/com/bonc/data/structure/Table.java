package com.bonc.data.structure;

/**
 * 原始表
 * 
 * @author huh
 *
 */
public class Table {

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
}
