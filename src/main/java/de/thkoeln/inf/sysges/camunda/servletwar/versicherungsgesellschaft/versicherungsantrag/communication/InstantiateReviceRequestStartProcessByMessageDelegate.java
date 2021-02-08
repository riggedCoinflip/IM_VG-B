package de.thkoeln.inf.sysges.camunda.servletwar.versicherungsgesellschaft.versicherungsantrag.communication;

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
        processVariables.put("pv_birthdate", execution.getVariable("mBirthdate"));
        processVariables.put("pv_sex", execution.getVariable("mSex"));
        processVariables.put("pv_height", execution.getVariable("mHeight"));
        processVariables.put("pv_weight", execution.getVariable("mWeight"));
        processVariables.put("pv_addressPostalCode", execution.getVariable("mAddressPostalCode"));
        processVariables.put("pv_addressCity", execution.getVariable("mAddressCity"));
        processVariables.put("pv_addressStreetName", execution.getVariable("mAddressStreetName"));
        processVariables.put("pv_addressStreetNumber", execution.getVariable("mAddressStreetNumber"));

        processVariables.put("pv_allergies", execution.getVariable("mAllergies"));
        processVariables.put("pv_lactoseIntolerance", execution.getVariable("mLactoseIntolerance"));
        processVariables.put("pv_neurodermatitis", execution.getVariable("mNeurodermatitis"));
        processVariables.put("pv_diabetes", execution.getVariable("mDiabetes"));
        processVariables.put("pv_chronicalLungDiseases", execution.getVariable("mChronicalLungDiseases"));
        processVariables.put("pv_osteoporosis", execution.getVariable("mOsteoporosis"));
        processVariables.put("pv_asthma", execution.getVariable("mAsthma"));
        processVariables.put("pv_paralysis", execution.getVariable("mParalysis"));
        processVariables.put("pv_multipleSclerosis", execution.getVariable("mMultipleSclerosis"));
        processVariables.put("pv_dementia", execution.getVariable("mDementia"));


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
