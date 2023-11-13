package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Proj implements Serializable {
    private static final long serialVersionUID = 1L;

    private String projName;
    private List<String> lifeCycleSteps;
    private List<String> effortCategories;
    private List<String> plans;
    private List<String> deliverables;
    private List<String> interruptions;
    private List<String> defectCategories;

    public Proj(String projName) {
        this.projName = projName;
        this.lifeCycleSteps = new ArrayList<>();
        this.effortCategories = new ArrayList<>();
        this.plans = new ArrayList<>();
        this.deliverables = new ArrayList<>();
        this.interruptions = new ArrayList<>();
        this.defectCategories = new ArrayList<>();
    }

    // Methods to add items to lists
    public void addLifeCycleStep(String step) {
        this.lifeCycleSteps.add(step);
    }

    public void addEffortCategory(String category) {
        this.effortCategories.add(category);
    }

    public void addPlan(String plan) {
        this.plans.add(plan);
    }

    public void addDeliverable(String deliverable) {
        this.deliverables.add(deliverable);
    }

    public void addInterruption(String interruption) {
        this.interruptions.add(interruption);
    }

    public void addDefectCategory(String category) {
        this.defectCategories.add(category);
    }

    // Getters
    public String getProjName() {
        return projName;
    }

    public List<String> getLifeCycleSteps() {
        return lifeCycleSteps;
    }

    public List<String> getEffortCategories() {
        return effortCategories;
    }

    public List<String> getPlans() {
        return plans;
    }

    public List<String> getDeliverables() {
        return deliverables;
    }

    public List<String> getInterruptions() {
        return interruptions;
    }

    public List<String> getDefectCategories() {
        return defectCategories;
    }

    // Setters
    public void setProjName(String projName) {
        this.projName = projName;
    }

    public void setLifeCycleSteps(List<String> lifeCycleSteps) {
        this.lifeCycleSteps = lifeCycleSteps;
    }

    public void setEffortCategories(List<String> effortCategories) {
        this.effortCategories = effortCategories;
    }

    public void setPlans(List<String> plans) {
        this.plans = plans;
    }

    public void setDeliverables(List<String> deliverables) {
        this.deliverables = deliverables;
    }

    public void setInterruptions(List<String> interruptions) {
        this.interruptions = interruptions;
    }

    public void setDefectCategories(List<String> defectCategories) {
        this.defectCategories = defectCategories;
    }
}
