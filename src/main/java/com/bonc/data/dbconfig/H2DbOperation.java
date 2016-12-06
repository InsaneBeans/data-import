package com.bonc.data.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.bonc.data.file.FileType;
import com.bonc.data.file.FileUtil;
import com.bonc.data.structure.Field;
import com.bonc.data.structure.Table;
import com.bonc.data.util.exception.DbException;

/**
 * h2数据库操作实现类
 * 
 * @author huh
 *
 */
@Component
public class H2DbOperation implements DbOperation {
	
	private Logger logger;

	private String url = "jdbc:h2:mem:testdb";
	private String name = "sa";
	private String password = "";
	private String driver = "org.h2.Driver";
	
	/**
	 * 数据库连接对象
	 */
	private Connection connection;

	@Override
	public boolean createTable(Table table) {
		StringBuilder sql = new StringBuilder("CREATE TABLE ");
		Connection connection = this.getConnection();
		Statement Statement = null;
		String tableName = table.getName();
		Field[] fields = table.getFields();
		try {
			sql.append(tableName + "(");
			for (Field field : fields) {
				sql.append(field.getName() + " " + field.getFieldType() + ",");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(")");
			Statement = connection.createStatement();
			if (Statement.executeUpdate(sql.toString()) == 0) {
				logger.info("表" + tableName + "创建成功:" + sql);
			} else {
				throw new DbException("创建数据表失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean createTable(Table[] tables) {
		for(Table table: tables) {
			StringBuilder sql = new StringBuilder("CREATE TABLE ");
			Connection connection = this.getConnection();
			Statement Statement = null;
			String tableName = table.getName();
			Field[] fields = table.getFields();
			try {
				sql.append(tableName + "(");
				for (Field field : fields) {
					sql.append(field.getName() + " " + field.getFieldType() + ",");
				}
				sql.deleteCharAt(sql.length() - 1);
				sql.append(")");
				Statement = connection.createStatement();
				if (Statement.executeUpdate(sql.toString()) == 0) {
					logger.info("表" + tableName + "创建成功:" + sql);
				} else {
					throw new DbException("创建数据表失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		return false;
	}

	@Override
	public String insertData(String filePath) {
		StringBuilder sqlBuilder = new StringBuilder("");
		FileUtil fileUtil = new FileUtil();
		Connection connection = this.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			// CSV文件的数据导入
			if (fileUtil.fileTypeJudge(filePath).equals(FileType.CSV)) {
				sqlBuilder.append("INSERT INTO CSV (SELECT * FROM CSVREAD");
				sqlBuilder.append("('");
				sqlBuilder.append(filePath);
				sqlBuilder.append("'))");
				if (!statement.execute(sqlBuilder.toString())) {
					return "插入数据成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String selectData() {
		return null;
	}

	@Override
	public boolean deleteTable() {
		return false;
	}

	@Override
	public boolean deleteData() {
		return false;
	}

	@Override
	public boolean isExist(String tableName) {
		Connection connection = this.getConnection();
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, name, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
