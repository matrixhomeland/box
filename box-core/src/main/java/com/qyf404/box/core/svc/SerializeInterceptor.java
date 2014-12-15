/*
 * 
 */
package com.qyf404.box.core.svc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qyf404.box.core.BoxException;
import com.qyf404.box.core.cmd.Command;

/**
 * 一个对象被序列化，然后再被反序列化取出，从而优雅的在内存中克隆了一个对象。
 * 然后使用这个副本对象执行命令，返回的结果同样进行序列化克隆。
 * 
 * @author qyf404@gmail.com
 * @since 2014年12月3日
 */
public class SerializeInterceptor extends Interceptor {
	private static final Logger log = LoggerFactory
			.getLogger(SerializeInterceptor.class);

	@SuppressWarnings("unchecked")
	public <T> T execute(Command<T> command) {
		log.info("serializing command " + command);
		Command<T> serializedCommand = (Command<T>) serialize(command);
		T returnValue = next.execute(serializedCommand);
		return (T) serialize(returnValue);
	}

	private static Object serialize(Object o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.close();

			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException e) {
			throw new BoxException("serialization exception", e);
		} catch (ClassNotFoundException e) {
			// should not happen, class is already loaded
			throw new AssertionError(e);
		}
	}
}
