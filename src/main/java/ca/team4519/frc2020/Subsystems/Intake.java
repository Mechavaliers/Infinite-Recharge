package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.controllers.IntakeLinkageController;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.Timer;
import ca.team4519.lib.IntakeLinkagePose;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake extends Subsystem implements Thread
{

    private static Intake thisInstance;

    //private final Vic;

    private final AnalogPotentiometer armPositionPot;
    private final Encoder armPositionEncoder;
    
    private final TalonSRX intakeLinkageMotor;

    private IntakeLinkagePose storedPose = new IntakeLinkagePose(0,0);
    private boolean potRateFirstRun = true;

    private Controllers controller = null;

    public interface Controllers
    {
        double update(IntakeLinkagePose pose);
    }

    private Intake()
    {
        armPositionPot = new AnalogPotentiometer(Constants.IntakeArmPot);
        armPositionEncoder = new Encoder(Constants.intakeLinkageEncoderA, Constants.intakeLinkageEncoderB);

        intakeLinkageMotor = new TalonSRX(Constants.intakeLinkageMotor);

    }

    public synchronized static Intake GrabInstance()
    {

        if(thisInstance == null)
        {
            thisInstance = new Intake();
        }

        return thisInstance;

    }

    public double getPotConvertedValue()
    {
        final double slope = (Gains.Intake.LinkagePot_MaxAngle - Gains.Intake.LinkagePot_MinAngle) / (Gains.Intake.LinkagePot_MaxVoltage - Gains.Intake.LinkagePot_MinVolage);
        //this is our b value
        final double offset = Gains.Intake.LinkagePot_MinAngle - slope * Gains.Intake.LinkagePot_MinVolage;
        // y = mx=b
        return (slope * armPositionPot.get() + offset);
    }

    public double getIntakeLinkageVelocity()
    {
        return armPositionEncoder.getRate();
    }

    public void wantDeployIntake()
    {
        if(!(controller instanceof IntakeLinkageController))
        {
            controller = new IntakeLinkageController(getIntakePose(), Gains.Intake.LinkagePos_Deployed);
        }

        ((IntakeLinkageController)controller).changeSetpoint(((IntakeLinkageController)controller).getSetpoint(), Gains.Intake.LinkagePos_Deployed);
    }

    public void wantStowIntake()
    {
        controller = new IntakeLinkageController(getIntakePose(), Gains.Intake.LinkagePos_Stowed);
    }

    public void wantsIntake(boolean wantsIntake)
    {
   	 	if (wantsIntake) {
   	 		// drop intake
   	 	}
    }

    public IntakeLinkagePose getIntakePose()
    {
        storedPose.reset(getPotConvertedValue(), getIntakeLinkageVelocity());
        return storedPose;
    }

    @Override
    public void loops()
    {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

    }

    @Override
    public void feedLogger()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub

    }

}