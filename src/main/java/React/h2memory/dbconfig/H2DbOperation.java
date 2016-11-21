package React.h2memory.dbconfig;

import java.sql.Connection;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.mysql.jdbc.Field;

/**
 * h2数据库操作实现类
 * 
 * @author huh
 *
 */
@Component
public class H2DbOperation extends AbstractDbOperation{

	public H2DbOperation(String url, String name, String password, Connection connection, String driver) {
		super(url, name, password, connection, driver);
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
			if(pStatement.executeUpdate(sql.toString())==0){
				System.out.println("表"+tableName+"创建成功:"+sql);
			} else {
				System.out.println("创建失败");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean createTable(String tableName, Field[] fields) {
		return false;
	}
	
	@Override
	public boolean createTable(String[] tableNames) {
		return false;
	}

	@Override
	public String insertData() {
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
}
