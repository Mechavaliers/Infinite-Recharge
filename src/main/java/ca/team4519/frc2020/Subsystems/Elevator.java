package ca.team4519.frc2020.subsystems;

import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

public class Elevator extends Subsystem implements Thread{
	
	private static Elevator thisInstance;
	
	public synchronized static Elevator GrabInstance()
    {

        if(thisInstance == null)
        {
            thisInstance = new Elevator();
        }

        return thisInstance;

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