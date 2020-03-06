package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.controllers.DriveLineController;
import ca.team4519.frc2020.subsystems.controllers.DrivebaseLockController;
import ca.team4519.lib.pose.DrivebasePose;
import ca.team4519.lib.DrivetrainOutput;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

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

    private final Solenoid shifter;

    private final double dt = Gains.CONTROL_LOOP_TIME_SECONDS;

    private double lastLeftVel = 0;
	private double lastRightVel = 0;
	private double leftAccel = 0;
	private double rightAccel = 0;

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
        rightDriveNeoA.setInverted(false);
        rightDriveNeoA.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoA.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);

        rightDriveNeoAEncoder = new CANEncoder(rightDriveNeoA);
        rightDriveNeoAEncoder.setPositionConversionFactor(Gains.Drive.NEO_HIGH_CorrectedTicksPerRev);
        rightDriveNeoAEncoder.setVelocityConversionFactor(1/Gains.Drive.NEO_HIGH_CorrectedTicksPerRev);

        rightDriveNeoB = new CANSparkMax(Constants.rightDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setInverted(false);
        rightDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        rightDriveNeoB.follow(rightDriveNeoA);

        rightDriveNeoBEncoder = new CANEncoder(rightDriveNeoB);
        rightDriveNeoBEncoder.setPositionConversionFactor(Gains.Drive.NEO_HIGH_CorrectedTicksPerRev);
        rightDriveNeoBEncoder.setVelocityConversionFactor(1/Gains.Drive.NEO_HIGH_CorrectedTicksPerRev);

        leftDriveNeoA = new CANSparkMax(Constants.leftDriveNeoA, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setInverted(true);
        leftDriveNeoA.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);

        leftDriveNeoAEncoder = new CANEncoder(leftDriveNeoA);
        leftDriveNeoAEncoder.setPositionConversionFactor(Gains.Drive.NEO_HIGH_CorrectedTicksPerRev);
        leftDriveNeoAEncoder.setVelocityConversionFactor(1/Gains.Drive.NEO_HIGH_CorrectedTicksPerRev);

        leftDriveNeoB = new CANSparkMax(Constants.leftDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setInverted(true);
        leftDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        leftDriveNeoB.follow(leftDriveNeoA);
        
        leftDriveNeoBEncoder = new CANEncoder(leftDriveNeoB);
        leftDriveNeoBEncoder.setPositionConversionFactor(Gains.Drive.NEO_HIGH_CorrectedTicksPerRev);
        leftDriveNeoBEncoder.setVelocityConversionFactor(1/Gains.Drive.NEO_HIGH_CorrectedTicksPerRev);

        odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0));

        shifter = new Solenoid(Constants.shifter);

        navX = new AHRS(SerialPort.Port.kMXP);
    }

    public void getAccel() {
		getLeftAccel();
		getRightAccel();
	}
	
	public void getLeftAccel() {
		double curVel = leftDriveNeoAEncoder.getVelocity();
		double dv = curVel - lastLeftVel;
		lastLeftVel = curVel;
		double accel = dv/dt;
		leftAccel = accel;
	}

	
	public void getRightAccel() {
		double curVel = rightDriveNeoAEncoder.getVelocity();
		double dv = curVel - lastRightVel;
		lastRightVel = curVel;
		double accel = dv/dt;
		rightAccel = accel;
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

    public void shift(boolean triggerShift)
    {
        if(triggerShift)
        {
            shifter.set(Gains.Drive.Shifter_LOW_GEAR);
         /*   rightDriveNeoAEncoder.setPositionConversionFactor(Gains.Drive.LOW_EncoderTicksPerRev);
            rightDriveNeoAEncoder.setVelocityConversionFactor(Gains.Drive.LOW_EncoderTicksPerRev);
            rightDriveNeoBEncoder.setPositionConversionFactor(Gains.Drive.LOW_EncoderTicksPerRev);
            rightDriveNeoBEncoder.setVelocityConversionFactor(Gains.Drive.LOW_EncoderTicksPerRev);
            leftDriveNeoAEncoder.setPositionConversionFactor(Gains.Drive.LOW_EncoderTicksPerRev);
            leftDriveNeoAEncoder.setVelocityConversionFactor(Gains.Drive.LOW_EncoderTicksPerRev);
            leftDriveNeoBEncoder.setPositionConversionFactor(Gains.Drive.LOW_EncoderTicksPerRev);
            leftDriveNeoBEncoder.setVelocityConversionFactor(Gains.Drive.LOW_EncoderTicksPerRev);*/
        }
        else
        {
            shifter.set(Gains.Drive.Shifter_HIGH_GEAR);
          /*  rightDriveNeoAEncoder.setPositionConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
            rightDriveNeoAEncoder.setVelocityConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
            rightDriveNeoBEncoder.setPositionConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
            rightDriveNeoBEncoder.setVelocityConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
            leftDriveNeoAEncoder.setPositionConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
            leftDriveNeoAEncoder.setVelocityConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
            leftDriveNeoBEncoder.setPositionConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);
            leftDriveNeoBEncoder.setVelocityConversionFactor(Gains.Drive.HIGH_EncoderTicksPerRev);*/
        }
    }

    public boolean isHighGear()
    {
        return (shifter.get() == Gains.Drive.Shifter_HIGH_GEAR);
    }

    public void setLeftRightPower(DrivetrainOutput power)
    {
        leftDriveNeoA.set(power.leftOutput);
        rightDriveNeoA.set(power.rightOutput);
    }

    public DrivetrainOutput arcade(double throttle, double turn)
    {
		throttle = (Math.abs(throttle) > Math.abs(0.04))? throttle : 0.0;
		turn = (Math.abs(turn) > Math.abs(0.04))? turn : 0.0;
		
		double right = -throttle - turn;
		double left = -throttle + turn;

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

    getLeftAccel();
    getRightAccel();
        getRobotPose();
        if(controller == null) return;

        //odometry.update(getAngle(), getLeftDistanceMeters(), getRightDistanceMeters());
        setLeftRightPower(controller.update(storedPose));
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
        SmartDashboard.putNumber("Velocity Converseion factor", leftDriveNeoAEncoder.getVelocityConversionFactor());
        SmartDashboard.putNumber("Left Drive Neo A Position", leftDriveNeoAEncoder.getPosition());
        SmartDashboard.putNumber("Left Drive Neo B Velocity", leftDriveNeoBEncoder.getVelocity());
        SmartDashboard.putNumber("Left Drive Neo B Position", leftDriveNeoBEncoder.getPosition());
        SmartDashboard.putNumber("Right Drive Neo A Velocity", rightDriveNeoAEncoder.getVelocity());
        SmartDashboard.putNumber("Right Drive Neo A Position", rightDriveNeoAEncoder.getPosition());
        SmartDashboard.putNumber("Right Drive Neo B Velocity", rightDriveNeoBEncoder.getVelocity());
        SmartDashboard.putNumber("Right Drive Neo B Position", rightDriveNeoBEncoder.getPosition());

        SmartDashboard.putNumber("Left Acceleration", leftAccel);
        SmartDashboard.putNumber("Right Acceleration", rightAccel);

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
}