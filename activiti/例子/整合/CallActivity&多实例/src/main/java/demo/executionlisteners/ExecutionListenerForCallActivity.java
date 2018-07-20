package demo.executionlisteners;

import java.util.Calendar;
import java.util.TimeZone;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ExecutionListenerForCallActivity implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) {
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		Calendar now = Calendar.getInstance(tz);
		now.add(Calendar.SECOND, 30);
		execution.setVariable("timeDate", now.getTime());
		System.out.println("timeDate：" + now.getTime());
	}

}
