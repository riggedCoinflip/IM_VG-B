package de.thkoeln.inf.sysges.camunda.servletwar.starter.imstarter.tutorial;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;

public class CheckWeatherDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Random random = new Random();

        delegateExecution.setVariable("name", "myname");
        delegateExecution.setVariable("weatherOk", random.nextBoolean());
    }
}
