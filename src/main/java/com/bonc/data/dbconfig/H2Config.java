package com.bonc.data.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

/**
 * h2数据库的访问
 * 
 * @author huh
 *
 */
@Component
public class H2Config {
	
	private String url = "jdbc:h2:mem:testdb";
	private String name = "sa";
	private String password="";
	private Connection connection;
	private String driver = "org.h2.Driver";
	
	public Connection getH2Connection(){
		try{
		    Class.forName(driver);
		    connection= DriverManager.getConnection(url,name,password);
		} catch (Exception e){
			e.printStackTrace();
		}
		return connection;
	}
	
	public String createTable(){
		String result = "";
		String sql = "CREATE TABLE HUHONG(ID INT PRIMARY KEY, NAME VARCHAR(255))";
		Connection connection = new H2Config().getH2Connection();
		Statement pStatement = null;
		try{
			pStatement = connection.createStatement();
			if(pStatement.executeUpdate(sql)==0){
				result = "胡红创建成功";
			} else {
				result = "创建失败";
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public void createTableByName(String tableName){
		StringBuffer sql = new StringBuffer("CREATE TABLE ");
		Connection connection = new H2Config().getH2Connection();
		Statement pStatement = null;
		try{
			sql.append(tableName);
			sql.append("(ID INT PRIMARY KEY, NAME VARCHAR)");
			System.out.println(sql);
			pStatement = connection.createStatement();
			if(pStatement.executeUpdate(sql.toString())==0){
				System.out.println("表"+tableName+"创建成功:"+sql.toString());
			} else {
				System.out.println("创建失败");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String insertData(){
		StringBuffer sql =  new StringBuffer("INSERT INTO HUHONG VALUES(1, 'HUHONG')");
		Connection connection = new H2Config().getH2Connection();
		Statement pStatement = null;
		try{
			pStatement = connection.createStatement();
			if(pStatement.executeUpdate(sql.toString())>0){
				return "插入成功";
			} else {
				return "插入失败";
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String selectData() throws SQLException{
		StringBuffer sql = new StringBuffer("SELECT * FROM HUHONG");
		Connection connection = new H2Config().getH2Connection();
		Statement pStatement = connection.createStatement();
		ResultSet set = pStatement.executeQuery(sql.toString());
		while(set.next()){
			return set.getString(2);
		}
		return set.toString();
	}
	
	public String insertCsvFile(String path) throws SQLException{
		StringBuilder sqlBuffer = new StringBuilder("INSERT INTO CSV (SELECT * FROM CSVREAD");
		Connection connection = new H2Config().getH2Connection();
		Statement pStatement = null;
		pStatement = connection.createStatement();
		sqlBuffer.append("('");
		sqlBuffer.append(path);
		sqlBuffer.append("'))");
		pStatement.execute(sqlBuffer.toString());
		String sql = "SELECT * FROM CSV";
		ResultSet newSet = pStatement.executeQuery(sql);
		System.out.println(newSet.getRow());
		return sqlBuffer.toString();
	}
}
