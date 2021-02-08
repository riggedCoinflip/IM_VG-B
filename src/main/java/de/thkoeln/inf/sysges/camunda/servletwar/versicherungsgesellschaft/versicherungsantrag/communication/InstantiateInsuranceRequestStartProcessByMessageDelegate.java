package de.thkoeln.inf.sysges.camunda.servletwar.versicherungsgesellschaft.versicherungsantrag.communication;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 *  Start VG AG "Underwriter prüft Antrag auf Vollständigkeit" by "Kundenantrag erhalten" or "Überarbeitung erhalten"
 */
public class InstantiateInsuranceRequestStartProcessByMessageDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        Map<String, Object> processVariables = new HashMap();
        // fill the message; use new names
        processVariables.put("mForename", execution.getVariable("pv_forename"));
        processVariables.put("mName", execution.getVariable("pv_name"));
        processVariables.put("mBirthdate", execution.getVariable("pv_birthdate"));
        processVariables.put("mSex", execution.getVariable("pv_sex"));
        processVariables.put("mHeight", execution.getVariable("pv_height"));
        processVariables.put("mWeight", execution.getVariable("pv_weight"));
        processVariables.put("mAddressPostalCode", execution.getVariable("pv_addressPostalCode"));
        processVariables.put("mAddressCity", execution.getVariable("pv_addressCity"));
        processVariables.put("mAddressStreetName", execution.getVariable("pv_addressStreetName"));
        processVariables.put("mAddressStreetNumber", execution.getVariable("pv_addressStreetNumber"));

        processVariables.put("mAllergies", execution.getVariable("pv_allergies"));
        processVariables.put("mLactoseIntolerance", execution.getVariable("pv_lactoseIntolerance"));
        processVariables.put("mNeurodermatitis", execution.getVariable("pv_neurodermatitis"));
        processVariables.put("mDiabetes", execution.getVariable("pv_diabetes"));
        processVariables.put("mChronicalLungDiseases", execution.getVariable("pv_chronicalLungDiseases"));
        processVariables.put("mOsteoporosis", execution.getVariable("pv_osteoporosis"));
        processVariables.put("mAsthma", execution.getVariable("pv_asthma"));
        processVariables.put("mParalysis", execution.getVariable("pv_paralysis"));
        processVariables.put("mMultipleSclerosis", execution.getVariable("pv_multipleSclerosis"));
        processVariables.put("mDementia", execution.getVariable("pv_dementia"));




        // set the correlation id to identify this in receiving process
        String correlationId = execution.getBusinessKey();
        if (correlationId == null) {            // if not set at process start
            correlationId = execution.getProcessInstanceId();
            execution.setProcessBusinessKey(correlationId);
        }
        processVariables.put("correlationId", correlationId);

        RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
        // correlate process with message name
        runtimeService.startProcessInstanceByMessage("insuranceRequest", processVariables);
    }
}
