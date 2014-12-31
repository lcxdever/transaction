package com.blackbread.service;

import java.sql.SQLException;

import com.blackbread.exception.MyException;

public interface TestService {
	public void insert(String name) throws SQLException, MyException;
}
