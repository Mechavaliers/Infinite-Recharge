package ca.team4519.lib;

public class DrivebasePose
{
    private double leftDist;
    private double rightDist;
    private double leftVel;
    private double rightVel;
    private double angularPos;
    private double angularVel;

    public DrivebasePose(double leftDist, double rightDist, double leftVel, double rightVel, double angularPos, double angularVel)
    {
        this.leftDist = leftDist;
        this.rightDist = rightDist;
        this.leftVel = leftVel;
        this.rightVel = rightVel;
        this.angularPos = angularPos;
        this.angularVel = angularVel;
    }

    public void reset(double leftDist, double rightDist, double leftVel, double rightVel, double angularPos, double angularVel)
    {
        this.leftDist = leftDist;
        this.rightDist = rightDist;
        this.leftVel = leftVel;
        this.rightVel = rightVel;
        this.angularPos = angularPos;
        this.angularVel = angularVel;
    }

    public double robotDistance()
    {
        return (leftDist + rightDist) / 2;
    }

    public double robotVelocity()
    {
        return (leftVel + rightVel) / 2;
    }

    public double leftDistance()
    {
        return leftDist;
    }

    public double rightDistance()
    {
        return rightDist;
    }

    public double leftVelocity()
    {
        return leftVel;
    }

    public double rightVelocity()
    {
        return rightVel;
    }

    public double angularPosition()
    {
        return angularPos;
    }

    public double angularVelocity()
    {
        return angularVel;
    }

}