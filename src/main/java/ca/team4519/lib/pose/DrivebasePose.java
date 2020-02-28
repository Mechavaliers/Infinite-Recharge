package ca.team4519.lib.pose;

import edu.wpi.first.wpilibj.geometry.Pose2d;

public class DrivebasePose
{
    private double leftDist;
    private double rightDist;
    private double leftVel;
    private double rightVel;
    private double angularPos;
    private double angularVel;
    private Pose2d pose;
	public Object navX;

    public DrivebasePose(double leftDist, double rightDist, double leftVel, double rightVel, double angularPos, double angularVel, Pose2d pose)
    {
        this.leftDist = leftDist;
        this.rightDist = rightDist;
        this.leftVel = leftVel;
        this.rightVel = rightVel;
        this.angularPos = angularPos;
        this.angularVel = angularVel;
        this.pose = pose;
    }

    public void reset(double leftDist, double rightDist, double leftVel, double rightVel, double angularPos, double angularVel, Pose2d pose)
    {
        this.leftDist = leftDist;
        this.rightDist = rightDist;
        this.leftVel = leftVel;
        this.rightVel = rightVel;
        this.angularPos = angularPos;
        this.angularVel = angularVel;
        this.pose = pose;
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

    public Pose2d getRobotPose2d()
    {
        return pose;
    }

}