package com.blackbread.aop.support;

import com.blackbread.annotation.Transactional;
import com.blackbread.emun.Isolation;
import com.blackbread.emun.Propagation;

public class TransactionAttributes {
	private Transactional transactional;
	private int level;
	
	public Transactional getTransactional() {
		return transactional;
	}

	public void setTransactional(Transactional transactional) {
		this.transactional = transactional;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
