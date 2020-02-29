package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Gains;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PDPMonitor {

    private static PDPMonitor thisInstance;

    public synchronized static PDPMonitor GrabInstance()
    {

        if(thisInstance == null)
        {
            thisInstance = new PDPMonitor();
        }

        return thisInstance;

    }

    private PowerDistributionPanel pdp = new PowerDistributionPanel();

    public double getTotalCurrent()
    {
        return pdp.getTotalCurrent();
    }

    public double getVoltage()
    {
      return pdp.getVoltage();
    }

    public double getTotalPower()
    {
        return pdp.getTotalPower();
    }

    public double getLeftDriveNeoA_Current()
    {
        return pdp.getCurrent(Gains.PDP.LeftDriveNeoA);
    }

    public double getLeftDriveNeoB_Current()
    {
        return pdp.getCurrent(Gains.PDP.LeftDriveNeoB);
    }

    public double getRightDriveNeoA_Current()
    {
        return pdp.getCurrent(Gains.PDP.RightDriveNeoA);
    }

    public double getRightDriveNeoB_Current()
    {
        return pdp.getCurrent(Gains.PDP.RightDriveNeoB);
    }

    public double getLeftDriveMotorsTotalCurrent()
    {
        return (getLeftDriveNeoA_Current()+getLeftDriveNeoB_Current());
    }

    public double getRightDriveMotorsTotalCurrent()
    {
        return (getRightDriveNeoA_Current()+getRightDriveNeoB_Current());
    }

    public double getDrivebaseTotalCurrent()
    {
        return (getLeftDriveMotorsTotalCurrent()+getRightDriveMotorsTotalCurrent());
    }

    public double getRightFlywheel_Current()
    {
        return pdp.getCurrent(Gains.PDP.RightFlywheel);
    }

    public double getLeftFlywheel_Current()
    {
        return pdp.getCurrent(Gains.PDP.LeftFlywheel);
    }

    public double getFlywheelTotalCurrent()
    {
        return getRightFlywheel_Current()+getLeftFlywheel_Current();
    }

    public double getIntakeRoller_Current()
    {
        return pdp.getCurrent(Gains.PDP.IntakeRollers);
    }
    
    public double getIntakePivot_Current()
    {
        return pdp.getCurrent(Gains.PDP.IntakePivot);
    }

    public double getLowerFeeder_Current()
    {
        return pdp.getCurrent(Gains.PDP.FeederLower);
    }

    public double getUpperFeeder_Current()
    {
        return pdp.getCurrent(Gains.PDP.FeederUpper);
    }

    public double getTurretPivot_Current()
    {
        return pdp.getCurrent(Gains.PDP.TurretPivot);
    }

    public void updateDashboard()
    {
        SmartDashboard.putNumber("PDP_Total_Current", getTotalCurrent());
        SmartDashboard.putNumber("Robot_Voltage", getVoltage());
        SmartDashboard.putNumber("Robot_Power", getTotalPower());
        SmartDashboard.putNumber("LeftDriveNeoA_Current", getLeftDriveNeoA_Current());
        SmartDashboard.putNumber("LeftDriveNeoB_Current", getLeftDriveNeoB_Current());
        SmartDashboard.putNumber("LeftDriveTotal_Current", getLeftDriveMotorsTotalCurrent());
        SmartDashboard.putNumber("RightDriveNeoA_Current", getRightDriveNeoA_Current());
        SmartDashboard.putNumber("RightDriveNeoB_Current", getRightDriveNeoB_Current());
        SmartDashboard.putNumber("RightDriveTotal_Current", getRightDriveMotorsTotalCurrent());
        SmartDashboard.putNumber("DrivebaseTotal_Current", getDrivebaseTotalCurrent());
        SmartDashboard.putNumber("RightFlywheel_Current", getRightFlywheel_Current());
        SmartDashboard.putNumber("LeftFlywheel_Current", getLeftFlywheel_Current());
        SmartDashboard.putNumber("FlywheelTotal_Current", getFlywheelTotalCurrent());
        SmartDashboard.putNumber("IntakeRoller_Current", getIntakeRoller_Current());
        SmartDashboard.putNumber("IntakePivot_Current", getIntakePivot_Current());
        SmartDashboard.putNumber("LowerFeeder_Current", getLowerFeeder_Current());
        SmartDashboard.putNumber("UpperFeederCurrent", getUpperFeeder_Current());
        SmartDashboard.putNumber("TurretPivot_Current", getTurretPivot_Current());
    }

    public void update()
    {
        updateDashboard();
    }
}