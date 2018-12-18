package com.example.mick.midicontroller.domains;

public class LinearAccelerometerEntry {
    private float x;
    private float y;
    private float z;
    private float timestamp;
    private double euclideanDistance;

    public LinearAccelerometerEntry(float x, float y, float z, float timestamp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }

    public double getEuclideanDistance() {
        return euclideanDistance;
    }

    public void setEuclideanDistance(double euclideanDistance) {
        this.euclideanDistance = euclideanDistance;
    }
}
