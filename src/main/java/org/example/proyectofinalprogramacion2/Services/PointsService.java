package org.example.proyectofinalprogramacion2.Services;

import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.Points;

import java.util.Comparator;
import java.util.List;

public class PointsService {

    public int redeem(Customer customer, int pointsToRedeem) {
        List<Points> list = customer.getPoints();
        list.sort(Comparator.comparing(Points::getExpirationDate));

        int remaining = pointsToRedeem;
        int redeemed = 0;

        for (Points p : list) {
            if (p.isExpired()) continue;
            if (remaining <= 0) break;
            int use = Math.min(p.getAmount(), remaining);
            remaining -= use;
            redeemed += use;
        }

        if (remaining > 0) return 0;
        return redeemed;
    }
}