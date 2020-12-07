package de.thkoeln.inf.sysges.camunda.servletwar.starter.imstarter.versicherungsantrag;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class CalculateRiskDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LocalDate birthdate = (LocalDate) delegateExecution.getVariable("pv_birthdate");
        int age = calculateAge(birthdate);
        delegateExecution.setVariable("age", age);

        int height = (int) delegateExecution.getVariable("pv_height");
        int weight = (int) delegateExecution.getVariable("pv_weight");
        float bmi = calculateBmi(height, weight);
        delegateExecution.setVariable("bmi", bmi);

        int highestHistoryCategory = (int) delegateExecution.getVariable("pv_riskHistory");

        delegateExecution.setVariable("ageRisk", ageRisk(age));
        delegateExecution.setVariable("bmiRisk", bmiRisk(bmi));
        delegateExecution.setVariable("historyRisk", historyRisk(highestHistoryCategory));
    }

    public int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    public int ageRisk(int age) throws IllegalArgumentException {
        if (age < 0) {
            throw new IllegalArgumentException("age = " + age + " can not be less than 0");
        } else if (age <= 40) {
            return 0;
        } else if (age <= 55){
            return 20;
        } else if (age <= 60){
            return 50;
        } else if (age <= 70){
            return 70;
        } else if (age <= 120){
            return 100;
        } else {
            throw new IllegalArgumentException("age = " + age + " can not be higher than 120");
        }
    }

    public float calculateBmi(int height, int weight) {
        return (float) (weight / Math.pow(((float) height / 100), 2));
    }

    public int bmiRisk(float bmi) throws IllegalArgumentException {
        if (bmi < 0) {
            throw new IllegalArgumentException("bmi = " + bmi + " can not be less than 0");
        } else if (bmi <= 20) {
            return 50;
        } else if (bmi <= 26){
            return 0;
        } else if (bmi <= 29){
            return 50;
        } else if (bmi <= 32){
            return 70;
        } else if (bmi <= 35){
            return 100;
        } else {
           return 1000;
        }
    }

    public int historyRisk(int highestHistoryCategory) throws IllegalArgumentException {
        switch (highestHistoryCategory){
            case 0:
                return 0;
            case 1:
                return 10;
            case 2:
                return 50;
            case 3:
                return 100;
            default:
                throw new IllegalArgumentException("highestHistoryCategory = " + highestHistoryCategory + " is not in range [0..3]");
        }
    }
}
