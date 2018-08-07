package demo;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTask3 implements JavaDelegate, Serializable {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("ServiceTask3: " + execution.getVariable("var1"));
	}

}
