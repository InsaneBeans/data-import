package com.bonc.data.dboperation;

import java.sql.Connection;

import org.springframework.stereotype.Component;

import com.bonc.data.structure.Table;

/**
 * 数据库操作接口
 * 
 * @author huh
 *
 */
@Component
public interface DbOperation {
	
	/**
	 * 获取数据库的连接
	 * @return Connection对象
	 */
	Connection getConnection();
	/**
	 * 创建单个数据表
	 */
	boolean createTable(Table table);
	/**
	 * 创建多个数据表
	 */
	boolean createTable(Table[] tables);
	/**
	 * 数据插入
	 */
	String insertData(String filePath);
	/**
	 * 删除表格
	 * @return
	 */
	boolean deleteTable();
	/**
	 * 判断数据表是否存在
	 * @return
	 */
	boolean isExist(String tableName);

}
