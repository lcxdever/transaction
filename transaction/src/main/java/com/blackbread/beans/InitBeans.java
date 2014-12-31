package com.blackbread.beans;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class InitBeans {
	private static Map<String, Object> beans = new HashMap<String, Object>();

	public Object getBean(String name) {
		return beans.get(name);
	}

	public static InitBeans getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		private static InitBeans instance = new InitBeans();
	}

	private InitBeans() {
		Map<String, String> mappring = new HashMap<String, String>();

//		Map<String, String> aopMap = new HashMap<String, String>();
		mappring.put("testService",
				"com.blackbread.service.imp.TestServiceImpl");
		mappring.put("userService",
				"com.blackbread.service.imp.UserServiceImpl");
//		aopMap.put("transaction",
//				"com.blackbread.aop.support.TransactionHandler");
//		aopMap.put("connectionManage",
//				"com.blackbread.aop.support.ConnectionHandler");
		Queue<String> queue=new LinkedList<String>();
		queue.add("com.blackbread.aop.support.TransactionHandler");
//		queue.add("com.blackbread.aop.support.ConnectionHandler");
		for (Iterator<Entry<String, String>> it = mappring.entrySet()
				.iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			Class<?> loadClass = null;
			Object o = null;
			try {
				loadClass = Class.forName(entry.getValue());
				o = loadClass.getConstructor().newInstance();
				for (Iterator<String> it2 = queue.iterator(); it2.hasNext();) {
					Class<?> aopClass = Class.forName(it2.next());
					MethodInterceptor o2 = (MethodInterceptor) aopClass
							.getConstructor(new Class[] { Object.class })
							.newInstance(o);

					Enhancer e = new Enhancer();
					e.setInterfaces(loadClass.getInterfaces());
					e.setSuperclass(loadClass);
					e.setCallback(o2);
					o = e.create();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			beans.put(entry.getKey(), o);
		}
		beans = Collections.unmodifiableMap(beans);

	}
}
