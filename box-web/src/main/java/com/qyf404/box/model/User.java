package com.qyf404.box.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.gson.annotations.Expose;
import com.qyf404.box.common.model.AbstractPersistentModel;
import com.qyf404.box.common.model.Enabled;

@Entity
@Table(name = "s_user")
public class User extends AbstractPersistentModel implements UserDetails,Enabled{
	private static final long serialVersionUID = -6887782523036018478L;
	
	@Id
	@TableGenerator(name = "empGen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "EMP_ID", allocationSize = 50)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "empGen")
	@Column
	@Expose
	private Integer id;
	@NaturalId
	@Column
	@Expose
	private String username;
	@Column
	private String password;
	@Column
	@Expose
	private String email;

	@Column
	@Expose
	private boolean enabled;
	
	@ManyToMany(targetEntity=Role.class, fetch=FetchType.LAZY)
	private Collection<Role> roles;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(getRoles() == null){
			return null;
		}
		
		Collection<GrantedAuthority> coll = new ArrayList<GrantedAuthority>(getRoles().size());
		
		for(Role role : getRoles()){
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getAuthority()); 
			coll.add(grantedAuthority);
		}
		
		return coll;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}
	
}

