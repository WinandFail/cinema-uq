package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

public class NoRefundStrategy implements RefundStrategy {
    @Override
    public double calculateRefund(double paidAmount, long hoursUntilScreening) {
        return 0.0; // 0% de reembolso
    }
}