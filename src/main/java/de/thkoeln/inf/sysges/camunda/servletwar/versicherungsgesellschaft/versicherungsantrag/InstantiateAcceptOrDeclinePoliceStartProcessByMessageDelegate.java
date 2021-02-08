package de.thkoeln.inf.sysges.camunda.servletwar.versicherungsgesellschaft.versicherungsantrag;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 *  Start VG AG "Underwriter prüft Antrag auf Vollständigkeit" by "Kundenantrag erhalten" or "Überarbeitung erhalten"
 */
public class InstantiateAcceptOrDeclinePoliceStartProcessByMessageDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        Map<String, Object> processVariables = new HashMap();
        // fill the message; use new names
        processVariables.put("mFee", execution.getVariable("pv_fee"));


        // set the correlation id to identify this in receiving process
        String correlationId = execution.getBusinessKey();
        if (correlationId == null) {            // if not set at process start
            correlationId = execution.getProcessInstanceId();
            execution.setProcessBusinessKey(correlationId);
        }
        processVariables.put("correlationId", correlationId);

        RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
        // correlate process with message name
        runtimeService.startProcessInstanceByMessage("acceptOrDeclinePolice", processVariables);
    }
}
