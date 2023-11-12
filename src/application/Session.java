package application;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Session implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sessionName;
	private String password;
	private ArrayList<Project> projects;
	
	public Session(String sessionName, String password) {
		this.sessionName = sessionName;
		this.password = password;
		projects = new ArrayList<Project>();
	}
	
	public String getSessionName() {
		return this.sessionName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Project getProject(String projectName) {
		for(int i =0; i<projects.size();i++){
			if(projects.get(i).getName().equals(projectName)) {
				return projects.get(i);
			}
		}
		return null;
		
	}
	
	public ArrayList<Project> getProjects(){
		return this.projects;
	}
	
	public void createProject(String projectName) {
		Project newProject = new Project(projectName);
		projects.add(newProject);
	}
	
	public Project getSingleProject(String projectName) {
		for(int i = 0; i < projects.size(); i++) {
			if(projects.get(i).getName().equals(projectName)) {
				return projects.get(i);
			}
		}
		return null;
	}
	public List<String> getProjectNames(){
		List<String> names = new ArrayList<String>();
		for(int i =0; i< projects.size();i++) {
			names.add(projects.get(i).getName());
		}
		return names;
	}
	
	
	public void printProjectNames(){
		for(int i =0; i< projects.size();i++) {
			System.out.println(projects.get(i).getName());
		}
	}
	
	
	public byte[] serialize() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
	
	public static Session deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        Session session = null;
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            session = (Session) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return session;
    }
}
