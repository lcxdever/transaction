package com.blackbread.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.blackbread.aop.support.ConnectionHolder;
import com.blackbread.dao.TestDao;

public class TestDaoImpl implements TestDao {

	public void insert(String name) throws SQLException {
		if (name.equals("2222")) {
			throw new SQLException("姓名异常");
		}
		Connection conn = ConnectionHolder.getConnetion();
		PreparedStatement ps = conn
				.prepareStatement("insert into test (f_name) values(?)");
		ps.setString(1, name);
		ps.executeUpdate();
		System.out.println("插入：" + name);
	}

}
