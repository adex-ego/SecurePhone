package com.securephone.server.api;

import com.securephone.server.database.*;
import com.securephone.server.security.*;
import com.securephone.server.models.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/contacts/*")
public class ContactServlet extends HttpServlet {
    
    private ContactDAO contactDAO;
    private UserDAO userDAO;
    private DatabaseManager dbManager;
    
    @Override
    public void init() {
        dbManager = DatabaseManager.getInstance();
        try {
            contactDAO = new ContactDAO(dbManager.getConnection());
            userDAO = new UserDAO(dbManager.getConnection());
        } catch (Exception e) {
            throw new RuntimeException("Erreur initialisation ContactServlet", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                handleGetContacts(req, resp);
            } else if ("/search".equals(pathInfo)) {
                handleSearchUsers(req, resp);
            } else {
                sendError(resp, 404, "Endpoint non trouvé");
            }
        } catch (Exception e) {
            sendError(resp, 500, "Erreur interne: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        
        try {
            if ("/add".equals(pathInfo)) {
                handleAddContact(req, resp);
            } else if ("/remove".equals(pathInfo)) {
                handleRemoveContact(req, resp);
            } else {
                sendError(resp, 404, "Endpoint non trouvé");
            }
        } catch (Exception e) {
            sendError(resp, 500, "Erreur interne: " + e.getMessage());
        }
    }
    
    private void handleGetContacts(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String sessionId = req.getParameter("session_id");
        SessionManager.Session session = SessionManager.getSession(sessionId);
        
        if (session == null) {
            sendError(resp, 401, "Session invalide");
            return;
        }
        
        var contacts = contactDAO.getUserContacts(session.userId);
        var users = userDAO.findContacts(session.userId);
        
        JSONArray contactsArray = new JSONArray();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            var contact = contacts.get(i);
            
            JSONObject contactObj = new JSONObject();
            contactObj.put("id", user.getId());
            contactObj.put("username", user.getUsername());
            contactObj.put("status", user.getStatus());
            contactObj.put("nickname", contact.getNickname());
            contactObj.put("favorite", contact.isFavorite());
            contactsArray.put(contactObj);
        }
        
        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("contacts", contactsArray);
        
        resp.getWriter().write(response.toString());
    }
    
    private void handleSearchUsers(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String sessionId = req.getParameter("session_id");
        SessionManager.Session session = SessionManager.getSession(sessionId);
        
        if (session == null) {
            sendError(resp, 401, "Session invalide");
            return;
        }
        
        String query = req.getParameter("q");
        if (query == null || query.trim().isEmpty()) {
            sendError(resp, 400, "Terme de recherche requis");
            return;
        }
        
        // Recherche simple (en production, utiliser FULLTEXT)
        String sql = "SELECT * FROM users WHERE username LIKE ? AND id != ? LIMIT 10";
        try (var stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            stmt.setInt(2, session.userId);
            
            var rs = stmt.executeQuery();
            JSONArray usersArray = new JSONArray();
            
            while (rs.next()) {
                JSONObject userObj = new JSONObject();
                userObj.put("id", rs.getInt("id"));
                userObj.put("username", rs.getString("username"));
                userObj.put("email", rs.getString("email"));
                userObj.put("status", rs.getString("status"));
                
                // Vérifier si déjà contact
                boolean isContact = contactDAO.isContact(session.userId, rs.getInt("id"));
                userObj.put("is_contact", isContact);
                
                usersArray.put(userObj);
            }
            
            JSONObject response = new JSONObject();
            response.put("status", "success");
            response.put("users", usersArray);
            
            resp.getWriter().write(response.toString());
        }
    }
    
    private void handleAddContact(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String sessionId = req.getParameter("session_id");
        SessionManager.Session session = SessionManager.getSession(sessionId);
        
        if (session == null) {
            sendError(resp, 401, "Session invalide");
            return;
        }
        
        int contactId = Integer.parseInt(req.getParameter("contact_id"));
        String nickname = req.getParameter("nickname");
        
        // Vérifier si l'utilisateur existe
        User contact = userDAO.findById(contactId);
        if (contact == null) {
            sendError(resp, 404, "Utilisateur non trouvé");
            return;
        }
        
        // Vérifier si déjà contact
        if (contactDAO.isContact(session.userId, contactId)) {
            sendError(resp, 409, "Déjà dans les contacts");
            return;
        }
        
        // Ajouter le contact
        boolean added = contactDAO.addContact(session.userId, contactId, nickname);
        
        JSONObject response = new JSONObject();
        if (added) {
            response.put("status", "success");
            response.put("message", "Contact ajouté");
            response.put("contact", new JSONObject()
                .put("id", contactId)
                .put("username", contact.getUsername())
                .put("nickname", nickname));
        } else {
            response.put("status", "error");
            response.put("message", "Erreur ajout contact");
        }
        
        resp.getWriter().write(response.toString());
    }
    
    private void handleRemoveContact(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String sessionId = req.getParameter("session_id");
        SessionManager.Session session = SessionManager.getSession(sessionId);
        
        if (session == null) {
            sendError(resp, 401, "Session invalide");
            return;
        }
        
        int contactId = Integer.parseInt(req.getParameter("contact_id"));
        boolean removed = contactDAO.removeContact(session.userId, contactId);
        
        JSONObject response = new JSONObject();
        response.put("status", removed ? "success" : "error");
        response.put("message", removed ? "Contact supprimé" : "Contact non trouvé");
        
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