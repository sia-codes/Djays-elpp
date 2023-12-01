package application;

public class UserSession {
    private String id;
    private long userId;
    private long sessionId;
    private String selectedCard;

    public UserSession(long userId, long sessionId, String selectedCard) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.selectedCard = selectedCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(String selectedCard) {
        this.selectedCard = selectedCard;
    }

}