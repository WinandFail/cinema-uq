package org.example.proyectofinalprogramacion2.Services;

import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.Points;

import java.util.Comparator;
import java.util.List;

public class PointsService {


    public boolean canRedeem(Customer customer, int pointsToRedeem) {
        return customer.totalPoints() >= pointsToRedeem;
    }


    public void deductPoints(Customer customer, int pointsToRedeem) {
        List<Points> list = customer.getPoints();

        list.sort(Comparator.comparing(Points::getExpirationDate));

        int remaining = pointsToRedeem;

        for (Points p : list) {
            if (p.isExpired()) continue;
            if (remaining <= 0) break;

            int use = Math.min(p.getAmount(), remaining);
            p.setAmount(p.getAmount() - use);
            remaining -= use;
        }


        list.removeIf(p -> p.getAmount() <= 0);
    }
}