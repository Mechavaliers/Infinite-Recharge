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

    public double getRobotDistance()
    {
        return (leftDist + rightDist) / 2;
    }

    public double getRobotVelocity()
    {
        return (leftVel + rightVel) / 2;
    }

    public double getLeftDistance()
    {
        return leftDist;
    }

    public double getRightDistance()
    {
        return rightDist;
    }

    public double getLeftVelocity()
    {
        return leftVel;
    }

    public double getRightVelocity()
    {
        return rightVel;
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