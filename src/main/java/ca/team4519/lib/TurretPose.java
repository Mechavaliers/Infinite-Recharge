package ca.team4519.lib;

public class TurretPose {

    private double angularPos;
    private double angularVel;

    public TurretPose(double angularPos, double angularVel) {
        this.angularPos = angularPos;
        this.angularVel = angularVel;
    }

    public void reset(double angularPos, double angularVel) {
        this.angularPos = angularPos;
        this.angularVel = angularVel;
    }

    public double getPosition() {
        return angularPos; 
    }

    public double getVelocity() {
        return angularVel;
    }
}