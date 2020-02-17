package ca.team4519.lib.pose;

import ca.team4519.frc2020.Gains;

public class TurretPose {

    private double angularPos;
    private double angularVel;
    private double goalOffset;
    private boolean hasValidGoal;
    private boolean onTarget;

    public TurretPose(double angularPos, double angularVel, double hasValidGoal ,double goalOffset)
    {
        this.angularPos = angularPos;
        this.angularVel = angularVel;
        this.goalOffset = goalOffset;
        this.hasValidGoal = hasValidGoal==1?true:false;
        this.onTarget = goalOffset==0?true:false;
    }

    public void reset(double angularPos, double angularVel, double hasValidGoal, double goalOffset)
    {
        this.angularPos = angularPos;
        this.angularVel = angularVel;
        this.goalOffset = goalOffset;
        this.hasValidGoal = hasValidGoal==1?true:false;
        this.onTarget = goalOffset==0?true:false;
    }

    public double getPosition()
    {
        return angularPos; 
    }

    public double getVelocity()
    {
        return angularVel;
    }

    public double getConvertedValue()
    {
        //y=mx+B
        return Gains.Turret.slope * getPosition() + Gains.Turret.offset;
    }

    public double getGoalOffset()
    {
        return goalOffset;
    }

    public boolean hasValidGoal()
    {
        return hasValidGoal;
    }

    public boolean onTarget()
    {
        return onTarget;
    }
}