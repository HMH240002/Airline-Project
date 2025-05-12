package com.mycompany.airlineproject;

/**
 * Stores the flight connections as a graph and finds all paths between cities.
 * @author hasan
 */

import java.util.*;

public class FlightNetwork {
    private Map<City, List<Flight>> adjacencyList;
    private boolean[][] adjacencyMatrix;
    private List<City> cities;
    
    // Initializes empty network.
    public FlightNetwork() {
        this.adjacencyList = new HashMap<>();
        this.cities = new ArrayList<>();
    }
    
    // Adds a flight (and its return flight) to the network.
    public void addFlight(Flight flight) {
        // Add flight to adjacency list
        adjacencyList.computeIfAbsent(flight.getOrigin(), k -> new ArrayList<>()).add(flight);
        Flight returnFlight = new Flight(
            flight.getDestination(), 
            flight.getOrigin(), 
            flight.getCost(), 
            flight.getDuration()
        );
        adjacencyList.computeIfAbsent(flight.getDestination(), k -> new ArrayList<>()).add(returnFlight);
        
        // Add cities to list if not already present
        if (!cities.contains(flight.getOrigin())) {
            cities.add(flight.getOrigin());
        }
        if (!cities.contains(flight.getDestination())) {
            cities.add(flight.getDestination());
        }
        
        // Update adjacency matrix
        updateAdjacencyMatrix();
    }
    
    // Updates the adjacency matrix (not essential for pathfinding here).
    private void updateAdjacencyMatrix() {
        int size = cities.size();
        adjacencyMatrix = new boolean[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                City from = cities.get(i);
                City to = cities.get(j);
                List<Flight> flights = adjacencyList.getOrDefault(from, new ArrayList<>());
                adjacencyMatrix[i][j] = flights.stream()
                    .anyMatch(f -> f.getDestination().equals(to));
            }
        }
    }
    
    // Finds all paths between two cities, sorted by time or cost.
    public List<FlightPath> findAllPaths(City origin, City destination, boolean sortByTime) {
        List<FlightPath> allPaths = new ArrayList<>();
        Stack<Flight> currentPath = new Stack<>();
        Set<City> visited = new HashSet<>();
        
        findPathsRecursive(origin, destination, visited, currentPath, allPaths, sortByTime);
        
        Collections.sort(allPaths);
        return allPaths;
    }
    
    // Helper for recursive DFS path finding.
    private void findPathsRecursive(City current, City destination, 
                                  Set<City> visited, Stack<Flight> currentPath,
                                  List<FlightPath> allPaths, boolean sortByTime) {
        visited.add(current); // Mark this city as visited
        
        // If we've reached the destination, save the current path
        if (current.equals(destination)) {
            FlightPath completePath = new FlightPath(sortByTime);
            currentPath.forEach(completePath::addFlight);
            allPaths.add(completePath);
        } else {
            // For each possible outgoing flight from the current city
            List<Flight> possibleFlights = adjacencyList.getOrDefault(current, new ArrayList<>());
            for (Flight flight : possibleFlights) {
                // For each possible outgoing flight from the current city
                if (!visited.contains(flight.getDestination())) {
                    currentPath.push(flight);// Add this flight to the current path
                // Recurse from the destination city of this flight
                    findPathsRecursive(flight.getDestination(), destination, 
                                     visited, currentPath, allPaths, sortByTime);
                    currentPath.pop();  // Backtrack: remove the last flight to try other options
                }
            }
        }
        
        // Unmark this city so it can be used in other paths
        visited.remove(current);
    }
}

