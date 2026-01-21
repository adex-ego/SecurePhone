package com.securephone.server.api;

import com.securephone.server.database.*;
import com.securephone.server.security.*;
import com.securephone.server.models.Message;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/messages/*")
public class MessageServlet extends HttpServlet {
    
    private MessageDAO messageDAO;
    private DatabaseManager dbManager;
    
    @Override
    public void init() {
        dbManager = DatabaseManager.getInstance();
        try {
            messageDAO = new MessageDAO(dbManager.getConnection());
        } catch (Exception e) {
            throw new RuntimeException("Erreur initialisation MessageServlet", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        
        try {
            if ("/send".equals(pathInfo)) {
                handleSendMessage(req, resp);
            } else if ("/read".equals(pathInfo)) {
                handleMarkAsRead(req, resp);
            } else {
                sendError(resp, 404, "Endpoint non trouvé");
            }
        } catch (Exception e) {
            sendError(resp, 500, "Erreur interne: " + e.getMessage());
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                handleGetConversation(req, resp);
            } else if ("/unread".equals(pathInfo)) {
                handleGetUnread(req, resp);
            } else {
                sendError(resp, 404, "Endpoint non trouvé");
            }
        } catch (Exception e) {
            sendError(resp, 500, "Erreur interne: " + e.getMessage());
        }
    }
    
    private void handleSendMessage(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String sessionId = req.getParameter("session_id");
        SessionManager.Session session = SessionManager.getSession(sessionId);
        
        if (session == null) {
            sendError(resp, 401, "Session invalide");
            return;
        }
        
        int receiverId = Integer.parseInt(req.getParameter("receiver_id"));
        String content = req.getParameter("content");
        String roomId = req.getParameter("room_id");
        
        if (content == null || content.trim().isEmpty()) {
            sendError(resp, 400, "Contenu du message requis");
            return;
        }
        
        Message message = new Message();
        message.setSenderId(session.userId);
        message.setReceiverId(receiverId);
        message.setRoomId(roomId);
        message.setContent(content);
        
        boolean saved = messageDAO.saveMessage(message);
        
        if (!saved) {
            sendError(resp, 500, "Erreur envoi message");
            return;
        }
        
        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("message_id", message.getId());
        
        resp.getWriter().write(response.toString());
    }
    
    private void handleGetConversation(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String sessionId = req.getParameter("session_id");
        SessionManager.Session session = SessionManager.getSession(sessionId);
        
        if (session == null) {
            sendError(resp, 401, "Session invalide");
            return;
        }
        
        int otherUserId = Integer.parseInt(req.getParameter("user_id"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        
        var messages = messageDAO.getConversation(session.userId, otherUserId, limit);
        
        JSONArray messagesArray = new JSONArray();
        for (Message msg : messages) {
            JSONObject msgObj = new JSONObject();
            msgObj.put("id", msg.getId());
            msgObj.put("sender_id", msg.getSenderId());
            msgObj.put("receiver_id", msg.getReceiverId());
            msgObj.put("content", msg.getContent());
            msgObj.put("timestamp", msg.getTimestamp());
            msgObj.put("read", msg.isReadStatus());
            messagesArray.put(msgObj);
        }
        
        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("messages", messagesArray);
        
        resp.getWriter().write(response.toString());
    }
    
    private void handleGetUnread(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String sessionId = req.getParameter("session_id");
        SessionManager.Session session = SessionManager.getSession(sessionId);
        
        if (session == null) {
            sendError(resp, 401, "Session invalide");
            return;
        }
        
        var messages = messageDAO.getUnreadMessages(session.userId);
        
        JSONArray messagesArray = new JSONArray();
        for (Message msg : messages) {
            JSONObject msgObj = new JSONObject();
            msgObj.put("id", msg.getId());
            msgObj.put("sender_id", msg.getSenderId());
            msgObj.put("content", msg.getContent());
            msgObj.put("timestamp", msg.getTimestamp());
            messagesArray.put(msgObj);
        }
        
        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("unread_count", messages.size());
        response.put("messages", messagesArray);
        
        resp.getWriter().write(response.toString());
    }
    
    private void handleMarkAsRead(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String sessionId = req.getParameter("session_id");
        SessionManager.Session session = SessionManager.getSession(sessionId);
        
        if (session == null) {
            sendError(resp, 401, "Session invalide");
            return;
        }
        
        int messageId = Integer.parseInt(req.getParameter("message_id"));
        boolean updated = messageDAO.markAsRead(messageId);
        
        JSONObject response = new JSONObject();
        response.put("status", updated ? "success" : "error");
        response.put("message", updated ? "Message marqué comme lu" : "Message non trouvé");
        
        resp.getWriter().write(response.toString());
    }
    
    private void sendError(HttpServletResponse resp, int code, String message) 
            throws IOException {
        
        resp.setStatus(code);
        JSONObject error = new JSONObject();
        error.put("error", message);
        resp.getWriter().write(error.toString());
    }
}