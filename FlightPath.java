package com.mycompany.airlineproject;

/**
 * // Represents a sequence of flights (a path) between two cities and can be compared by time or cost.
 * @author hasan
 */

import java.util.ArrayList;
import java.util.List;

public class FlightPath implements Comparable<FlightPath> {
    private List<Flight> flights;
    private boolean sortByTime;
    
    // Creates an empty path, with sorting preference
    public FlightPath(boolean sortByTime) {
        this.flights = new ArrayList<>();
        this.sortByTime = sortByTime;
    }
    
    public void addFlight(Flight flight) {
        flights.add(flight);
    }
    
    public double getTotalCost() {
        return flights.stream()
                     .mapToDouble(Flight::getCost)
                     .sum();
    }
    
    public int getTotalDuration() {
        return flights.stream()
                     .mapToInt(Flight::getDuration)
                     .sum();
    }
    
    public List<Flight> getFlights() {
        return new ArrayList<>(flights);
    }
    
    // Compare paths by total duration or cost.
    @Override
    public int compareTo(FlightPath other) {
        if (sortByTime) {
            return Integer.compare(this.getTotalDuration(), other.getTotalDuration());
        } else {
            return Double.compare(this.getTotalCost(), other.getTotalCost());
        }
    }
    
    // String representation for output.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < flights.size(); i++) {
            sb.append(flights.get(i).getOrigin());
            if (i < flights.size() - 1) {
                sb.append(" -> ");
            }
        }
        if (!flights.isEmpty()) {
            sb.append(" -> ").append(flights.get(flights.size() - 1).getDestination());
        }
        sb.append(". Time: ").append(getTotalDuration());
        sb.append(" Cost: ").append(String.format("%.2f", getTotalCost()));
        return sb.toString();
    }
}
