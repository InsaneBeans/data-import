package com.bonc.data.dbconfig;

import java.sql.Connection;

import org.springframework.stereotype.Component;

import com.bonc.data.structure.Field;
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
	 * 创建数据库
	 * @return
	 */
	boolean createDb(String dbName);
	/**
	 * 创建数据表
	 */
	boolean createTable(String tableName);
	/**
	 * 创建数据表
	 */
	boolean createTable(String tableName, Field[] fields);
	/**
	 * 创建数据表
	 */
	boolean createTable(Table[] tables);
	/**
	 * 数据插入
	 */
	String insertData(String filePath);
	/**
	 * 数据查询
	 * @return
	 */
	String selectData();
	/**
	 * 删除表格
	 * @return
	 */
	boolean deleteTable();
	/**
	 * 删除数据
	 * @return
	 */
	boolean deleteData();
	/**
	 * 判断数据表是否存在
	 * @return
	 */
	boolean isExist(String tableName);

}
