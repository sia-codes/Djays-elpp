package application;

public class Session {
    private String id;
    private String name;
    private String pin;
    private String status;

    public Session(String name, String pin) {
        this.name = name;
        this.pin = pin;
        this.status = "open";
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}