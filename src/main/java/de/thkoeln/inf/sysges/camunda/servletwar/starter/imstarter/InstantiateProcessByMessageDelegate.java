package de.thkoeln.inf.sysges.camunda.servletwar.starter.imstarter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Send Task to start another process by mesaage
 * 
 */
public class InstantiateProcessByMessageDelegate implements JavaDelegate {
	 public void execute(DelegateExecution execution) {
		Map<String, Object> processVariables = new HashMap();
		// fill the message; use new names
		processVariables.put("mName", execution.getVariable("pv_name"));
		processVariables.put("mAge", execution.getVariable("pv_age"));
		
		// set the correlation id to identify this in receiving process
		String correlationId = execution.getBusinessKey();
		if (correlationId == null) {			// if not set at process start
			correlationId = execution.getProcessInstanceId();
			execution.setProcessBusinessKey(correlationId);
		}
		processVariables.put("correlationId", correlationId);

		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		// correlate process with message name
		runtimeService.startProcessInstanceByMessage("instantiationMessage", processVariables);
	  }
}
