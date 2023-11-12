package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ViewLogsController extends Controller{
	
	private ArrayList<Project> userProjects;
	private ArrayList<Log> userLogs;
	private ArrayList<Defect> userDefects;
	private StringBuilder fieldContent = new StringBuilder("");
	@FXML
	private TextArea viewLogsField;
	
	//Displays all of the logs for all of the projects
	@FXML
	public void showLogs() {
		viewLogsField.setText("");
		fieldContent.setLength(0);
		userProjects = Main.currentUser.getProjects();
		for(int i = 0; i < userProjects.size(); i++) {
			fieldContent.append(userProjects.get(i).getName() + "\n");
			userLogs = userProjects.get(i).getLogs();
			if(userLogs.size() == 0) {
				fieldContent.append("N/A\n");
			}
			for(int j = 0; j < userLogs.size(); j++) {
				fieldContent.append(userLogs.get(j).toString() + "\n");
			}
		}
		viewLogsField.setText(fieldContent.toString());
	}
	
	//Displays all of the defects for all of the projects
	@FXML
	public void showDefects() {
		viewLogsField.setText("");
		fieldContent.setLength(0);
		userProjects = Main.currentUser.getProjects();
		for(int i = 0; i < userProjects.size(); i++) {
			fieldContent.append(userProjects.get(i).getName() + "\n");
			userDefects = userProjects.get(i).getDefects();
			if(userDefects.size() == 0) {
				fieldContent.append("N/A\n");
			}
			for(int j = 0; j < userDefects.size(); j++) {
				fieldContent.append(userDefects.get(j).toString() + "\n");
			}
		}
		viewLogsField.setText(fieldContent.toString());
	}
}
