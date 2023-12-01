package application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import org.json.JSONObject;  // Import the org.json package
import javafx.application.Platform;



public class RealtimeClient {
	
	private PlanningPokerPageViewController controller;
	
    private final String webSocketURL = "ws://localhost:8080"; // URL of your Node.js WebSocket server
    
    

    public RealtimeClient(PlanningPokerPageViewController controller) {
        this.controller = controller;
        startWebSocketClient();
    }


    private void startWebSocketClient() {
        HttpClient client = HttpClient.newHttpClient();
        WebSocket.Builder builder = client.newWebSocketBuilder();

        builder.buildAsync(URI.create(webSocketURL), new WebSocket.Listener() {
            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                String message = data.toString();
                JSONObject json = new JSONObject(message);
                if (json.getBoolean("update")) {
                    Platform.runLater(() -> {
                        if (controller != null) {
                            controller.populateUserStories();
                        }
                    });
                }
                return null;
            }
            
            
            // Implement other necessary WebSocket listener methods
            
        });
    }
    
    

    private void processMessage(String message) {
        // Parse the JSON message and print its contents
        try {
            JSONObject jsonObject = new JSONObject(message);
            System.out.println("Received change: " + jsonObject.toString(4)); // Pretty print JSON
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
    
    
}
