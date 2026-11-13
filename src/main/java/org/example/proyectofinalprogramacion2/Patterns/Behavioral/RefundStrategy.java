package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

public interface RefundStrategy {
    double calculateRefund(double paidAmount, long hoursUntilScreening);
}