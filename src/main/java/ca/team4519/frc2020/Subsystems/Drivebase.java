package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.controllers.DriveLineController;
import ca.team4519.frc2020.subsystems.controllers.DrivebaseLockController;
import ca.team4519.lib.pose.DrivebasePose;
import ca.team4519.lib.DrivetrainOutput;
import ca.team4519.lib.MechaLogger;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.team254.lib.trajectory.TrajectoryFollower;

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
    private final DifferentialDriveOdometry odometry;

    private DrivebasePose storedPose = new DrivebasePose(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null);

   // private final Solenoid shifter;

    private final AHRS navX;

    public enum AutoPaths
    {
        NONE,FAST
    }

    public interface Controllers{
        DrivetrainOutput update(DrivebasePose pose);
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
        rightDriveNeoAEncoder.setPositionConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
        rightDriveNeoAEncoder.setVelocityConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
        rightDriveNeoAEncoder.setMeasurementPeriod(Gains.CONTROL_LOOP_TIME_MILLISECOND);

        rightDriveNeoB = new CANSparkMax(Constants.rightDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        rightDriveNeoB.follow(rightDriveNeoA);

        rightDriveNeoBEncoder = new CANEncoder(rightDriveNeoB);
        rightDriveNeoBEncoder.setPositionConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
        rightDriveNeoBEncoder.setVelocityConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
        rightDriveNeoBEncoder.setMeasurementPeriod(Gains.CONTROL_LOOP_TIME_MILLISECOND);

        leftDriveNeoA = new CANSparkMax(Constants.leftDriveNeoA, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);

        leftDriveNeoAEncoder = new CANEncoder(leftDriveNeoA);
        leftDriveNeoAEncoder.setPositionConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
        leftDriveNeoAEncoder.setVelocityConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
        leftDriveNeoAEncoder.setMeasurementPeriod(Gains.CONTROL_LOOP_TIME_MILLISECOND);

        leftDriveNeoB = new CANSparkMax(Constants.leftDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        leftDriveNeoB.follow(rightDriveNeoA);
        
        leftDriveNeoBEncoder = new CANEncoder(leftDriveNeoB);
        leftDriveNeoBEncoder.setPositionConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
        leftDriveNeoBEncoder.setVelocityConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
        leftDriveNeoBEncoder.setMeasurementPeriod(Gains.CONTROL_LOOP_TIME_MILLISECOND);

        odometry = new DifferentialDriveOdometry(getAngle());

       // shifter = new Solenoid(Constants.shifter);

        navX = new AHRS(SerialPort.Port.kMXP);
    }

    public DrivebasePose getRobotPose() {
        storedPose.reset(
            getLeftDistance(), 
            getRightDistance(), 
            getLeftVelocity(), 
            getRightVelocity(), 
            navX.getAngle(), 
            navX.getRate(),
            odometry.update(Rotation2d.fromDegrees(navX.getAngle()), getLeftDistance(), getRightDistance()));          
        return storedPose;
    }

    
    public void wantDriveLine(double target, double maxVelocity)
    {
        double maxVel = Math.min(maxVelocity, Gains.Drive.ROBOT_MAX_VELOCITY);
        controller = new DriveLineController(getRobotPose(), target, maxVel);
    }

    public void wantDriveLine(double target)
    {
        wantDriveLine(target, Gains.Drive.ROBOT_MAX_VELOCITY);
    }

    public void wantHoldPos()
    {
        DrivebasePose poseToHold = getRobotPose();

        controller = new DrivebaseLockController(poseToHold);

    }

    private double getLeftDistance() {
        return (leftDriveNeoAEncoder.getPosition() + leftDriveNeoBEncoder.getPosition()) / 2;
    }

    private double getRightDistance() {
        return (rightDriveNeoAEncoder.getPosition() + rightDriveNeoBEncoder.getPosition()) / 2;
    }

    private double getLeftVelocity() {
        return (leftDriveNeoAEncoder.getVelocity() + leftDriveNeoBEncoder.getVelocity()) / 2;
    }

    private double getRightVelocity() {
        return (rightDriveNeoAEncoder.getVelocity() + rightDriveNeoBEncoder.getVelocity()) / 2;
    }
/*
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
*/
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
        return Rotation2d.fromDegrees(navX.getAngle());
    }

    @Override
    public void loops()
    {
        if(controller == null) return;

        //odometry.update(getAngle(), getLeftDistanceMeters(), getRightDistanceMeters());
        setLeftRightPower(controller.update(getRobotPose()));
    }

    @Override
    public void zeroSensors()
    {
        rightDriveNeoAEncoder.setPosition(0.0);
        rightDriveNeoBEncoder.setPosition(0.0);
        leftDriveNeoAEncoder.setPosition(0.0);
        leftDriveNeoBEncoder.setPosition(0.0);

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
        SmartDashboard.putNumber("Drivebase Angle", navX.getAngle());

        if(controller != null)
        {
            SmartDashboard.putString("Drivebase ControllerOutput", controller.update(storedPose).toString());
        }
        else
        {
            SmartDashboard.putString("Drivebase ControllerOutput", "No Controller");
        }
    }

    @Override
    public void feedLogger()
    {
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoA_Velocity", leftDriveNeoAEncoder::getVelocity);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoA_Position", leftDriveNeoAEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoB_Velocity", leftDriveNeoBEncoder::getVelocity);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoA_Position", leftDriveNeoAEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("LeftDriveNeoB_Position", leftDriveNeoBEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("RightDriveNeoA_Position", rightDriveNeoAEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("RightDriveNeoB_Position", rightDriveNeoBEncoder::getPosition);
        MechaLogger.grabInstance().logThis_Double("Drivebase Angle", navX::getAngle);

        if(controller != null)
        {
            MechaLogger.grabInstance().logThis_String("Drivebase_ControllerOutput", controller.update(storedPose)::toString);
        }
        else
        {
            MechaLogger.grabInstance().logThis_String("Drivebase_ControllerOutput", () -> (String) "No controller");
        }


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