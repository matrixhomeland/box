package com.qyf404.box.common.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.google.gson.annotations.Expose;
import com.qyf404.box.common.cmd.LogicallyDeleteCommand;
import com.qyf404.box.core.svc.CommandService;

@MappedSuperclass
public class GeneralPersistentModel extends AbstractPersistentModel implements Enabled {
	private static final long serialVersionUID = 1L;
	@Column(nullable=false)
	@Expose
	private boolean enabled = true;
	
	
	@Override
	public void delete(CommandService commandService){
		commandService.execute(new LogicallyDeleteCommand<GeneralPersistentModel>(this));
	}
	
	@Override
	public void disable() {
		this.enabled = false;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
}
