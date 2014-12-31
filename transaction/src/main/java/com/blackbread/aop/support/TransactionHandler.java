package com.blackbread.aop.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.blackbread.annotation.Transactional;
import com.blackbread.emun.Propagation;
import com.blackbread.exception.MyException;

public class TransactionHandler implements MethodInterceptor {
	private Object target;

	public TransactionHandler(Object target) {
		super();
		this.target = target;
	}

	public Object intercept(Object arg0, Method method, Object[] args,
			MethodProxy times) throws Throwable {
		// method.g
		Annotation[] anns = method.getDeclaredAnnotations();
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
		TransactionalHolder.push(transactional);
		if (transactional.propagation() == Propagation.REQUIRED) {
			conn = ConnectionHolder.getConnetion();
		} else if (transactional.propagation() == Propagation.REQUIRES_NEW) {
			conn = ConnectionHolder.getNewConnetion();
		}
		conn.setAutoCommit(false);
		Object o = null;
		try {
			o = method.invoke(target, args);
			if (transactional.propagation() == Propagation.REQUIRES_NEW|| (transactional.propagation() == Propagation.REQUIRED&& TransactionalHolder.getSize() <2)) {
				conn.commit();
			}
		} catch (Exception e) {
//			if (transactional.propagation() == Propagation.REQUIRES_NEW|| (transactional.propagation() == Propagation.REQUIRED&& TransactionalHolder.getSize() <2)) {
			conn.rollback();
//			}
			System.out.println("事务处理中异常，事务回滚");
			throw e.getCause();
		} finally {
			if (transactional.propagation() == Propagation.REQUIRES_NEW|| (transactional.propagation() == Propagation.REQUIRED&& TransactionalHolder.getSize() <2)) {
				conn.close();
			}
			TransactionalHolder.pop();
		}
		return o;
	}
}
