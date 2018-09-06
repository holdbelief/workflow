package com.example.demo;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTask1 implements JavaDelegate, Serializable {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("ServiceTask1: " + execution.getVariable("var1"));
	}

}
