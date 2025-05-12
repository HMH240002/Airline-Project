package com.mycompany.airlineproject;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main entry point for the Flight Path Finder project.
 * Handles the execution flow of loading flight data, processing flight requests, and generating output.
 * 
 * @author hasan
 */

public class AirlineProject { // This is the main entry point of the program
    public static void main(String[] args) {
        try {
            // Print the current date and time in UTC for logging purposes
            ZonedDateTime now = ZonedDateTime.now(java.time.ZoneOffset.UTC);
            String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println("Current Date and Time (UTC): " + formattedDateTime);

            // Initialize the flight planner
            FlightPlanner planner = new FlightPlanner();
            
            // Load flight network data from flightData.txt
            System.out.println("Loading flight data...");
            planner.loadFlightData("flightData.txt");
            
            // Process flight requests from flightRequests.txt and write results to flightPaths.txt
            System.out.println("Processing flight requests...");
            planner.processFlightRequests("flightRequests.txt", "flightPaths.txt");
            
            // Notify the user that the process is complete
            System.out.println("Flight paths have been calculated and written to flightPaths.txt");
        } catch (FileNotFoundException e) {
            // Handle the case where input files are not found
            System.err.println("Error: One or more input files were not found. Please check the file paths.");
        } catch (IOException e) {
            // Handle general I/O exceptions
            System.err.println("Error processing files: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}