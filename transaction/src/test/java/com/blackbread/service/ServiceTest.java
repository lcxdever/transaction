package com.blackbread.service;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.blackbread.beans.InitBeans;
import com.blackbread.exception.MyException;

public class ServiceTest {

	InitBeans init;

	@Before
	public void init() {
		init = InitBeans.getInstance();
	}

	@Test
	public void insert() throws MyException {
		try {
			TestService proxy = (TestService) init.getBean("testService");

			proxy.insert("测试NAME");
			System.out.println("success");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
