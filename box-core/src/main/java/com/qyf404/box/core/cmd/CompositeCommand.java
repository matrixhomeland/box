package com.qyf404.box.core.cmd;

import java.util.ArrayList;
import java.util.List;

import com.qyf404.box.core.env.Environment;

/** container for executing multiple commands in one transaction. 
 * 
 */
public class CompositeCommand extends AbstractCommand<Void> {
  
  private static final long serialVersionUID = 1L;

  protected List<Command<?>> commands = new ArrayList<Command<?>>();

  public Void execute(Environment environment) throws Exception {
    for (Command<?> command: commands) {
      command.execute(environment);
    }
    return null;
  }

  public void addCommand(Command<?> command) {
    commands.add(command);
  }
}
