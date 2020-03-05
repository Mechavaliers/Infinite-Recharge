package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import ca.team4519.lib.IntakeLinkagePose;

import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake extends Subsystem implements Thread
{

    private Solenoid intakePivot;

    private final TalonSRX intakeRoller; 

    private static Intake thisInstance;
    
    private final VictorSPX intakeLinkageMotor;


    private Controllers controller = null;

    public interface Controllers
    {
        double update(IntakeLinkagePose pose);
    }

    private Intake()
    {
        intakeLinkageMotor = new VictorSPX(Constants.intakeLinkageMotor);
        intakeLinkageMotor.setInverted(true); //sets + to deploy and - to stow

        intakeRoller = new TalonSRX(Constants.intakeRoller);

        intakePivot = new Solenoid(Constants.intakePivot); //nicole - check cause new


    }

    public synchronized static Intake GrabInstance()
    {

        if(thisInstance == null)
        {
            thisInstance = new Intake();
        }

        return thisInstance;

    }

    public void setPower(double input)
    {   
        SmartDashboard.putNumber("Intake Controller Output", input);
        intakeLinkageMotor.set(ControlMode.PercentOutput, input);
    }

    public void wantIntake(boolean on, boolean off, int ballCount){
        if(on && ballCount != 5){
            intakeRoller.set(ControlMode.PercentOutput, 0.8);
            intakePivot.set(true);
        }
        else if(off)
        {
            intakeRoller.set(ControlMode.PercentOutput, -0.8);
        }
        else 
        {
            intakeRoller.set(ControlMode.PercentOutput, 0.0);
            intakePivot.set(false);
        }
            

    }

    @Override
    public void loops()
    {

       // setPower(controller.update(storedPose));
    }

    @Override
    public void zeroSensors()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableSubsystem()
    {
        controller = null;

    }

    @Override
    public void updateDashboard()
    {

    }
}