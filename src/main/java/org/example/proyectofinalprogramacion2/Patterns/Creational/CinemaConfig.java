package org.example.proyectofinalprogramacion2.Patterns.Creational;

public class CinemaConfig {

    private double vat;
    private int pointsPerThousand;


    private CinemaConfig() {
        this.vat = 0.19;
        this.pointsPerThousand = 10;
    }


    private static class Holder {
        private static final CinemaConfig INSTANCE = new CinemaConfig();
    }

    public static CinemaConfig getInstance() {
        return Holder.INSTANCE;
    }

    public double getVat() { return vat; }
    public int getPointsPerThousand() { return pointsPerThousand; }
}