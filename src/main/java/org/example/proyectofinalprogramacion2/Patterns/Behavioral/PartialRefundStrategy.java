package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

public class PartialRefundStrategy implements RefundStrategy {
    @Override
    public double calculateRefund(double paidAmount, long hoursUntilScreening) {
        return paidAmount * 0.80; // 80%
    }
}