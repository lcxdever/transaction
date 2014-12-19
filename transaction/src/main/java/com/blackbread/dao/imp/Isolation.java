package com.blackbread.dao.imp;

import java.sql.Connection;

public enum Isolation {
		DEFAULT(-1),
		READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
		READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
		REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
		SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

		private final int value;

		Isolation(int value) { this.value = value; }
		public int value() { return this.value; }
		

}
