package com.blackbread.service.imp;

import java.sql.SQLException;

import com.blackbread.annotation.Transactional;
import com.blackbread.beans.InitBeans;
import com.blackbread.dao.TestDao;
import com.blackbread.dao.imp.TestDaoImpl;
import com.blackbread.emun.Propagation;
import com.blackbread.service.TestService;
import com.blackbread.service.UserService;

public class TestServiceImpl implements TestService {
	TestDao tDao = new TestDaoImpl();
	UserService us;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(String name) throws SQLException {
		us = (UserService) InitBeans.getInstance().getBean("userService");
		tDao.insert(name);
		us.insert("2222");

	}

}
