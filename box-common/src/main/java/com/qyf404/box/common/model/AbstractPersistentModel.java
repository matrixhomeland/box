package com.qyf404.box.common.model;

import java.io.Serializable;

import com.qyf404.box.common.cmd.DeleteCommand;
import com.qyf404.box.common.cmd.SaveCommand;
import com.qyf404.box.common.cmd.SaveOrUpdateCommand;
import com.qyf404.box.common.cmd.UpdateCommand;
import com.qyf404.box.core.svc.CommandService;

public abstract class AbstractPersistentModel implements PersistentModel,Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void save(CommandService commandService){
		commandService.execute(new SaveCommand<PersistentModel>(this));
	}
	@Override
	public void update(CommandService commandService){
		commandService.execute(new UpdateCommand<PersistentModel>(this));
	}
	@Override
	public void saveOrUpdate(CommandService commandService){
		commandService.execute(new SaveOrUpdateCommand<PersistentModel>(this));
	}
	@Override
	public void delete(CommandService commandService){
		commandService.execute(new DeleteCommand<PersistentModel>(this));
//		commandService.execute(new LogicallyDeleteCommand<AbstractPersistentModel>(this));
	}
	
	
}
