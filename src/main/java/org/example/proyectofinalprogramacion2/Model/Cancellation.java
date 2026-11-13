package org.example.proyectofinalprogramacion2.Model;

import org.example.proyectofinalprogramacion2.Patterns.Behavioral.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class Cancellation {
    public static double calculateRefund(double paidAmount, LocalDateTime screeningDate) {
        long hours = Duration.between(LocalDateTime.now(), screeningDate).toHours();
        RefundStrategy strategy;

        if (hours > 24) {
            strategy = new FullRefundStrategy();
        } else if (hours >= 2) {
            strategy = new PartialRefundStrategy();
        } else {
            strategy = new NoRefundStrategy();
        }
        return strategy.calculateRefund(paidAmount, hours);
    }
}