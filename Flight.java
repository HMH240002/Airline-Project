package com.mycompany.airlineproject;

/**
 * Represents a direct flight between two cities with cost and duration.
 * @author hasan
 */

public class Flight {
    private City origin;
    private City destination;
    private double cost;
    private int duration; // in minutes
    
    public Flight(City origin, City destination, double cost, int duration) {
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.duration = duration;
    }
    
    public City getOrigin() { return origin; }
    public City getDestination() { return destination; }
    public double getCost() { return cost; }
    public int getDuration() { return duration; }
    
    @Override
    public String toString() {
        return origin + " -> " + destination + 
               " (Cost: $" + String.format("%.2f", cost) + 
               ", Time: " + duration + " minutes)";
    }
}

