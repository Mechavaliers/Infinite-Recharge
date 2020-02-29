package ca.team4519.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.team4519.frc2020.Constants;
import ca.team4519.lib.*; //possibly needed?
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class Climber extends Subsystem implements Thread {

    private static Climber thisInstance;

    private final Solenoid climbLockL;

    private final Solenoid climbLockR;

    private VictorSP winchMotorL;
    private VictorSP winchMotorR;

    public synchronized static Climber GrabInstance() {

        if (thisInstance == null) {
            thisInstance = new Climber();
        }

        return thisInstance;

    }

    private Climber()
    {
   
        climbLockL = new Solenoid(Constants.lockReleaseL);
        climbLockR = new Solenoid(Constants.lockReleaseR);
        winchMotorL = new VictorSP(Constants.winchMotorL); 
        winchMotorR = new VictorSP(Constants.winchMotorR);
     }

     public void wantLockL(boolean lock) //nicole - solenoid
     {    
        climbLockL.set(lock);  
     }
     public void wantLockR(boolean lock) //nicole - solenoid
     {    
        climbLockR.set(lock);    
     }
     public void climberControl(double leftInput, double rightInput, boolean leftLock, boolean rightLock) //booleans are axis, doubles are buttons
     {    
        if(leftLock == true) {
            wantLockL(false);
        }
        if(rightLock == true) {
            wantLockR(false);
        }
        if(leftInput > 0) {
            //idk
        }
     }


    @Override
    public void loops() {
        // TODO Auto-generated method stub

    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableSubsystem() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateDashboard() {
        // TODO Auto-generated method stub

    }

    @Override
    public void feedLogger() {
        // TODO Auto-generated method stub

    }
    
    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
}