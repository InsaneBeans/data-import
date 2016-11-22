package React.h2memory.dbconfig;

/**
 * 数据库数据表
 * 
 *@author huh
 *
 */
public class DbTable {
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 表包含的列
	 */
	private DbField[] fields;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public DbField[] getFields() {
		return fields;
	}
	public void setFields(DbField[] fields) {
		this.fields = fields;
	}
}
