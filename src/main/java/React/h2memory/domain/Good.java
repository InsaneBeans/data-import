package React.h2memory.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Good {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String alias;
	
	@OneToOne
	@JoinColumn(name = "provinceId")
	private Province province;
	
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
