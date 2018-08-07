package demo;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTask2 implements JavaDelegate, Serializable {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("ServiceTask2: " + execution.getVariable("var1"));
	}

	public void test() {
		System.out.println("ServiceTask2: Test");
	}
}
