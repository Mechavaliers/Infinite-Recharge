package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

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
     
     public void testing(double input){
         rightWheelNeo.set(input);
         leftWheelNeo.set(-input);
         
     }

     public void wantSpin()
     {
         //rightW
     }
	
    @Override
    public void loops() {
        // TODO anything that needs to be run at 200hz

    }

    @Override
    public void zeroSensors() {
        // TODO reset count on all sensors

    }

    @Override
    public void disableSubsystem() {
        rightWheelNeo.disable();
        leftWheelNeo.disable();

    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("leftWheel Velocity", leftWheelNeoEncoder.getVelocity());
        SmartDashboard.putNumber("Right wheel Velocity", rightWheelNeoEncoder.getVelocity());

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