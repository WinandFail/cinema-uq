package org.example.proyectofinalprogramacion2.Services;

import org.example.proyectofinalprogramacion2.Model.User;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser; // Ahora es User, puede ser Admin o Customer

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User user) { this.currentUser = user; }
}