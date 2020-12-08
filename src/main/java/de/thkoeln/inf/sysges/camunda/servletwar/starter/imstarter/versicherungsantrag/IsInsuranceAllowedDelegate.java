package de.thkoeln.inf.sysges.camunda.servletwar.starter.imstarter.versicherungsantrag;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class IsInsuranceAllowedDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int ageRisk = (int) delegateExecution.getVariable("ageRisk");
        int bmiRisk = (int) delegateExecution.getVariable("bmiRisk");
        int historyRisk = (int) delegateExecution.getVariable("historyRisk");

        String insuranceMode;
        if (bmiRisk > 100 || historyRisk >= 100) {
            delegateExecution.setVariable("insuranceMode", "not insurable");
        } else if (ageRisk < 50 && bmiRisk < 50 && historyRisk < 50) {
            delegateExecution.setVariable("insuranceMode", "insurable");
        } else if ((ageRisk < 50 && (bmiRisk >= 50 && bmiRisk < 70) && historyRisk < 50) || ((ageRisk >= 50 && ageRisk < 70) && bmiRisk < 50 && historyRisk < 50)) {
            delegateExecution.setVariable("insuranceMode", "insurable");
        } else if ((ageRisk >= 50 && ageRisk < 70) && (bmiRisk >= 50 && bmiRisk < 70)) {
            delegateExecution.setVariable("insuranceMode", "manual test");
        } else if (historyRisk >= 50) {
            delegateExecution.setVariable("insuranceMode", "manual test");
        } else if (ageRisk >= 70 && historyRisk >= 25) {
            delegateExecution.setVariable("insuranceMode", "manual test");
        } else if (bmiRisk >= 50 && historyRisk >= 25) {
            delegateExecution.setVariable("insuranceMode", "manual test");
        } else {
            throw new IllegalArgumentException(String.format("No insurance policy for ageRisk = %d, bmiRisk = %d, historyRisk =  %d", ageRisk, bmiRisk, historyRisk));
        }
    }
}
