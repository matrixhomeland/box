package com.qyf404.box.core.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qyf404.box.core.util.Closable;


public class DefaultEnvironment extends BasicEnvironment {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(DefaultEnvironment.class.getName());

	public DefaultEnvironment(){
		
	}
	public String toString() {
		return "BoxEnvironment[" + System.identityHashCode(this) + "]";
	}
	/* (non-Javadoc)
	 * @see com.qyf404.box.core.env.BasicEnvironment#close()
	 */
	@Override
	public void close() {
	    Context context = getEnvironmentContext();
	    if (context instanceof Closable) {
	      ((Closable)context).close();
	    }
	    super.close();
	    if (log.isTraceEnabled()) log.trace("closed "+this);
	  }
}
