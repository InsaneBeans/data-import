package com.bonc.data.dbconfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.bonc.data.file.FileType;
import com.bonc.data.file.FileUtil;
import com.bonc.data.structure.Field;
import com.bonc.data.structure.Table;
import com.bonc.data.util.NameUtil;
import com.bonc.data.util.exception.DbException;


/**
 * h2数据库操作实现类
 * 
 * @author huh
 *
 */
@Component
public class H2DbOperation extends AbstractDbOperation{
	
	public H2DbOperation(String url, String name, String password, String driver) {
		super(url, name, password, driver);
	}

	public H2DbOperation() {
		super();
	}
	
	@Override
	public boolean createDb(String dbName) {
		return false;
	}

	@Override
	public boolean createTable(String tableName) {
		StringBuilder sql = new StringBuilder("CREATE TABLE ");
		Connection connection = new H2Config().getH2Connection();
		Statement pStatement = null;
		try{
			sql.append(tableName);
			sql.append("(ID INT PRIMARY KEY)");
			pStatement = connection.createStatement();
			if(pStatement.executeUpdate(sql.toString())==0) {
				System.out.println("表"+tableName+"创建成功:"+sql);
			} else {
				System.out.println("创建失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean createTable(String tableName, Field[] fields) {
		StringBuilder sql = new StringBuilder("CREATE TABLE ");
		Connection connection = new H2Config().getH2Connection();
		Statement pStatement = null;
		NameUtil nameUtil = new NameUtil();
		try{
			if(nameUtil.isChinese(tableName)) {
				sql.append(nameUtil.generateEnglishName(tableName));
			} else {
				sql.append(tableName + "(");
			}
			for(Field field: fields) {
				//中文字段的判断
				if(!nameUtil.isChinese(field.getName())) {
					sql.append(field.getName() +" "+ field.getFieldType() + ",");
				} else {
					String newFieldName = nameUtil.generateColumnName(field);
					sql.append(newFieldName +" "+ field.getFieldType() + ",");
				}
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(")");
			pStatement = connection.createStatement();
			if(pStatement.executeUpdate(sql.toString())==0) {
				System.out.println("表"+tableName+"创建成功:"+sql);
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
		return false;
	}

	@Override
	public String insertData(String filePath) {
		StringBuilder sqlBuilder = new StringBuilder("");
		FileUtil fileUtil = new FileUtil();
		Connection connection = new H2Config().getH2Connection();
		Statement statement = null;
		try{
			statement = connection.createStatement();
			//CSV文件的数据导入
			if(fileUtil.fileTypeJudge(filePath).equals(FileType.CSV)) {
				sqlBuilder.append("INSERT INTO CSV (SELECT * FROM CSVREAD");
				sqlBuilder.append("('");
				sqlBuilder.append(filePath);
				sqlBuilder.append("'))");
				if(!statement.execute(sqlBuilder.toString())) {
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
		Connection connection = new H2Config().getH2Connection();
		try {
			ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null);
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
