package com.example.farm.farm;

public class FarmStats {

    private final long cowCount;
    private final long chickenCount;
    private final double totalMilkLiters;
    private final double totalEggs;

    public FarmStats(long cowCount, long chickenCount,
                     double totalMilkLiters, double totalEggs) {
        this.cowCount = cowCount;
        this.chickenCount = chickenCount;
        this.totalMilkLiters = totalMilkLiters;
        this.totalEggs = totalEggs;
    }

    public long getCowCount() {
        return cowCount;
    }

    public long getChickenCount() {
        return chickenCount;
    }

    public long getTotalAnimals() {
        return cowCount + chickenCount;
    }

    public double getTotalMilkLiters() {
        return totalMilkLiters;
    }

    public double getTotalEggs() {
        return totalEggs;
    }
}