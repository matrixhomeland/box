package com.qyf404.box.core.svc;

import org.hibernate.StaleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qyf404.box.core.BoxException;
import com.qyf404.box.core.cmd.Command;

/**
 * 在执行命令抛出异常后，判断异常类型，选择性的重复执行命令，直到抛出其他异常或达到最大执行次数。 retries the command execution
 * in case exceptions.
 * 
 * @author Tom Baeyens
 * @author Huisheng Xu
 */
public class RetryInterceptor extends Interceptor {

	private static final Logger log = LoggerFactory.getLogger(RetryInterceptor.class);

	int retries = 3;
	long delay = 50;
	long delayFactor = 4;

	public <T> T execute(Command<T> command) {

		int attempt = 1;
		long sleepTime = delay;
		RuntimeException e = null;
		while (attempt <= retries) {
			if (attempt > 1) {
				log.trace("retrying...");
			}
			try {

				return next.execute(command);

			} catch (RuntimeException ex) {
				e = ex;
				if (!this.isCausedByOperateFailure(ex)) {
					throw ex;
				}

				attempt++;
				log.trace("操作失败: " + ex);
				// log.trace("optimistic locking failed: " + ex);
				log.trace("waiting " + sleepTime + " millis");

				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e1) {
					log.trace("尝试休眠失败");
					// log.trace("retry sleeping got interrupted");
				}

				sleepTime *= delayFactor;
			}
		}

		throw new BoxException("达到最大尝试次数 " + attempt, e);
		// throw new RobotException("gave up after " + attempt + " attempts");
	}

	/**
	 * fix for JBPM-2864. If this exception is caused by StaleStateException,
	 * then we should retry.
	 */
	protected boolean isCausedByOperateFailure(Throwable throwable) {
		while (throwable != null) {
			 //某些异常进行重试操作
			 if (throwable instanceof StaleStateException) {
				 return true;
			 } else {
				 throwable = throwable.getCause();
			 }
			return true;
		}

		return false;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getDelayFactor() {
		return delayFactor;
	}

	public void setDelayFactor(long delayFactor) {
		this.delayFactor = delayFactor;
	}
}
