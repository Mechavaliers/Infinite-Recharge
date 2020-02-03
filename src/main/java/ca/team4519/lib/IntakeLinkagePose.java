package ca.team4519.lib;

public class IntakeLinkagePose
{
    private double angularPos;
    private double angularVel;

    public IntakeLinkagePose(double angularPos, double angularVel)
    {
        this.angularPos = angularPos;
        this.angularVel = angularVel;
    }

    public void reset(double angularPos, double angularVel)
    {
        this.angularPos = angularPos;
        this.angularVel = angularVel;
    }

    public double getAngularPosition()
    {
        return angularPos;
    }

    public double getAngularVelocity()
    {
        return angularVel;
    }
}