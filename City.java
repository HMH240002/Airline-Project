package com.mycompany.airlineproject;

/**
 * Represents a city/airport, uniquely identified by its code.
 * @author hasan
 */

public class City {
    private String code; // Using airport codes instead of city names
    
    public City(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof City)) return false;
        City other = (City) obj;
        return code.equals(other.code);
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }
    
    @Override
    public String toString() {
        return code;
    }
}

