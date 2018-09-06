package demo.servicetaskclass;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class MyServiceTaskClass implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		System.out.println("每10s输出一次是正常的");
	}

}
