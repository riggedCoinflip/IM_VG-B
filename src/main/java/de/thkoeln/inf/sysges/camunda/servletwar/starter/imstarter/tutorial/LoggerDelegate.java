package de.thkoeln.inf.sysges.camunda.servletwar.starter.imstarter.tutorial;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class LoggerDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
                + "processDefinitionId=" + delegateExecution.getProcessDefinitionId()
                + ", activityId=" + delegateExecution.getCurrentActivityId()
                + ", activityName='" + delegateExecution.getCurrentActivityName().replace("\n", " ") + "'"
                + ", processInstanceId=" + delegateExecution.getProcessInstanceId()
                + ", businessKey=" + delegateExecution.getProcessBusinessKey()
                + ", executionId=" + delegateExecution.getId()
                + ", modelName=" + delegateExecution.getBpmnModelInstance().getModel().getModelName()
                + ", elementId" + delegateExecution.getBpmnModelElementInstance().getId()
                + " \n\n");

    }
}
