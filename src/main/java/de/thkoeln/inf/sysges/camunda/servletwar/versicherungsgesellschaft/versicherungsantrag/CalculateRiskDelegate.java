package de.thkoeln.inf.sysges.camunda.servletwar.versicherungsgesellschaft.versicherungsantrag;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalculateRiskDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        /*
        We use LocalDate instead of Date as LocalDate has a 'now()' function.
        Date is not typecastable to LocalDate so we use a method instead
         */

        // Age
        LocalDate mBirthdate = convertToLocalDateViaInstant((Date) delegateExecution.getVariable("mBirthdate"));
        int pv_age = calculateAge(mBirthdate);
        delegateExecution.setVariable("pv_age", pv_age);

        // BMI
        int pv_height = ((Long) delegateExecution.getVariable("mHeight")).intValue();
        int pv_weight = ((Long) delegateExecution.getVariable("mWeight")).intValue();
        int pv_bmi = (int) calculateBmi(pv_height, pv_weight);
        delegateExecution.setVariable("pv_bmi", pv_bmi);

        // Risk
        int pv_history = calculateHistory(delegateExecution);
        delegateExecution.setVariable("pv_history", pv_history);



        delegateExecution.setVariable("pv_ageRisk", ageRisk(pv_age));
        delegateExecution.setVariable("pv_bmiRisk", bmiRisk(pv_bmi));
        delegateExecution.setVariable("pv_historyRisk", historyRisk(pv_history));
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
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
        } else { //bmi > 32
           return 100;
        }
    }

    public int calculateHistory(DelegateExecution delegateExecution) {
        //ugly solution, a mapping would be better. But it works.

        if ((boolean) delegateExecution.getVariable("mDementia") ||
                (boolean) delegateExecution.getVariable("mParalysis") ||
                (boolean) delegateExecution.getVariable("mMultipleSclerosis")) {
            return 3;
        }
        else if ((boolean) delegateExecution.getVariable("mDiabetes") ||
                (boolean) delegateExecution.getVariable("mChronicalLungDiseases") ||
                (boolean) delegateExecution.getVariable("mAsthma") ||
                (boolean) delegateExecution.getVariable("mOsteoporosis")) {
            return 2;
        }
        else if ((boolean) delegateExecution.getVariable("mAllergies") ||
                (boolean) delegateExecution.getVariable("mLactoseIntolerance") ||
                (boolean) delegateExecution.getVariable("mNeurodermatitis")) {
            return 1;
        }
        else {
            return 0;
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
