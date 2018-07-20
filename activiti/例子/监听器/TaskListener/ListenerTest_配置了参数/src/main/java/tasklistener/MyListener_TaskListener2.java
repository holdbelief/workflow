package tasklistener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyListener_TaskListener2 implements TaskListener{


	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println(delegateTask.getVariable("var"));
		
	}	



}
