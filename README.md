# Airline-Project
This Java-based project implements a flight path finder system that determines optimal routes between cities using an airline network. The system processes flight data and user requests to generate efficient travel plans based on either time or cost preferences.

## Table of Contents
1. Overview
2. Architecture
3. Data Structures
4. File Formats
5. Usage
6. Algorithm
7. Testing
8. Conclusion
   

## Overview
* Graph-based routing with object-oriented design.
* Supports TCP/UDP-like flight mapping (by analogy): origin, destination, cost, duration.
* Outputs top 3 routes sorted by cost or time.


## Architecture

### Core Classes
* **City**: Represents airports (overrides `hashCode` & `equals`).
* **Flight**: Describes a leg (origin, destination, cost, duration, `Comparable`).
* **FlightPath**: Aggregates legs; computes total cost/time; implements comparison.

### Processing Classes
* **FlightNetwork**: Adjacency list graph; path-finding logic.
* **FlightPlanner**: File I/O handler; parses requests and formats output.
* **AirlineProject**: `main` entry point; orchestrates components.

## Data Structures
* **Adjacency List**: `Map<City, List<Flight>>`
* **Stack**: Backtracking path exploration
* **HashSet**: Visited city tracking


## File Formats
* **flightData.txt**

  ```
  DFW|LAX|350|185
  DFW|JFK|290|195
  ...  
  ```
  
* **flightRequests.txt**
  ```
  3
  DFW|CDG|T
  DFW|CDG|C
  LAX|CDG|C
  ```
  
* **Output**
  ```
  Flight 1: DFW -> CDG (Time)
  1. DFW -> CDG | Time: 600 Cost: 1200.00
  2. DFW -> CLT -> CDG | Time: 625 Cost: 1115.00
  3. DFW -> ORD -> CDG | Time: 630 Cost: 1075.00
  ```



## Algorithm

* **Iterative backtracking** (DFS-style) to explore all routes.
* **Cycle prevention** with `visited` set.
* **Sorting** via `Comparable` on `FlightPath`, returns top 3.


## Testing

* **Unit tests** for direct, multi-stop, and missing routes.
* **Stress tests** on large datasets for performance and memory.


## Conclusion

Flight Path Finder delivers efficient, sorted route options with clear output and robust error handling—demonstrating fundamental graph algorithms in Java.

