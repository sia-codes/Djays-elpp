package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project_Legacy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectName;
	private ArrayList<Log> logs;
	private ArrayList<Defect> defects;
	
	public Project_Legacy(String projectName) {
		this.projectName = projectName;
		this.logs = new ArrayList<Log>();
		this.defects = new ArrayList<Defect>();
	}
	
	public String getName() {
		return this.projectName;
	}
	
	public ArrayList<Log> getLogs() {
		return this.logs;
	}
	
	public ArrayList<Defect> getDefects(){
		return this.defects;
	}
	
	public Defect findDefect(String defectName) {
		for(int i = 0; i < this.defects.size(); i++) {
			if(this.defects.get(i).getDefectName().equals(defectName)) {
				return this.defects.get(i);
			}
		}
		return null;
	}
	
	public int getDefectIndex(Defect defect) {
		for(int i = 0; i < this.defects.size(); i++) {
			if(defect == this.defects.get(i)) {
				return i;
			}
		}
		return -1;
	}
	
	public void addLog(Log log) {
		this.logs.add(log);
	}
	
	public void addDefect(Defect defect) {
		this.defects.add(defect);
	}
	
	public boolean isDefect(String defectName) {
		for(int i = 0; i < this.defects.size();i++) {
			if(this.defects.get(i).getDefectName().equals(defectName)) {
				return true;
			}
		}
		return false;
	}
	
	public void removeDefect(Defect defect) {
		int index = getDefectIndex(defect);
		this.defects.remove(index);
	}
	
	public List<String> getDefectNames(){
		List<String> names = new ArrayList<String>();
		for(int i =0; i< this.defects.size();i++) {
			names.add(this.defects.get(i).getDefectName());
		}
		return names;
	}
}
