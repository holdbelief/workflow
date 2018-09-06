package tasklistener;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

public class MyListener_TaskListener3 implements TaskListener, Serializable{


	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println(delegateTask.getVariable("var"));
		
	}	

	public void back() {
		System.out.println("back");
	}

}
