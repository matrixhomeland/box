package com.qyf404.box.core.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qyf404.box.core.BoxException;
import com.qyf404.box.core.cmd.Command;
import com.qyf404.box.core.env.EnvironmentImpl;




/**
 * @author Tom Baeyens
 */
public class DefaultCommandService implements CommandService {

  private static final Logger log = LoggerFactory.getLogger(DefaultCommandService.class);

  public <T> T execute(Command<T> command) {
    EnvironmentImpl environment = EnvironmentImpl.getCurrent();
    
    try {
      return command.execute(environment);
      
    } catch (RuntimeException e) {
       log.info("exception while executing command "+command, e);
      throw e;
      
    } catch (Exception e) {
      log.info("exception while executing command "+command, e);
      throw new BoxException("exception while executing command "+command, e);
    }
  }
}
