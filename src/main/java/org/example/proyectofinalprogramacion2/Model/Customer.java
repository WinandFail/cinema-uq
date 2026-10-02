package org.example.proyectofinalprogramacion2.Model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String phone;
    private VirtualCard card;
    private List<Points> points;

    public Customer(String id, String name, String email, String password, String phone) {
        super(id, name, email, password);
        this.phone = phone;
        this.card = new VirtualCard("VC-" + id, 0.0);
        this.points = new ArrayList<>();
    }

    public VirtualCard getCard() { return card; }
    public List<Points> getPoints() { return points; }

    public void addPoints(Points p) { points.add(p); }

    public int totalPoints() {
        return points.stream().filter(p -> !p.isExpired()).mapToInt(Points::getAmount).sum();
    }
}
