package com.qyf404.box.core.svc;

import com.qyf404.box.core.cmd.Command;
import com.qyf404.box.core.env.EnvironmentImpl;


/**
 * @author Tom Baeyens
 */
public class SkipInterceptor extends Interceptor {
  
  static DefaultCommandService defaultCommandService = new DefaultCommandService();

  public <T> T execute(Command<T> command) {
    EnvironmentImpl environment = EnvironmentImpl.getCurrent();
    // if there is an environment active
    if (environment!=null) {
      // skip the rest of the interceptor stack and just execute the command
      return defaultCommandService.execute(command);
    }

    return next.execute(command);
  }

}
