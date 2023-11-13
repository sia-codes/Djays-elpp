package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProjList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Proj> projects;

    public ProjList() {
        projects = new ArrayList<>();
    }

    public void addProj(Proj project) {
        projects.add(project);
    }

    public boolean removeProj(String projName) {
        return projects.removeIf(p -> p.getProjName().equals(projName));
    }

    public Proj findProj(String projName) {
        for (Proj project : projects) {
            if (project.getProjName().equals(projName)) {
                return project;
            }
        }
        return null;
    }

    public List<Proj> getProjs() {
        return new ArrayList<>(projects);
    }

    public void serializeToFile(String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ProjList deserializeFromFile(String filename) {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (ProjList) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
