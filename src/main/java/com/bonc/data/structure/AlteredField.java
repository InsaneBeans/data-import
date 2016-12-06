package com.bonc.data.structure;

public class AlteredField {

	/**
	 * 原来字段的名称
	 */
	private String origenalName;
	/**
	 * 字段名称
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

	public String getOrigenalName() {
		return origenalName;
	}

	public void setOrigenalName(String origenalName) {
		this.origenalName = origenalName;
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
}
