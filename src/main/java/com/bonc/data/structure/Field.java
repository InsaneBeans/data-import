package com.bonc.data.structure;

/**
 * 原字段
 * 
 * @author huh
 *
 */
public class Field {

	/**
	 * 字段名
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private FieldType fieldType;

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
}
