package com.mycompany.airlineproject;

/**
 * Main class for loading flight data, processing user requests, and writing results.
 * @author hasan
 */

import java.io.*;
import java.util.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class FlightPlanner {
    private FlightNetwork network;
    private Map<String, City> cities;
    private final String currentUser;
    private final ZonedDateTime currentDateTime;
    
    // Sets up the planner and records the current user and date/time.
    public FlightPlanner() {
        this.network = new FlightNetwork();
        this.cities = new HashMap<>();
        this.currentUser = "xxDM777";
        this.currentDateTime = ZonedDateTime.now(ZoneId.of("America/Chicago")); // Central Time
    }
    
    // Reads flight data from file and builds the network.
    public void loadFlightData(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int numFlights = Integer.parseInt(reader.readLine().trim());
            for (int i = 0; i < numFlights; i++) {
                String[] parts = reader.readLine().split("\\|");
                if (parts.length != 4) {
                    throw new IOException("Invalid flight data format");
                }
                City origin = cities.computeIfAbsent(parts[0], City::new);
                City destination = cities.computeIfAbsent(parts[1], City::new);
                double cost = Double.parseDouble(parts[2]);
                int duration = Integer.parseInt(parts[3]);
                network.addFlight(new Flight(origin, destination, cost, duration));
            }
        }
    }
    
    
    // Reads flight requests, finds best paths, and writes results to output file.
    public void processFlightRequests(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            // Write metadata
            writer.println("Current Date and Time (Central): " + 
                currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("User: " + currentUser);
            writer.println();
            int numRequests = Integer.parseInt(reader.readLine().trim());
            for (int i = 0; i < numRequests; i++) {
                String[] parts = reader.readLine().split("\\|");
                if (parts.length != 3) {
                    throw new IOException("Invalid request format");
                }
                City origin = cities.get(parts[0]);
                City destination = cities.get(parts[1]);
                boolean sortByTime = parts[2].equalsIgnoreCase("T");
                writer.printf("Flight %d: %s, %s (%s)%n", 
                    i + 1, parts[0], parts[1], 
                    sortByTime ? "Time" : "Cost");
                if (origin == null || destination == null) {
                    writer.println("Error: One or both cities not found in network");
                    continue;
                }
                List<FlightPath> paths = network.findAllPaths(origin, destination, sortByTime);
                if (paths.isEmpty()) {
                    writer.println("No flight paths available");
                } else {
                    int pathsToShow = Math.min(3, paths.size());
                    for (int j = 0; j < pathsToShow; j++) {
                        writer.printf("Path %d: %s%n", j + 1, paths.get(j));
                    }
                }
                writer.println();
            }
        }
    }
}