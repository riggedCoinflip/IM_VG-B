package de.thkoeln.inf.sysges.camunda.servletwar.starter.imstarter.versicherungsantrag;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 *  Start VG AG "Underwriter prüft Antrag auf Vollständigkeit" by "Kundenantrag erhalten" or "Überarbeitung erhalten"
 */
public class InstantiateReviceRequestStartProcessByMessageDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        Map<String, Object> processVariables = new HashMap();
        // fill the message; use new names
        processVariables.put("pv_forename", execution.getVariable("mForename"));
        processVariables.put("pv_name", execution.getVariable("mName"));
        processVariables.put("pv_Birthdate", execution.getVariable("mBirthdate"));
        processVariables.put("pv_sex", execution.getVariable("mSex"));
        processVariables.put("pv_height", execution.getVariable("mHeight"));
        processVariables.put("pv_weight", execution.getVariable("mWeight"));
        processVariables.put("pv_history", execution.getVariable("mHistory"));
        processVariables.put("pv_addressPostalCode", execution.getVariable("mAddressPostalCode"));
        processVariables.put("pv_addressCity", execution.getVariable("mAddressCity"));
        processVariables.put("pv_addressStreetName", execution.getVariable("mAddressStreetName"));
        processVariables.put("pv_addressStreetNumber", execution.getVariable("mAddressStreetNumber"));


        // set the correlation id to identify this in receiving process
        String correlationId = execution.getBusinessKey();
        if (correlationId == null) {            // if not set at process start
            correlationId = execution.getProcessInstanceId();
            execution.setProcessBusinessKey(correlationId);
        }
        processVariables.put("correlationId", correlationId);

        RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
        // correlate process with message name
        runtimeService.startProcessInstanceByMessage("reviceRequest", processVariables);
    }
}
