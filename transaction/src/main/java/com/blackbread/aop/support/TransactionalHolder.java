package com.blackbread.aop.support;

import java.util.Stack;

import com.blackbread.annotation.Transactional;

public class TransactionalHolder {
	private static ThreadLocal<Stack<Transactional>> transHolder = new ThreadLocal<Stack<Transactional>>();

	// private static ThreadLocal<AtomicInteger> level = new
	// ThreadLocal<AtomicInteger>();

	public static Transactional pop() {
		Stack<Transactional> stack = transHolder.get();
		if (stack != null && !stack.isEmpty())
			return stack.pop();
		else
			return null;
	}

	public static int getSize() {
		Stack<Transactional> stack = transHolder.get();
		if (stack != null)
			return stack.size();
		else
			return 0;
	}

	public static void push(Transactional transactional) {
		Stack<Transactional> stack = transHolder.get();
		if (stack == null) {
			stack = new Stack<Transactional>();
			transHolder.set(stack);
		}
		stack.push(transactional);
	}
}
