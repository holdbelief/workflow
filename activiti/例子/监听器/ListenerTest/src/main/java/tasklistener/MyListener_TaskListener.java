package tasklistener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyListener_TaskListener implements  TaskListener{


	@Override
	public void notify(DelegateTask delegateTask) {
		Object o  = delegateTask.getVariable("var");
		System.out.println(o);
		
	}


}
