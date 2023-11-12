package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SessionList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Session> sessions;
	
	public SessionList() {
		sessions = new ArrayList<Session>();
	}
	public void addSession(Session session) {
		sessions.add(session);
	}
	
	public ArrayList<Session> getSessionList(){
		return sessions;
	}
	
	public void updateSession(Session session) {
		int foundSessionIndex = findSessionIndex(session.getSessionName());
		sessions.set(foundSessionIndex, session);
		
		
	}
	public Session findSession(String sessionName) {
		for(int i = 0; i < sessions.size(); i++) {
			if(sessions.get(i).getSessionName().equals(sessionName)) {
				return sessions.get(i);
			}
		}
		return null;
	}
	
	public void printSessions() {
		for(int i =0; i< sessions.size();i++) {
			System.out.println(sessions.get(i).getSessionName());
		}
	}
	
	public void writeToFile(byte[] serializedSessions) {
		try (FileOutputStream fos = new FileOutputStream("session" + ".ser")) {
            fos.write(serializedSessions);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public int findSessionIndex(String sessionName) {
		for(int i = 0; i < sessions.size(); i++) {
			if(sessions.get(i).getSessionName().equals(sessionName)) {
				return i;
			}
		}
		return -1;
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
	
	public static SessionList deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        SessionList sessionList = null;
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            sessionList = (SessionList) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sessionList;
    }
}
