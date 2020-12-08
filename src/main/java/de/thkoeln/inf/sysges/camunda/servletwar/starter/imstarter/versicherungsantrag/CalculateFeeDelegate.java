package de.thkoeln.inf.sysges.camunda.servletwar.starter.imstarter.versicherungsantrag;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class CalculateFeeDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        int age = (int) delegateExecution.getVariable("age");
        int baseFee = Math.max(110, 10 * age);

        int riskFee;
        if (delegateExecution.hasVariable("pv_riskFee")) {
            riskFee = ((Long) delegateExecution.getVariable("pv_riskFee")).intValue();
        } else {
            riskFee = 0;
        }

        int fee = baseFee + riskFee;

        delegateExecution.setVariable("fee", fee);
    }
}
