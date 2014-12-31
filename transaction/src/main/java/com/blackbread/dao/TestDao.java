package com.blackbread.dao;

import java.sql.SQLException;

import com.blackbread.exception.MyException;

public interface TestDao {
	public void insert(String name) throws SQLException;
}
