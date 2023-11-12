package application;

import java.io.Serializable;

public class Log implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectName;
	private String lifeCycleStep;
	private String effortCategory;
	private String planType;
	private String startTime;
	private String endTime;
	
	public Log(String projectName, String lifeCycleStep, String effortCategory, String planType, String startTime, String endTime) {
		this.projectName = projectName;
		this.lifeCycleStep = lifeCycleStep;
		this.effortCategory = effortCategory;
		this.planType = planType;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public String getLifeCycleStep() {
		return this.lifeCycleStep;
	}
	
	public String getEffortCategory() {
		return this.effortCategory;
	}
	
	public String getPlanType() {
		return this.planType;
	}
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public void setLifeCycleStep(String lifeCycleStep) {
		this.lifeCycleStep = lifeCycleStep;
	}
	
	public void setEffortCategory(String effortCategory) {
		this.effortCategory = effortCategory;
	}
	
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	//this will be trickier to modify since it has the date and time in the string
	//public void setStartTime() {
		//this.startTime;
	//}
	//public void setEndTime() {
		//this.endTime;
	//}
	
	//toString method so we get display the logs in a readable way
	public String toString() {
		return "Start time: " + this.startTime + " End time: " + this.endTime + " Project: " + this.projectName + " LifeCycle: " + this.lifeCycleStep + " EffortCategory: " + this.effortCategory + " Plan: " + this.planType; 
	}
	

	public void setDate(String text) {
		// TODO Auto-generated method stub
		
	}
	public void setStopTime(String text) {
		// TODO Auto-generated method stub
		
	}
	public void setStartTime(String text) {
		// TODO Auto-generated method stub
		
	}
	public void setDetails(String text) {
		// TODO Auto-generated method stub
		
	}
	public String getDate() {
		// TODO Auto-generated method stub
		return null;
	}
}
