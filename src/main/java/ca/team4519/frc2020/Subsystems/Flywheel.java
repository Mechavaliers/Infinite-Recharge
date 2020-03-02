package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.controllers.FlywheelController;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

public class Flywheel extends Subsystem implements Thread{

	 private static Flywheel thisInstance;
	 
	 private final CANSparkMax rightWheelNeo;
	 private final CANEncoder rightWheelNeoEncoder;
	 private final CANSparkMax leftWheelNeo;
	 private final CANEncoder leftWheelNeoEncoder;
	 
     public interface Controllers
     {
         double update(double rpm);
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
         rightWheelNeo.setIdleMode(IdleMode.kCoast);
		 
         rightWheelNeoEncoder = new CANEncoder(rightWheelNeo);
		 
		 leftWheelNeo = new CANSparkMax(Constants.leftWheelNeo, CANSparkMaxLowLevel.MotorType.kBrushless);
		 leftWheelNeo.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
         leftWheelNeo.setSmartCurrentLimit(Constants.flywheelNeoCurrentLimit);
         leftWheelNeo.setIdleMode(IdleMode.kCoast);
		 
         leftWheelNeoEncoder = new CANEncoder(leftWheelNeo);
     }
     
     public void testing(double input){
         rightWheelNeo.set(-input);
         leftWheelNeo.set(input);
         SmartDashboard.putNumber("Flywheel Controller ouput", input);
         
     }

     public void wantFlywheel()
     {
        controller = new FlywheelController(Gains.Flywheel.ShotRPM); //makes it spin
     }

     public void wantOff()
     {
        controller = new FlywheelController(Gains.Flywheel.Brake);
     }
	
    @Override
    public void loops() {
        if(controller == null) return;
        testing(controller.update(rightWheelNeoEncoder.getVelocity()));

    }

    public double getFlywheelSpeed() {
        return rightWheelNeoEncoder.getVelocity(); //always use right motor when getting these values
    }

    @Override
    public void zeroSensors() {//We have nothing to clear here... this might change later?
    }

    @Override
    public void disableSubsystem() {
        rightWheelNeo.disable();
        leftWheelNeo.disable();
        if (controller != null) controller = null;

    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("leftWheel Velocity", leftWheelNeoEncoder.getVelocity());
        SmartDashboard.putNumber("Right wheel Velocity", rightWheelNeoEncoder.getVelocity());
    }
}