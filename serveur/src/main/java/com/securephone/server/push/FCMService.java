package com.securephone.server.push;

import com.securephone.server.database.DeviceTokenDAO;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FCMService {
    
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    private static String API_KEY = ""; 
    
    private final DeviceTokenDAO deviceTokenDAO;
    
    public FCMService(DeviceTokenDAO deviceTokenDAO) {
        this.deviceTokenDAO = deviceTokenDAO;
    }
    
    public boolean sendPushToUser(int userId, PushNotification notification) {
        try {
            String deviceToken = deviceTokenDAO.getDeviceToken(userId);
            if (deviceToken == null) {
                System.out.println("[FCM] Aucun token pour l'utilisateur " + userId);
                return false;
            }
            
            return sendPushToDevice(deviceToken, notification);
            
        } catch (Exception e) {
            System.err.println("[FCM] Erreur envoi push: " + e.getMessage());
            return false;
        }
    }

    public boolean sendPushToDevice(String deviceToken, PushNotification notification) {
        if (API_KEY == null || API_KEY.isEmpty()) {
            System.out.println("[FCM] Mode simulation (pas de clé API configurée)");
            simulatePush(deviceToken, notification);
            return true;
        }
        
        try {
            URL url = new URL(FCM_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            String payload = buildFCMPayload(deviceToken, notification);
            
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes());
                os.flush();
            }
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("[FCM] ✅ Notification envoyée à " + deviceToken.substring(0, 10) + "...");
                return true;
            } else {
                try (Scanner scanner = new Scanner(conn.getErrorStream())) {
                    String errorResponse = scanner.useDelimiter("\\A").next();
                    System.err.println("[FCM] ❌ Erreur FCM (" + responseCode + "): " + errorResponse);
                }
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("[FCM] ❌ Exception: " + e.getMessage());
            simulatePush(deviceToken, notification);
            return true;
        }
    }

    private String buildFCMPayload(String deviceToken, PushNotification notification) {
        return String.format(
            "{\"to\":\"%s\"," +
            "\"notification\":{" +
            "\"title\":\"%s\"," +
            "\"body\":\"%s\"," +
            "\"sound\":\"default\"" +
            "}," +
            "\"data\":{" +
            "\"type\":\"%s\"," +
            "\"timestamp\":\"%d\"," +
            "%s" +
            "}," +
            "\"priority\":\"high\"," +
            "\"content_available\":true" +
            "}",
            deviceToken,
            escapeJson(notification.getTitle()),
            escapeJson(notification.getBody()),
            notification.getType(),
            notification.getTimestamp(),
            buildDataJson(notification.getData())
        );
    }

    private String buildDataJson(java.util.Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (java.util.Map.Entry<String, String> entry : data.entrySet()) {
            if (!first) sb.append(",");
            sb.append(String.format("\"%s\":\"%s\"", 
                   entry.getKey(), escapeJson(entry.getValue())));
            first = false;
        }
        return sb.toString();
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    private void simulatePush(String deviceToken, PushNotification notification) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📱 PUSH NOTIFICATION SIMULÉE");
        System.out.println("=".repeat(60));
        System.out.println("📱 Device: " + deviceToken.substring(0, Math.min(20, deviceToken.length())) + "...");
        System.out.println("📌 Title: " + notification.getTitle());
        System.out.println("📝 Body: " + notification.getBody());
        System.out.println("🔧 Type: " + notification.getType());
        System.out.println("⏰ Time: " + new java.util.Date(notification.getTimestamp()));
        
        if (!notification.getData().isEmpty()) {
            System.out.println("📊 Data:");
            for (java.util.Map.Entry<String, String> entry : notification.getData().entrySet()) {
                System.out.println("  - " + entry.getKey() + ": " + entry.getValue());
            }
        }
        
        System.out.println("=".repeat(60) + "\n");
    }
    
    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
        if (apiKey != null && !apiKey.isEmpty()) {
            System.out.println("[FCM] Clé API configurée");
        } else {
            System.out.println("[FCM] Mode simulation activé (pas de clé API)");
        }
    }
}