package ca.team4519.frc2020.Subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.lib.DrivetrainOutput;
import ca.team4519.lib.MechaLogger;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivebase extends Subsystem implements Thread
{

    public static Drivebase thisInstance;

    private final CANSparkMax rightDriveNeoA;
    private final CANEncoder rightDriveNeoAEncoder;
    private final CANSparkMax rightDriveNeoB;
    private final CANEncoder rightDriveNeoBEncoder;
    private final CANSparkMax leftDriveNeoA;
    private final CANEncoder leftDriveNeoAEncoder;
    private final CANSparkMax leftDriveNeoB;
    private final CANEncoder leftDriveNeoBEncoder;

    private final Encoder leftDriveGrayhill;
    private final Encoder rightDriveGrayhill;

    private final DifferentialDriveOdometry odometry;

    private final Solenoid shifter;

    private final AHRS navX;

    public enum AutoPaths
    {
        NONE,FAST
    }

    public interface Controllers{
        DrivetrainOutput update(DifferentialDriveOdometry odometry);
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

        rightDriveNeoAEncoder = new CANEncoder(rightDriveNeoA);

        rightDriveNeoB = new CANSparkMax(Constants.rightDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        rightDriveNeoB.follow(rightDriveNeoA);

        rightDriveNeoBEncoder = new CANEncoder(rightDriveNeoB);

        leftDriveNeoA = new CANSparkMax(Constants.leftDriveNeoA, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);

        leftDriveNeoAEncoder = new CANEncoder(leftDriveNeoA);

        leftDriveNeoB = new CANSparkMax(Constants.leftDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        leftDriveNeoB.follow(rightDriveNeoA);
        
        leftDriveNeoBEncoder = new CANEncoder(leftDriveNeoB);

        leftDriveGrayhill = new Encoder(Constants.leftDriveGrayhillA, Constants.leftDriveGrayhillB, Constants.isLeftDriveGrayhillFlipped, CounterBase.EncodingType.k4X);
        leftDriveGrayhill.setDistancePerPulse(Gains.Drive.EncoderTicksPerRev);
        rightDriveGrayhill = new Encoder(Constants.rightDriveGrayhillA, Constants.rightDriveGrayhillB, Constants.isRightDriveGrayhillFlipped, CounterBase.EncodingType.k4X);
        rightDriveGrayhill.setDistancePerPulse(Gains.Drive.EncoderTicksPerRev);

        odometry = new DifferentialDriveOdometry(getAngle());

        shifter = new Solenoid(Constants.shifter);

        navX = new AHRS(SerialPort.Port.kMXP);
        
    }

    public DifferentialDriveOdometry getRobotPose(){
        odometry.update(getAngle(), getLeftDistanceMeters(), getRightDistanceMeters());
        return odometry;
    }

    public void shift(boolean triggerShift)
    {
        if(triggerShift)
        {
            shifter.set(Gains.Drive.Shifter_LOW_GEAR);
            MechaLogger.grabInstance().logThis_Bool("ShifterHighGear", () -> Gains.Drive.Shifter_LOW_GEAR);
        }
        else
        {
            shifter.set(Gains.Drive.Shifter_HIGH_GEAR);
            MechaLogger.grabInstance().logThis_Bool("ShifterHighGear", () -> Gains.Drive.Shifter_HIGH_GEAR);
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

        double finalThrottle = throttle;
        double finalTurn = turn;
        MechaLogger.grabInstance().logThis_Double("RawInput_Throttle", () -> finalThrottle);
        MechaLogger.grabInstance().logThis_Double("RawInput_Turn", () -> finalTurn);
		
		double right = throttle + turn;
		double left = throttle - turn;
		MechaLogger.grabInstance().logThis_Double("ArcadeOutput_Right", () -> right);
		MechaLogger.grabInstance().logThis_Double("ArcadeOutput_Left", () -> left);

		return new DrivetrainOutput(left, right);
    }

    public double getLeftDistanceMeters()
    {
       return ( ( (leftDriveNeoAEncoder.getPosition() + leftDriveNeoBEncoder.getPosition() ) / 2) * Constants.inchesToMeters);
    }

    public double getRightDistanceMeters()
    {
        return ( ( (rightDriveNeoAEncoder.getPosition() + rightDriveNeoBEncoder.getPosition() ) / 2) * Constants.inchesToMeters);
    }

    public Rotation2d getAngle()
    {
        return Rotation2d.fromDegrees(30);
    }

    @Override
    public void loops()
    {
        if(controller == null) return;

        odometry.update(getAngle(), getLeftDistanceMeters(), getRightDistanceMeters());
        setLeftRightPower(controller.update(getRobotPose()));
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
        if(controller != null) controller = null;
        setLeftRightPower(new DrivetrainOutput(0.0, 0.0));
    }

    @Override
    public void updateDashboard()
    {
        SmartDashboard.putNumber("Left Drive Neo A Velocity", leftDriveNeoAEncoder.getVelocity());
        SmartDashboard.putNumber("Left Drive Neo A Position", leftDriveNeoAEncoder.getPosition());
        SmartDashboard.putNumber("Left Drive Neo B Velocity", leftDriveNeoBEncoder.getVelocity());
        SmartDashboard.putNumber("Left Drive Neo B Position", leftDriveNeoBEncoder.getPosition());
        SmartDashboard.putNumber("Right Drive Neo A Velocity", rightDriveNeoAEncoder.getVelocity());
        SmartDashboard.putNumber("Right Drive Neo A Position", rightDriveNeoAEncoder.getPosition());
        SmartDashboard.putNumber("Right Drive Neo B Velocity", rightDriveNeoBEncoder.getVelocity());
        SmartDashboard.putNumber("Right Drive Neo B Position", rightDriveNeoBEncoder.getPosition());
    }

    @Override
    public void feedLogger()
    {
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoA_Velocity", leftDriveNeoAEncoder::getVelocity);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoA_Position", leftDriveNeoAEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoB_Velocity", leftDriveNeoBEncoder::getVelocity);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoB_Position", leftDriveNeoBEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoB_Position", leftDriveNeoBEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoB_Position", rightDriveNeoBEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoB_Position", rightDriveNeoAEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("RightDriveNeoB_Position", rightDriveNeoBEncoder::getPosition);
    }

    @Override
    public void update()
    {
      updateDashboard();
      feedLogger();
    }

    public void test()
    {

    }

}