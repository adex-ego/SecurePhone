package com.securephone.server.api;

import com.securephone.server.database.*;
import com.securephone.server.security.*;
import com.securephone.server.models.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import org.json.JSONObject;

@WebServlet("/api/auth/*")
public class AuthServlet extends HttpServlet {
    
    private UserDAO userDAO;
    private DatabaseManager dbManager;
    
    @Override
    public void init() {
        dbManager = DatabaseManager.getInstance();
        try {
            userDAO = new UserDAO(dbManager.getConnection());
        } catch (Exception e) {
            throw new RuntimeException("Erreur initialisation AuthServlet", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        
        try {
            if ("/login".equals(pathInfo)) {
                handleLogin(req, resp);
            } else if ("/verify-2fa".equals(pathInfo)) {
                handleVerify2FA(req, resp);
            } else if ("/register".equals(pathInfo)) {
                handleRegister(req, resp);
            } else {
                sendError(resp, 404, "Endpoint non trouvé");
            }
        } catch (Exception e) {
            sendError(resp, 500, "Erreur interne: " + e.getMessage());
        }
    }
    
    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        if (username == null || password == null) {
            sendError(resp, 400, "Username et password requis");
            return;
        }
        
        User user = userDAO.findByUsername(username);
        if (user == null) {
            sendError(resp, 401, "Utilisateur non trouvé");
            return;
        }
        
        if (!PasswordHasher.verify(password, user.getPasswordHash())) {
            sendError(resp, 401, "Mot de passe incorrect");
            return;
        }
        
        // Générer code 2FA
        String code = Simple2FA.generateCode(username);
        
        JSONObject response = new JSONObject();
        response.put("status", "2fa_required");
        response.put("username", username);
        response.put("message", "Code 2FA généré, vérifiez la console serveur");
        
        resp.getWriter().write(response.toString());
    }
    
    private void handleVerify2FA(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String username = req.getParameter("username");
        String code = req.getParameter("code");
        
        if (username == null || code == null) {
            sendError(resp, 400, "Username et code requis");
            return;
        }
        
        if (!Simple2FA.verifyCode(username, code)) {
            sendError(resp, 401, "Code 2FA invalide");
            return;
        }
        
        User user = userDAO.findByUsername(username);
        if (user == null) {
            sendError(resp, 401, "Utilisateur non trouvé");
            return;
        }
        
        // Créer session
        String sessionId = SessionManager.createSession(user.getId(), user.getUsername());
        
        // Mettre à jour statut
        userDAO.updateStatus(user.getId(), "online");
        
        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("session_id", sessionId);
        response.put("user_id", user.getId());
        response.put("username", user.getUsername());
        
        resp.getWriter().write(response.toString());
    }
    
    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) 
            throws Exception {
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        
        if (username == null || password == null || email == null) {
            sendError(resp, 400, "Tous les champs sont requis");
            return;
        }
        
        // Vérifier si l'utilisateur existe déjà
        if (userDAO.findByUsername(username) != null) {
            sendError(resp, 409, "Username déjà utilisé");
            return;
        }
        
        // Hasher le mot de passe
        String hashedPassword = PasswordHasher.hash(password);
        
        // Créer l'utilisateur
        boolean created = userDAO.createUser(username, hashedPassword, email);
        
        if (!created) {
            sendError(resp, 500, "Erreur création utilisateur");
            return;
        }
        
        JSONObject response = new JSONObject();
        response.put("status", "success");
        response.put("message", "Utilisateur créé avec succès");
        
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