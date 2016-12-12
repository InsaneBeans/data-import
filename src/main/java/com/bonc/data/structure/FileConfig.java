package com.bonc.data.structure;

/**
 * 文件设置项
 * 
 * @author huh
 *
 */
public class FileConfig {

	/**
	 * 表头是第几行，默认是1
	 */
	private int headerRow;
	/**
	 * 指定类型，多页签对应同一个表为类型1，每个页签对应一个表为类型2
	 */
	private int type;

	public int getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(int headerRow) {
		this.headerRow = headerRow;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
