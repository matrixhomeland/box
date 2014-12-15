package com.qyf404.box.common.model;

import com.qyf404.box.common.cmd.DeleteCommand;
import com.qyf404.box.common.cmd.SaveCommand;
import com.qyf404.box.common.cmd.SaveOrUpdateCommand;
import com.qyf404.box.common.cmd.UpdateCommand;
import com.qyf404.box.core.svc.CommandService;

public  class AbstractPersistentModel implements PersistentModel {
	
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
	}
	
	
}
