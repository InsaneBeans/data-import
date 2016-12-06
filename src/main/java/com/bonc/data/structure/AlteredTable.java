package com.bonc.data.structure;

/**
 * 更改后提交的表
 * 
 * @author huh
 *
 */
public class AlteredTable {

	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 更改后的字段
	 */
	private AlteredField[] fields;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public AlteredField[] getFields() {
		return fields;
	}

	public void setFields(AlteredField[] fields) {
		this.fields = fields;
	}
}
