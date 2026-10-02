package org.example.proyectofinalprogramacion2.Model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Cancellation {
    public static double calculatePercentage(LocalDateTime screeningDate) {
        long hours = Duration.between(LocalDateTime.now(), screeningDate).toHours();
        if (hours > 24) return 1.00;
        if (hours >= 2) return 0.80;
        return 0.00;
    }
}