package org.example.proyectofinalprogramacion2.Model;

public class Admin extends User {
    private String position;

    public Admin(String id, String name, String email, String password, String position) {
        super(id, name, email, password);
        this.position = position;
    }

    public void rechargeCard(VirtualCard card, double amount) {
        card.recharge(amount);
    }

    public String getPosition() { return position; }
}