package com.bonc.data.structure;

/**
 * 更改之后的字段
 * 
 * @author huh
 *
 */
public class AlteredField {

	/**
	 * 字段原来的名称
	 */
	private String originalName;
	/**
	 * 当前字段名称（存入数据库）
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private FieldType fieldType;
	/**
	 * 字段是否导入数据库
	 */
	private boolean isInsert;
	/**
	 * 索引号
	 */
	private int indexNo;

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public boolean isInsert() {
		return isInsert;
	}

	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}

	public int getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(int indexNo) {
		this.indexNo = indexNo;
	}
}
