package React.h2memory.dbconfig;

/**
 * 数据表表字段类
 * 
 * @author huh
 *
 */
public class Field {
	
	/**
	 * 字段名称
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private FieldType type;
	/**
	 * 字段长度
	 */
	private int maxLength;
	
	public Field(String name,FieldType type,int maxLength){
		this.name = name;
		this.type = type;
		this.maxLength = maxLength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
}
