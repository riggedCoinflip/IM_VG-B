package de.thkoeln.inf.sysges.camunda.servletwar.versicherungsgesellschaft.versicherungsantrag;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class IsInsuranceAllowedDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int pv_ageRisk = (int) delegateExecution.getVariable("pv_ageRisk");
        int pv_bmiRisk = (int) delegateExecution.getVariable("pv_bmiRisk");
        int pv_historyRisk = (int) delegateExecution.getVariable("pv_historyRisk");

        if (pv_bmiRisk >= 100 || pv_historyRisk >= 100) {
            delegateExecution.setVariable("pv_isInsurable", false);
            delegateExecution.setVariable("pv_manualCheck", false);
        } else if ((pv_ageRisk < 50 && pv_bmiRisk < 50 && pv_historyRisk < 50) ||
                ((pv_ageRisk < 50 && (pv_bmiRisk >= 50 && pv_bmiRisk < 70) && pv_historyRisk < 50)) ||
                ((pv_ageRisk >= 50 && pv_ageRisk < 70) && pv_bmiRisk < 50 && pv_historyRisk < 50)) {
            delegateExecution.setVariable("pv_isInsurable", true);
            delegateExecution.setVariable("pv_manualCheck", false);
        } else if (((pv_ageRisk >= 50 && pv_ageRisk < 70) && (pv_bmiRisk >= 50 && pv_bmiRisk < 70)) ||
                (pv_historyRisk >= 50) ||
                (pv_ageRisk >= 70 && pv_historyRisk >= 25) ||
                (pv_bmiRisk >= 50 && pv_historyRisk >= 25)) {
            delegateExecution.setVariable("pv_manualCheck", true);
        } else {
            throw new IllegalArgumentException(String.format("No insurance policy for ageRisk = %d, bmiRisk = %d, historyRisk =  %d", pv_ageRisk, pv_bmiRisk, pv_historyRisk));
        }
    }
}
