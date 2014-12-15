package com.qyf404.box.core.svc;




/**
 * 所有业务层都要继承这个抽象类来处理业务
 * @author Tom Baeyens
 */
public class AbstractServiceImpl {
  
  protected CommandService commandService;

  public CommandService getCommandService() {
    return commandService;
  }

  public void setCommandService(CommandService commandService) {
    this.commandService = commandService;
  }
}
