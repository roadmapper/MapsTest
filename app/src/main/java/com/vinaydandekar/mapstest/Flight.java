package com.vinaydandekar.mapstest;

/**
 * Created by Vinay Dandekar on 7/22/2014.
 */
public class Flight {

    private String flightNum, origin, destination;
    private int track;
    private double latitude, longitude;

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public String toString() {
        return "Flight Number: " + flightNum + "\n" + origin + " -> " + destination + "\n" + "(" + latitude + ", " + longitude + ") \n\n";
    }
}
