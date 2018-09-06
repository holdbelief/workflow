package demo.servicetaskclass;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class MyServiceTask implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("模拟进行自动退件操作");

	}

}
