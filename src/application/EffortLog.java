package application;

import java.sql.Date;
import java.sql.Time;

public class EffortLog {
    private int id;
    private Date date;
    private Time startTime;
    private Time stopTime;
    private double deltaTimeInSeconds; // Updated to double for representing seconds
    private int projectId;
    private int lifecycleStepId;
    private int effortCategoryId;
    private int planId;

    // Constructors
    public EffortLog() {}

    // Updated constructor to calculate deltaTimeInSeconds in seconds
    public EffortLog(int id, Date date, Time startTime, Time stopTime, int projectId, int lifecycleStepId, int effortCategoryId, int planId) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.projectId = projectId;
        this.lifecycleStepId = lifecycleStepId;
        this.effortCategoryId = effortCategoryId;
        this.planId = planId;
        this.deltaTimeInSeconds = calculateDeltaTimeInSeconds(startTime, stopTime);
    }

    // Calculate deltaTimeInSeconds
    private double calculateDeltaTimeInSeconds(Time startTime, Time stopTime) {
        long startTimeMillis = startTime.getTime();
        long stopTimeMillis = stopTime.getTime();
        return (stopTimeMillis - startTimeMillis) / 1000.0; // Convert to seconds
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
        // Recalculate deltaTimeInSeconds whenever startTime changes
        this.deltaTimeInSeconds = calculateDeltaTimeInSeconds(startTime, stopTime);
    }

    public Time getStopTime() {
        return stopTime;
    }

    public void setStopTime(Time stopTime) {
        this.stopTime = stopTime;
        // Recalculate deltaTimeInSeconds whenever stopTime changes
        this.deltaTimeInSeconds = calculateDeltaTimeInSeconds(startTime, stopTime);
    }

    public double getDeltaTimeInSeconds() {
        return deltaTimeInSeconds;
    }

    public void setDeltaTimeInSeconds(double deltaTimeInSeconds) {
        this.deltaTimeInSeconds = deltaTimeInSeconds;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getLifecycleStepId() {
        return lifecycleStepId;
    }

    public void setLifecycleStepId(int lifecycleStepId) {
        this.lifecycleStepId = lifecycleStepId;
    }

    public int getEffortCategoryId() {
        return effortCategoryId;
    }

    public void setEffortCategoryId(int effortCategoryId) {
        this.effortCategoryId = effortCategoryId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    // Additional methods, like toString(), equals(), hashCode(), etc., can be added as needed.
}
