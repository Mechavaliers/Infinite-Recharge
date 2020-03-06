package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem implements Thread
{

    private static Climber thisInstance;

    private final Solenoid climbLockL;

    private final Solenoid climbLockR;

    private VictorSP winchMotorL;
    private VictorSP winchMotorR;

    public synchronized static Climber GrabInstance()
    {
        if (thisInstance == null) thisInstance = new Climber();
        return thisInstance;
    }

    private Climber()
    {
   
        climbLockL = new Solenoid(Constants.lockReleaseL);
        climbLockR = new Solenoid(Constants.lockReleaseR);
        winchMotorL = new VictorSP(Constants.winchMotorL); 
        winchMotorR = new VictorSP(Constants.winchMotorR);
    }

    public void wantLockL() //nicole - solenoid
    {    
       climbLockL.set(false);  
    }
    public void wantLockR() //nicole - solenoid
    {    
       climbLockR.set(false);    
    }
    public void wantUnlockL()
    {
        climbLockL.set(true);
    }
    public void wantUnlockR()
    {
        climbLockR.set(true);
    }

    public void wantUnlockAll()
    {
        climbLockL.set(true);
        climbLockR.set(true);
    }

    public void wantLockAll()
    {
        climbLockR.set(false);
        climbLockL.set(false);
    }

    public void setLeftPower(double power)
    {
        winchMotorL.set((Math.abs(power) > Math.abs(0.1))? power : 0.0);
    }

    public void setLeftOff()
    {
        winchMotorL.set(0.0);
    }

    public void setRightPower(double power)
    {
        winchMotorR.set((Math.abs(power) > Math.abs(0.1))? -power : 0.0);
    }

    public void setRightOff()
    {
        winchMotorR.set(0.0);
    }

    public void setLeftRightPower(double power)
    {
        power = (power < 0)? power/2 : power;  

        winchMotorL.set((Math.abs(power) > Math.abs(0.1))? power : 0.0);
        winchMotorR.set((Math.abs(power) > Math.abs(0.1))? -power : 0.0);
    }

    public void climberControl(double leftInput, double rightInput, boolean unlockLeft, boolean unlockRight) //booleans are axis, doubles are buttons
    {    
       if(unlockLeft && unlockRight)
       {
           wantUnlockAll();
           setLeftRightPower(leftInput);
       }
       else if(unlockLeft && !unlockRight)
       {
            wantLockR();
            wantUnlockL();
            setLeftPower(leftInput);
            setRightOff();
       }
       else if(unlockRight && !unlockLeft)
       {
            wantLockL();
            wantUnlockR();
            setRightPower(rightInput);
            setLeftOff();
       }
       else
       {
            wantLockAll();
            setRightOff();
            setLeftOff();
       }
    }

    @Override
    public void loops()
    {
    }

    @Override
    public void zeroSensors()
    {
    }

    @Override
    public void disableSubsystem()
    {
    }

    @Override
    public void updateDashboard()
    {
        SmartDashboard.putBoolean("Is Left Lock Engaged", climbLockL.get());
        SmartDashboard.putBoolean("Is Right Lock Engaged", climbLockR.get());
    }
}