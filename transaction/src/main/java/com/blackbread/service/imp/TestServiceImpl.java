package com.blackbread.service.imp;

import java.sql.Connection;

import com.blackbread.annotation.Transactional;
import com.blackbread.emun.Propagation;
import com.blackbread.service.TestService;

public class TestServiceImpl implements TestService {
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert() {
		
//		Connection con=null;
//		con.setTransactionIsolation(Connection.TRANSACTION_NONE);
	}

}
