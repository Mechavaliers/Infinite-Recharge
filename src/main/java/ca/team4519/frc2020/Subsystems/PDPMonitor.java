package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Gains;
import ca.team4519.lib.MechaLogger;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

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

    public void FeedLogger()
    {
        MechaLogger.grabInstance().logThis_Double("PDP_Total_Current", () -> getTotalCurrent());
        MechaLogger.grabInstance().logThis_Double("Robot_Voltage", () -> getVoltage());
        MechaLogger.grabInstance().logThis_Double("Robot_Power", () -> getTotalPower());
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoA_Current", () -> getLeftDriveNeoA_Current());
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoB_Current", () -> getLeftDriveNeoB_Current());
        MechaLogger.grabInstance().logThis_Double("LeftDriveTotal_Current", () -> getLeftDriveMotorsTotalCurrent());
        MechaLogger.grabInstance().logThis_Double("RightDriveNeoA_Current", () -> getRightDriveNeoA_Current());
        MechaLogger.grabInstance().logThis_Double("RightDriveNeoB_Current", () -> getRightDriveNeoB_Current());
        MechaLogger.grabInstance().logThis_Double("RightDriveTotal_Current", () -> getRightDriveMotorsTotalCurrent());
        MechaLogger.grabInstance().logThis_Double("DrivebaseTotal_Current", () -> getDrivebaseTotalCurrent());
        MechaLogger.grabInstance().logThis_Double("RightFlywheel_Current", () -> getRightFlywheel_Current());
        MechaLogger.grabInstance().logThis_Double("LeftFlywheel_Current", () -> getLeftFlywheel_Current());
        MechaLogger.grabInstance().logThis_Double("FlywheelTotal_Current", () -> getFlywheelTotalCurrent());
        MechaLogger.grabInstance().logThis_Double("IntakeRoller_Current", () -> getIntakeRoller_Current());
        MechaLogger.grabInstance().logThis_Double("IntakePivot_Current", () -> getIntakePivot_Current());
        MechaLogger.grabInstance().logThis_Double("LowerFeeder_Current", () -> getLowerFeeder_Current());
        MechaLogger.grabInstance().logThis_Double("UpperFeederCurrent", () -> getUpperFeeder_Current());
        MechaLogger.grabInstance().logThis_Double("TurretPivot_Current", () -> getTurretPivot_Current());

    }

    public void update()
    {
        FeedLogger();
    }
}