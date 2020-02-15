package ca.team4519.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.team4519.frc2020.Constants;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.Solenoid;

public class Elevator extends Subsystem implements Thread{
	
    private static Elevator thisInstance;

    private Solenoid releaseHook;
    
    private TalonSRX Elevatormotor; 
	
	public synchronized static Elevator GrabInstance()
    {

        if(thisInstance == null)
        {
            thisInstance = new Elevator();
        }

        return thisInstance;

    }

    private Elevator()
    {
   
        releaseHook = new Solenoid(1);

        Elevatormotor = new TalonSRX(Constants.elevatormov); 

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