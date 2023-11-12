package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<User> users;
	
	public UserList() {
		users = new ArrayList<User>();
	}
	public void addUser(User user) {
		users.add(user);
	}
	
	public ArrayList<User> getUserList(){
		return users;
	}
	
	public void updateUser(User user) {
		int foundUserIndex = findUserIndex(user.getUserName());
		users.set(foundUserIndex, user);
		
		
	}
	public User findUser(String userName) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUserName().equals(userName)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	public void printUsers() {
		for(int i =0; i< users.size();i++) {
			System.out.println(users.get(i).getUserName());
		}
	}
	
	public void writeToFile(byte[] serializedUsers) {
		try (FileOutputStream fos = new FileOutputStream("user" + ".ser")) {
            fos.write(serializedUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public int findUserIndex(String userName) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUserName().equals(userName)) {
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
	
	public static UserList deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        UserList userList = null;
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            userList = (UserList) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
