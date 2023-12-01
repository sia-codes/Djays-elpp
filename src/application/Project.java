package application;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private List<String> lifecycles;
    private List<String> deliverables;
    private List<String> plans;
    private List<String> effortCategories;
    private List<String> interruptions;
    private List<String> defectCategories;
    private List<String> userStories;

    // Constructor
    public Project(int id, String name) {
        this.id = id;
        this.name = name;
        this.lifecycles = new ArrayList<>();
        this.deliverables = new ArrayList<>();
        this.plans = new ArrayList<>();
        this.effortCategories = new ArrayList<>();
        this.interruptions = new ArrayList<>();
        this.defectCategories = new ArrayList<>();
        this.userStories = new ArrayList<>();
    }

    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLifecycles() {
        return lifecycles;
    }

    public void setLifecycles(List<String> lifecycles) {
        this.lifecycles = lifecycles;
    }

    public List<String> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(List<String> deliverables) {
        this.deliverables = deliverables;
    }

    public List<String> getPlans() {
        return plans;
    }

    public void setPlans(List<String> plans) {
        this.plans = plans;
    }

    public List<String> getEffortCategories() {
        return effortCategories;
    }

    public void setEffortCategories(List<String> effortCategories) {
        this.effortCategories = effortCategories;
    }

    public List<String> getInterruptions() {
        return interruptions;
    }

    public void setInterruptions(List<String> interruptions) {
        this.interruptions = interruptions;
    }

    public List<String> getDefectCategories() {
        return defectCategories;
    }

    public void setDefectCategories(List<String> defectCategories) {
        this.defectCategories = defectCategories;
    }

    public List<String> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<String> userStories) {
        this.userStories = userStories;
    }

    // Additional methods as needed
    // Here you can add any other functionality required by your application
}
