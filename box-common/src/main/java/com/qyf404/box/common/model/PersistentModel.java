package com.qyf404.box.common.model;

import com.qyf404.box.core.svc.CommandService;

public interface PersistentModel {

	public abstract void delete(CommandService commandService);

	public abstract void saveOrUpdate(CommandService commandService);

	public abstract void update(CommandService commandService);

	public abstract void save(CommandService commandService);



}
