package com.blackbread.aop.support;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ConnectionHandler implements MethodInterceptor {
	private Object target;

	public ConnectionHandler(Object target) {
		super();
		this.target = target;
	}

	public Object intercept(Object arg0, Method method, Object[] args,
			MethodProxy times) throws Throwable {
		Object o;
		try {
			o = method.invoke(target, args);
		} finally {
			ConnectionHolder.closeConnection();
		}
		return o;
	}

}
