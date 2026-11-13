package org.example.proyectofinalprogramacion2.Services;

import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.Points;

import java.util.Comparator;
import java.util.List;

public class PointsService {

    // 1. Verificar si tiene puntos suficientes
    public boolean canRedeem(Customer customer, int pointsToRedeem) {
        return customer.totalPoints() >= pointsToRedeem;
    }

    // 2. Deducir los puntos de los lotes más próximos a vencer
    public void deductPoints(Customer customer, int pointsToRedeem) {
        List<Points> list = customer.getPoints();
        // Ordenamos por fecha de vencimiento (los más viejos primero)
        list.sort(Comparator.comparing(Points::getExpirationDate));

        int remaining = pointsToRedeem;

        for (Points p : list) {
            if (p.isExpired()) continue;
            if (remaining <= 0) break;

            int use = Math.min(p.getAmount(), remaining);
            p.setAmount(p.getAmount() - use);
            remaining -= use;
        }

        // Limpiamos los lotes de puntos que se quedaron en 0
        list.removeIf(p -> p.getAmount() <= 0);
    }
}