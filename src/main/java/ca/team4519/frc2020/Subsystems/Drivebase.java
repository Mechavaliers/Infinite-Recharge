package ca.team4519.frc2020.Subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.lib.DrivetrainOutput;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import ca.team4519.lib.controllers.Controller;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;;

public class Drivebase extends Subsystem implements Thread
{

    public static Drivebase thisInstance;

    private final CANSparkMax rightDriveNeoA;
    private final CANEncoder rightDriveNeoEncoderA;
    private final CANSparkMax rightDriveNeoB;
    private final CANEncoder rightDriveNeoEncoderB;
    private final CANSparkMax leftDriveNeoA;
    private final CANEncoder leftDriveNeoEncoderA;
    private final CANSparkMax leftDriveNeoB;
    private final CANEncoder leftDriveNeoEncoderB;

    private final Encoder leftDriveGrayhill;
    private final Encoder rightDriveGrayhill;

    private final Solenoid shifter;

    private final AHRS navX;

    public enum AutoPaths{
        NONE,FAST;
    }

    public interface Controllers{
        DrivetrainOutput update(new DrivetrainOutput(0.0,0.0)); //TODO Interface with this properly
    }


    public synchronized static Drivebase GrabInstance()
    {
        if(thisInstance == null)
        {
            thisInstance = new Drivebase();
        }

        return thisInstance;

    }

    private Controllers controller = null;

    private Drivebase()
    {

        rightDriveNeoA = new CANSparkMax(Constants.rightDriveNeoA, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoA.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoA.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);

        rightDriveNeoEncoderA = new CANEncoder(rightDriveNeoA);

        rightDriveNeoB = new CANSparkMax(Constants.rightDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        rightDriveNeoB.follow(rightDriveNeoA);

        rightDriveNeoEncoderB = new CANEncoder(rightDriveNeoB);

        leftDriveNeoA = new CANSparkMax(Constants.leftDriveNeoA, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);

        leftDriveNeoEncoderA = new CANEncoder(leftDriveNeoA);

        leftDriveNeoB = new CANSparkMax(Constants.leftDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        leftDriveNeoB.follow(rightDriveNeoA);
        
        leftDriveNeoEncoderB = new CANEncoder(leftDriveNeoB);

        leftDriveGrayhill = new Encoder(Constants.leftDriveGrayhillA, Constants.leftDriveGrayhillB, Constants.isLeftDriveGrayhillFlipped, CounterBase.EncodingType.k4X);
        leftDriveGrayhill.setDistancePerPulse(Gains.Drive.EncoderTicksPerRev);
        rightDriveGrayhill = new Encoder(Constants.rightDriveGrayhillA, Constants.rightDriveGrayhillB, Constants.isRightDriveGrayhillFlipped, CounterBase.EncodingType.k4X);
        rightDriveGrayhill.setDistancePerPulse(Gains.Drive.EncoderTicksPerRev);

        shifter = new Solenoid(Constants.shifter);

        navX = new AHRS(SerialPort.Port.kMXP);
        
    }

    public void shift(boolean triggerShift)
    {
        if(triggerShift)
        {
            shifter.set(Gains.Drive.Shifter_LOW_GEAR);
            
        }
        else
        {
            shifter.set(Gains.Drive.Shifter_HIGH_GEAR);
        }
    }

    public boolean isHighGear()
    {
        return (shifter.get() == Gains.Drive.Shifter_HIGH_GEAR);
    }

    public void setLeftRightPower(DrivetrainOutput power)
    {
        rightDriveNeoA.set(power.rightOutput);
        leftDriveNeoA.set(power.leftOutput);
    }

    public DrivetrainOutput arcade(double throttle, double turn)
    {
		throttle = (Math.abs(throttle) > Math.abs(0.03))? throttle : 0.0;
		turn = (Math.abs(turn) > Math.abs(0.03))? turn : 0.0;
		
		double right = throttle + turn;
		double left = throttle - turn;	
				
		return new DrivetrainOutput(left, right);
    }

    @Override
    public void loops()
    {
        if(controller == null)
        {
            return;
        }

        // TODO Auto-generated method stub

    }

    @Override
    public void zeroSensors()
    {
        rightDriveGrayhill.reset();
        leftDriveGrayhill.reset();
        navX.reset();

    }

    @Override
    public void disableSubsystem()
    {
        if(controller != null)
        {
            controller = null;
        }
        setLeftRightPower(new DrivetrainOutput(0.0, 0.0 ));
        // TODO Auto-generated method stub

    }

    @Override
    public void update()
    {
        SmartDashboard.putNumber("Left Drive Neo A Velocity", leftDriveNeoEncoderA.getVelocity());
        SmartDashboard.putNumber("Left Drive Neo A Position", leftDriveNeoEncoderA.getPosition());
        SmartDashboard.putNumber("Left Drive Neo B Velocity", leftDriveNeoEncoderB.getVelocity());
        SmartDashboard.putNumber("Left Drive Neo B Position", leftDriveNeoEncoderB.getPosition());
        SmartDashboard.putNumber("Right Drive Neo A Velocity", rightDriveNeoEncoderA.getVelocity());
        SmartDashboard.putNumber("Right Drive Neo A Position", rightDriveNeoEncoderA.getPosition());
        SmartDashboard.putNumber("Right Drive Neo B Velocity", rightDriveNeoEncoderB.getVelocity());
        SmartDashboard.putNumber("Right Drive Neo B Position", rightDriveNeoEncoderB.getPosition());
    }

    public void test()
    {

    }

}