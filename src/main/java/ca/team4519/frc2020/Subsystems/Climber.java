package ca.team4519.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.team4519.frc2020.Constants;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber extends Subsystem implements Thread {

    private static Climber thisInstance;

    private Solenoid releaseHook;

    private TalonSRX winchMotorL;
    private TalonSRX winchMotorR;

    public synchronized static Climber GrabInstance() {

        if (thisInstance == null) {
            thisInstance = new Climber();
        }

        return thisInstance;

    }

    private Climber()
    {
   
        releaseHook = new Solenoid(Constants.hookRelease);
        winchMotorL = new TalonSRX(Constants.winchMotorL); 
        winchMotorR = new TalonSRX(Constants.winchMotorR);
     }

     public void wantHook(boolean hook) //nicole - solenoid
     {    

        releaseHook.set(hook);   
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