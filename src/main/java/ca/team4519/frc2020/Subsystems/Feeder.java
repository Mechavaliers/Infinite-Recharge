package ca.team4519.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.Constants;

import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import edu.wpi.first.wpilibj.Ultrasonic;

public class Feeder extends Subsystem implements Thread
{

    private Ultrasonic balldetector;
    //TODO Initalize 2 motor controllers - TBD

    private static Feeder thisInstance;

    private TalonSRX Feederx; //nicole

    private TalonSRX Feedery; //nicole

    //private VictorSPX Feederz; unsure if u wanted victorspx or talonsrx - ben said we had both. Delete if unneeded


    public synchronized static Feeder GrabInstance()
    {

       if(thisInstance == null)
       {
           thisInstance = new Feeder();
       }

       return thisInstance;

    }
     
    private Feeder()
    {
   
        balldetector = new Ultrasonic(0,1);
        Feederx = new TalonSRX(Constants.feeder1); //should it have 2 feeders?
        Feedery = new TalonSRX(Constants.feeder2); 

        //Feederz = new VictorSPX(Constants.feeder2); unsure if victorspx or talonsrx. Delete if unneeded


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
    
    public void intakeBall()
    {
        //TODO populate method with intake sequence logic
    }

    @Override
    public void loops()
    {
        // TODO add any method that needs to be run in the robots main loop (200hz)

    }

    @Override
    public void zeroSensors()
    {
        // TODO reset/home any sensors in this subsystem

    }

    @Override
    public void disableSubsystem()
    {
        // TODO disable all motor outputs
    }

    @Override
    public void updateDashboard()
    {
        // TODO Feed dashboard

    }

    @Override
    public void feedLogger()
    {
        // TODO feed MechaLogger 

    }
    
    @Override
    public void update()
    {
        // TODO Auto-generated method stub

    }

}