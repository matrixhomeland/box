package com.qyf404.box.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="s_role")
public class Role {

	@Id
	@TableGenerator(name = "empGen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "EMP_ID", allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "empGen")
	@Column()
	private Integer id;// 自增长ID
	@Column()
	private String name;// 角色名称
	
	/**
	 * 角色的代码
	 */
	@Column()
	private String authority;
	@Column()
	private String description;// 角色描述
	// 角色用户多对多关系
	//	@ManyToMany(target = User.class, relTable = "t_user_role", from = "roleId", to = "userId")
//	@ManyToMany(mappedBy="roles")
//	private Collection<User> users ;// 用户
	// 角色权限多对多关系
//	@ManyMany(target = Permission.class, relTable = "t_role_permission", from = "roleId", to = "permissionId")
//	private List<Permission> permissions = new ArrayList<Permission>();// 权限
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	

}
