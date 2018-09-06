package com.example.demo;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTask2 implements JavaDelegate, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3796994236044620081L;

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("ServiceTask2: " + execution.getVariable("var1"));
	}

	public void test() {
		System.out.println("ServiceTask2: Test");
	}
}
