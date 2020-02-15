package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Flywheel extends Subsystem implements Thread{

	 private static Flywheel thisInstance;
	 
	 private final CANSparkMax rightWheelNeo;
	 private final CANEncoder rightWheelNeoEncoder;
	 private final CANSparkMax leftWheelNeo;
	 private final CANEncoder leftWheelNeoEncoder;
	 
     public interface Controllers
     {
     }
     
	 public synchronized static Flywheel GrabInstance()
    {

       if(thisInstance == null)
       {
           thisInstance = new Flywheel();
       }

       return thisInstance;

    }
     
    private Controllers controller = null;

	 private Flywheel()
	 {
		 rightWheelNeo = new CANSparkMax(Constants.rightWheelNeo, CANSparkMaxLowLevel.MotorType.kBrushless);
		 rightWheelNeo.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
		 rightWheelNeo.setSmartCurrentLimit(Constants.flywheelNeoCurrentLimit);
		 
		 rightWheelNeoEncoder = new CANEncoder(rightWheelNeo);
		 
		 leftWheelNeo = new CANSparkMax(Constants.leftWheelNeo, CANSparkMaxLowLevel.MotorType.kBrushless);
		 leftWheelNeo.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
		 leftWheelNeo.setSmartCurrentLimit(Constants.flywheelNeoCurrentLimit);
		 leftWheelNeo.follow(rightWheelNeo, true);
		 
		 leftWheelNeoEncoder = new CANEncoder(leftWheelNeo);
		 
		 
	        
	 }
	
    @Override
    public void loops() {
        // TODO anything that needs to be run at 200hz

    }

    @Override
    public void zeroSensors() { //nicole - just guessing based off drivebase, remove if needed

        rightWheelNeoEncoder.setPosition(0.0);
        leftWheelNeoEncoder.setPosition(0.0);

    }

    @Override
    public void disableSubsystem() {
        // TODO kill subsystem on call of this method

    }

    @Override
    public void updateDashboard() {

        SmartDashboard.putNumber("Right Wheel Neo Velocity", rightWheelNeoEncoder.getVelocity());
        SmartDashboard.putNumber("Right Wheel Neo Position", rightWheelNeoEncoder.getPosition());
        SmartDashboard.putNumber("Left Wheel Neo Velocity", leftWheelNeoEncoder.getVelocity());
        SmartDashboard.putNumber("Left Wheel Neo Position", leftWheelNeoEncoder.getPosition());

        if(controller == null)
        {
            SmartDashboard.putNumber("Flywheel ControllerOutput left", 0);
            SmartDashboard.putNumber("Flywheel ControllerOutput right", 0);

        }
        else
        {
            SmartDashboard.putNumber("Flywheel ControllerOutput left", 0);
            SmartDashboard.putNumber("Flywheel ControllerOutput right", 0);
        }
    }

    @Override
    public void feedLogger() {
        // TODO feed values to logger
    }

    @Override
    public void update() {
        feedLogger();
        updateDashboard();

    }

}