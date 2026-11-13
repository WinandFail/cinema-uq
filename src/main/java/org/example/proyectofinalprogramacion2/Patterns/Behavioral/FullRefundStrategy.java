package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

public class FullRefundStrategy implements RefundStrategy {
    @Override
    public double calculateRefund(double paidAmount, long hoursUntilScreening) {
        return paidAmount; // 100%
    }
}