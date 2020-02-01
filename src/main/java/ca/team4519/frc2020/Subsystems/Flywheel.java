package ca.team4519.frc2020.Subsystems;

import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Flywheel extends Subsystem implements Thread{

	 private static Flywheel thisInstance;
	 
	 private final CANSparkMax rightWheelNeoA;
	 private final CANEncoder rightWheelNeoAEncoder;
	 private final CANSparkMax leftWheelNeoB;
	 private final CANEncoder leftWheelNeoBEncoder;
	 
	 
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