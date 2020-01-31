package ca.team4519.frc2020.Subsystems;

import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

public class Flywheel extends Subsystem implements Thread{

	 public synchronized static Flywheel GrabInstance()
    {

       if(thisInstance == null)
       {
           thisInstance = new Flywheel();
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