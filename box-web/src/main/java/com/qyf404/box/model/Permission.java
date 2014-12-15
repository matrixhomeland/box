package com.qyf404.box.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "s_permission")
public class Permission {
	/**数据范围，数据权限应该和用户所属组织机构绑定*/
	enum DateRange{
		
	}
	
	/**对url的操作*/
	enum Operation {
		GET, POST, DELETE, PUT
	}

	@Id
	@Column()
	private Integer id;// 自增长ID

	@Column()
	private String name;// 权限名称
	@Column()
	private String url;// http访问url
	@Column()
	private String description;// 描述
	// 权限和操作时多对一的关系，一种操作可以形成多种权限（搭配不同的url）
	// 暂时来说操作目前有GET、POST、PUT、DELETE四种操作，不排除未来会有其他操作
	@Enumerated(EnumType.STRING)
	@Column()
	private Operation operation;// 操作
	// 角色权限是多对多关系
	// @ManyMany(target = Role.class, relTable = "t_role_permission", from =
	// "permissionId", to = "roleId")
	@ManyToMany()
	private Collection<Role> roles;// 角色
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

}
