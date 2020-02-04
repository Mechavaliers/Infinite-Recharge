package ca.team4519.frc2020.subsystems;

import ca.team4519.lib.Subsystem;
import ca.team4519.frc2020.Gains;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.Ultrasonic;

//this sensor detects if there's a ball underneath the feeder, shoudl return some sort of distance and if distance is short, be ready for intake

public class Feeder extends Subsystem implements Thread{

    private Ultrasonic balldetector;

    private static Feeder thisInstance;

    public synchronized static Feeder GrabInstance()
    {

       if(thisInstance == null)
       {
           thisInstance = new Feeder();
       }

       return thisInstance;

    }
     
    private Feeder() {
   
        balldetector = new Ultrasonic(0,1); //make a method that returns a boolean if less than this distance (Gains.Feeder). Everything here might be wrong, delete if necessary

     }
        
    public boolean checkDistance()
    {    
        if (balldetector.getRangeInches() >= Gains.Feeder.BallDetectorRange) {
            return true;
        }
        else {
            return false;
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