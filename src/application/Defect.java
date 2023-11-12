package application;

import java.io.Serializable;

public class Defect implements Serializable{

	private static final long serialVersionUID = 1L;
	private String defectName;
	//private ArrayList<String> defectSymptoms;
	//private ArrayList<String> defectResolutions;
	private String defectSymptoms;
	private String defectResolutions;
	private String stepWhenInjected;
	private String stepWhenRemoved;
	private String defectCategory;
	private String status;
	
	public Defect(String defectName, String injectName, String removeName, String category) {
		this.defectName = defectName;
		this.defectSymptoms = "";
		this.defectResolutions = "";
		this.stepWhenInjected = injectName;
		this.stepWhenRemoved = removeName;
		this.defectCategory = category;
		this.status = "Open";
	}
	
	//This method will display our defects in a readable way so we can display it on the view logs screen
	public String toString() {
		String retString = "Defect Name: " + this.defectName + " Inject Step: " + this.stepWhenInjected + " Remove Step: " + this.stepWhenRemoved + " Defect Category: " + this.defectCategory + " Status: " + this.status;
		String symptoms = "\nDefect Symptoms: \n" + this.defectSymptoms;
		String resolutions = "\nDefect Resolutions: \n" + this.defectResolutions;
		return retString + symptoms + resolutions;
	}
	
	//gets name of defect
	public String getDefectName() {
		return this.defectName;
	}
	
	//gets defect symptoms
	public String getDefectSymptoms(){
		return this.defectSymptoms;
	}
	
	//gets defect resolutions
	public String getDefectResolutions(){
		return this.defectResolutions;
	}
	
	//gets injection step
	public String getStepWhenInjected() {
		return this.stepWhenInjected;
	}
	
	//gets removal step
	public String getStepWhenRemoved() {
		return this.stepWhenRemoved;
	}
	
	//gets defect category
	public String getDefectCategory() {
		return this.defectCategory;
	}
	
	//gets the status of the defect
	public String getStatus() {
		return this.status;
	}
	
	//sets defect name
	public void setDefectName(String defectName) {
		this.defectName = defectName;
	}
	
	//sets symptoms
	public void setDefectSymptoms(String defectSymptoms){
		this.defectSymptoms = defectSymptoms;
	}
	
	//sets resolutions
	public void setDefectResolutions(String defectResolutions){
		this.defectResolutions = defectResolutions;
	}
	
	//sets injection step
	public void setStepWhenInjected(String stepWhenInjected) {
		this.stepWhenInjected = stepWhenInjected;
	}
	
	//sets removal step
	public void setStepWhenRemoved(String stepWhenRemoved) {
		this.stepWhenRemoved = stepWhenRemoved;
	}
	
	//sets defect category
	public void setDefectCategory(String defectCategory) {
		this.defectCategory = defectCategory;
	}
	
	//sets status
	public void setStatus() {
		if(this.status.equals("Closed")) {
			this.status = "Open";
		}
		else {
			this.status = "Closed";
		}
	}
}
