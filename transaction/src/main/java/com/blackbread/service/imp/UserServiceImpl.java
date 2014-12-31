package com.blackbread.service.imp;

import java.sql.SQLException;

import com.blackbread.annotation.Transactional;
import com.blackbread.dao.TestDao;
import com.blackbread.dao.imp.TestDaoImpl;
import com.blackbread.emun.Propagation;
import com.blackbread.service.UserService;

public class UserServiceImpl implements UserService {
	TestDao tDao = new TestDaoImpl();

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(String name) throws SQLException {
		tDao.insert(name);
	}
}
