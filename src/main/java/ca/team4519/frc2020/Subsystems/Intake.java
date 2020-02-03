package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Intake extends Subsystem implements Thread{

    private static Intake thisInstance;

    //private final Vic;

    private final AnalogPotentiometer armPosition;

    public interface Controllers
    {
        
    }

    private Intake()
    {
        armPosition = new AnalogPotentiometer(Constants.IntakeArmPot);
    }

    public synchronized static Intake GrabInstance()
    {

        if(thisInstance == null)
        {
            thisInstance = new Intake();
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
    
    public void wantsIntake(boolean wantsIntake) {
   	 	if (wantsIntake) {
   	 		// drop intake
   	 	}
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