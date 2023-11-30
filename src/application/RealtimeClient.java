package application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import org.json.JSONObject;  // Import the org.json package

public class RealtimeClient {

    private final String webSocketURL = "ws://localhost:8080"; // URL of your Node.js WebSocket server

    public RealtimeClient() {
        startWebSocketClient();
    }

    private void startWebSocketClient() {
        HttpClient client = HttpClient.newHttpClient();
        WebSocket.Builder builder = client.newWebSocketBuilder();

        builder.buildAsync(URI.create(webSocketURL), new WebSocket.Listener() {
            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                String message = data.toString();
                // Process the received message
                processMessage(message);
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
