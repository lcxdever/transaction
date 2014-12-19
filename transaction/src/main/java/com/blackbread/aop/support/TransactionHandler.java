package com.blackbread.aop.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.blackbread.annotation.Transactional;

public class TransactionHandler implements MethodInterceptor {
	private Object target;

	public TransactionHandler(Object target) {
		super();
		this.target = target;
	}

	public Object intercept(Object arg0, Method method, Object[] args,
			MethodProxy times) throws Throwable {
		Annotation[] anns = method.getAnnotations();
		Transactional transactional = null;
		for (Annotation annotation : anns) {
			if (annotation instanceof Transactional) {
				transactional = (Transactional) annotation;
				break;
			}
		}
		Object o = null;
		if (transactional != null)
			o = doTrans(method, args, transactional);
		else
			o = method.invoke(target, args);
		return o;
	}

	private Object doTrans(Method method, Object[] args,
			Transactional transactional) throws Throwable {
		Connection conn = null;
		if (transactional.propagation().value() == 0) {
			conn = ConnectionHolder.getConnetion();
		} else if (transactional.propagation().value() == 3) {
			conn = ConnectionHolder.getNewConnetion();
		}
		conn.setAutoCommit(false);
		Object o = null;
		try {
			o = method.invoke(target, args);
			conn.commit();
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			System.out.println("事务处理中异常，事务回滚");
			throw e;
		} finally {
			ConnectionHolder.closeConnection();
		}
		return o;
	}
}
