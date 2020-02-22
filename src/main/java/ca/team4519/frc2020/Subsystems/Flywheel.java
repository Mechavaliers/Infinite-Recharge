package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.controllers.FlywheelController;
import ca.team4519.lib.MechaLogger;
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
		 
         rightWheelNeoEncoder = new CANEncoder(rightWheelNeo);
         rightWheelNeoEncoder.setVelocityConversionFactor(Gains.NEO_TicksPerRev);
		 
		 leftWheelNeo = new CANSparkMax(Constants.leftWheelNeo, CANSparkMaxLowLevel.MotorType.kBrushless);
		 leftWheelNeo.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
		 leftWheelNeo.setSmartCurrentLimit(Constants.flywheelNeoCurrentLimit);
		 
         leftWheelNeoEncoder = new CANEncoder(leftWheelNeo);
         leftWheelNeoEncoder.setVelocityConversionFactor(Gains.NEO_TicksPerRev); 
     }
     
     public void testing(double input){
         rightWheelNeo.set(-input);
         leftWheelNeo.set(input);
         SmartDashboard.putNumber("Flywheel Controller ouput", input);
         
     }

     public void wantFlywheel()
     {
        controller = new FlywheelController(Gains.Flywheel.ShotRPM);
     }

     public void wantOff()
     {
        controller = null;
     }
	
    @Override
    public void loops() {
        if(controller == null) return;
        testing(controller.update(rightWheelNeoEncoder.getVelocity()));

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

    @Override
    public void feedLogger() {
        MechaLogger.grabInstance().logThis_Double("LeftFlywheel_Velocity", leftWheelNeoEncoder::getVelocity);
        MechaLogger.grabInstance().logThis_Double("RightFlywheel_Velocity", rightWheelNeoEncoder::getVelocity);
    }

    @Override
    public void update() {
        feedLogger();
        updateDashboard();

    }

}