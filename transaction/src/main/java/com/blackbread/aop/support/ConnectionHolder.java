package com.blackbread.aop.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionHolder {

	private static ThreadLocal<Stack<Connection>> connHolder = new ThreadLocal<Stack<Connection>>();

	public static Connection getConnetion() throws SQLException {
		Connection conn = connHolder.get().peek();
		if (conn == null) {
			conn = buildConnetion();
			connHolder.get().push(conn);
		}
		return conn;
	}

	public static Connection getNewConnetion() throws SQLException {
		Connection conn = buildConnetion();
		connHolder.get().push(conn);
		return conn;
	}

	public static Connection buildConnetion() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("数据库驱动");
			conn = DriverManager.getConnection("", "", "");
		} catch (ClassNotFoundException e) {
			throw new SQLException("数据库驱动异常");
		} catch (SQLException e) {
			throw e;
		}
		return conn;
	}

	public static void closeConnection() {
		Connection conn = connHolder.get().pop();
		if (conn != null) {
			connHolder.remove();
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("数据库链接关闭异常");
			}
		}
	}
}
