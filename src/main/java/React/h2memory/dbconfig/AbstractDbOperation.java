package React.h2memory.dbconfig;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Component;

/**
 * 数据库操作抽象类
 * 
 * @author huh
 *
 */
@Component
public abstract class AbstractDbOperation implements DbOperation{

	/**
	 * 数据库连接地址
	 */
	private String url;
	/**
	 * 数据库用户名
	 */
	private String name;
	/**
	 * 数据库密码
	 */
	private String password;
	/**
	 * 数据库连接对象
	 */
	private Connection connection;
	/**
	 * 数据库驱动
	 */
	private String driver;
	
	public AbstractDbOperation(String url,String name,String password,Connection connection,String driver){
		this.url = url;
		this.connection = connection;
		this.name = name;
		this.password = password;
		this.driver = driver;
	}
	
	public AbstractDbOperation() {
	}
	
	@Override
	public Connection getConnection(){
		try{
		    Class.forName(driver);
		    connection= DriverManager.getConnection(url,name,password);
		} catch (Exception e){
			e.printStackTrace();
		}
		return connection;
	}
}
