package ca.team4519.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.Constants;

import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import edu.wpi.first.wpilibj.Ultrasonic;

public class Feeder extends Subsystem implements Thread
{

   // private Ultrasonic balldetector;
    //TODO Initalize 2 motor controllers - TBD

    private static Feeder thisInstance;

    private VictorSPX feederx; 

    private VictorSPX feedery; 



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
   
      //  balldetector = new Ultrasonic(1);

        feederx = new VictorSPX(Constants.feeder1); 
        feedery = new VictorSPX(Constants.feeder2); 
        feedery.setInverted(true);


     }
        
   /* public boolean checkDistance()
    {    
        if (balldetector.getRangeInches() >= Gains.Feeder.BallDetectorRange) {
            return true;
        }
        else {
            return false;
        }
    
    }
    */

    public void insertName(boolean feed)
    {if(feed){
        feederx.set(ControlMode.PercentOutput, -0.4);
     feedery.set(ControlMode.PercentOutput, -0.4);
    }else{
        feederx.set(ControlMode.PercentOutput, 0.0);
     feedery.set(ControlMode.PercentOutput, 0.0);
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