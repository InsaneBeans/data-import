package com.bonc.data.poi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Component;

@Component
public class SQLExecutor {

	private String url = "jdbc:h2:mem:testdb";
	private String name = "sa";
	private String password = "";
	private Connection connection;
	private String driver = "org.h2.Driver";

	private Connection getH2Connection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, name, password);
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
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);
			if (!statement.execute(sql)) {
				return "执行成功:" + sql;
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
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
					ResultSet.CONCUR_UPDATABLE);
			return statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
