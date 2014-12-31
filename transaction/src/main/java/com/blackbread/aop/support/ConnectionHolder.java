package com.blackbread.aop.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionHolder {

	private static ThreadLocal<Stack<Connection>> connHolder = new ThreadLocal<Stack<Connection>>();

	public static Connection getConnetion() throws SQLException {
		Stack<Connection> stack = connHolder.get();
		if (stack == null) {
			stack = new Stack<Connection>();
			connHolder.set(stack);
		}
		Connection conn = null;
		if (stack.size() == 0) {
			conn = buildConnetion();
			connHolder.get().push(conn);
		} else {
			conn = stack.peek();
		}
		return conn;
	}

	public static Connection getNewConnetion() throws SQLException {
		Connection conn = buildConnetion();
		Stack<Connection> stack = connHolder.get();
		if (stack == null) {
			stack = new Stack<Connection>();
			connHolder.set(stack);
		}
		connHolder.get().push(conn);
		return conn;
	}

	public static Connection buildConnetion() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://114.215.125.112:3306/pay?useUnicode=true&characterEncoding=utf8&autoReconnect=true",
							"mapabc_pay", "mapabc_pay");
		} catch (ClassNotFoundException e) {
			throw new SQLException("数据库驱动异常");
		} catch (SQLException e) {
			throw e;
		}
		return conn;
	}

	public static void closeConnection() {
		if (connHolder.get() != null && connHolder.get().size() > 0) {
			Connection con = connHolder.get().pop();
			if (con != null) {
				connHolder.remove();
				try {
					con.close();
				} catch (SQLException e) {
					System.out.println("数据库链接关闭异常");
				}
			}
		}
	}
}
