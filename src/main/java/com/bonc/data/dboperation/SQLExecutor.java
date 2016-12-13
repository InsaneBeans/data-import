package com.bonc.data.dboperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * SQL执行器
 * 
 *@author huh
 *
 */
@Component
public class SQLExecutor {

	/**
	 * 数据库地址
	 */
	private String url;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 驱动
	 */
	private String driver;

	private Connection getH2Connection() {

		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 执行插入SQL
	 * 
	 * @param sql
	 * @return
	 */
	public String execute(String sql) {
		try {
			Connection conn = this.getH2Connection();
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			if (!statement.execute(sql)) {
				return "执行成功:" + sql;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 批量执行SQL语句
	 * 
	 * @param sqls
	 * @return
	 */
	public String multiExecute(List<String> sqls) {
		try {
			Connection conn = this.getH2Connection();
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			for (String sql : sqls) {
				statement.execute(sql);
				System.out.println("执行成功:" + sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行查询
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet executeQuery(String query) {
		try {
			Connection conn = getH2Connection();
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			return statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断数据表是否存在
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean isTableExist(String tableName) {
		try {
			Connection conn = getH2Connection();
			ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
			if (!rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
