package ca.team4519.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team254.lib.trajectory.TrajectoryFollower;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.subsystems.controllers.TurretRotationController;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import ca.team4519.lib.pose.TurretPose;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends Subsystem implements Thread
{

    public static Turret thisInstance;

    private Encoder turretPositionEncoder;
    private DigitalInput turretLimitHome;
    private TurretPose storedPose = new TurretPose(0.0, 0.0, 0.0, 0.0);

    private double lastVel = 0;
	private double accel = 0;
	private double maxAccel = accel;

    public VictorSPX turretPivot;


    public synchronized static Turret grabInstance()
    {
        if(thisInstance == null) thisInstance = new Turret();
        return thisInstance;
    }

    public interface Controllers
    {
        double update(TurretPose pose);

    }

    private Controllers controller = null;

    private Turret()
    {
        turretPositionEncoder = new Encoder(Constants.turretEncoderA, Constants.turretEncoderB, false, EncodingType.k4X);
        turretPositionEncoder.setDistancePerPulse(1/8.05555555555);
        turretPivot = new VictorSPX(Constants.turretPivotMotor);
        turretLimitHome = new  DigitalInput(Constants.turretHomeDetector);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);

    }

    //gets the controllers current setpoint, if the controller doesnt exist then it gets the turrets position
    public TrajectoryFollower.TrajectorySetpoint getSetpoint()
    {
		TrajectoryFollower.TrajectorySetpoint setpoint = null;
		if (controller instanceof TurretRotationController) {
			setpoint = ((TurretRotationController) controller).getSetpoint();
		}else {
			setpoint = new TrajectoryFollower.TrajectorySetpoint();
			setpoint.pos = storedPose.getPosition();
		}
		return setpoint;
		
	}

    /**
    *Safely re-aims the turret
    *   Checks if aim controller is running
    *       no? then create a new instance
    *       yes? then just change the setpoint of the current controller
    * @param targetPos The desired turret angle.
    */
    public void aimTurretAtPos(double targetPos)
    {

        if(!(controller instanceof TurretRotationController))
        {
            controller = new TurretRotationController(getTurretPose(), targetPos);
        }

        ((TurretRotationController)controller).changeSetpoint(((TurretRotationController)controller).getSetpoint(), targetPos);
    }


    public boolean isTurretHome()
    {
        return !turretLimitHome.get();
    }

    public double cameraToGoalAngle() // tx
    {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0); //difference between camera and goal
    }

    public double getWantedAngle() { 
        return cameraToGoalAngle() + getHomedAngle();
    }

    //Master control of turret aim, will update control loop if operator assigns new intent
    public void SetTurretIntent(Joystick input, boolean ForwardIntent, boolean RightIntent, boolean ReverseIntent, boolean LeftIntent, boolean autoaim)
    {
        switch (input.getPOV()) {
            case 0:
                //top
                aimTurretAtPos(Gains.Turret.topAngle);
                break;
            case 45:
                //topRight
                aimTurretAtPos(Gains.Turret.topRightAngle);
                break;
            case 90:
                //right
                aimTurretAtPos(Gains.Turret.rightAngle);
                break;
            case 135:
                //bottomRight
                aimTurretAtPos(Gains.Turret.bottomRightAngle);
                break;
            case 180:
                //bottom
                aimTurretAtPos(Gains.Turret.bottomAngle);
                break;
            case 225:
                //bottomLeft - deadzone
                break;
            case 270:
                //left
                aimTurretAtPos(Gains.Turret.leftAngle);
                break;
            case 315:
                //upperLeft
                aimTurretAtPos(Gains.Turret.topLeftAngle);
                break;
            case -1:
                //notPressed
                if(limelightHasValidTarget()) aimTurretAtPos(getWantedAngle());
                break;
            default:
                break;
        }

        if(ForwardIntent)
        {
            aimTurretAtPos(Gains.Turret.Intent_ForwardConverted);
        }
        else if (RightIntent)
        {
            aimTurretAtPos(Gains.Turret.Intent_RightConverted);
        }
        else if (ReverseIntent)
        {
            aimTurretAtPos(Gains.Turret.Intent_ReverseConverted);
        }
        else if (LeftIntent)
        {
            aimTurretAtPos(Gains.Turret.Intent_LeftConverted);
        }
        else if (autoaim)
        {
            aimTurretAtPos(getWantedAngle());
        }
    }

    public double getHomedAngle()
    {

        if (isTurretHome()) turretPositionEncoder.reset();
        return turretPositionEncoder.getDistance();
    }

    public void setPower(double power)
    {
        if(power > 0.5){
            power = 0.5;
        }else if(power < -0.5){
            power = -0.5;
        }

        turretPivot.set(ControlMode.PercentOutput, power);

    }

    public boolean limelightHasValidTarget()
    {
        return  NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1? true : false;
    }

    public TurretPose getTurretPose()
    {
        storedPose.reset(
            getHomedAngle(),
            turretPositionEncoder.getRate(),
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0), //1 retu
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0) //invert this
        );
        return storedPose;
    }

    @Override
    public void loops()
    {
        getAccel();
        getTurretPose();

        //if no controller is running, exit loop
        if(controller == null) return;
        setPower(controller.update(storedPose));

    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub
    }

    @Override
    public void disableSubsystem() {

        if(controller != null) controller = null;

    }

        public void getAccel() {
            double curVel = Math.abs(turretPositionEncoder.getRate());
            double dv = curVel - lastVel;
            lastVel = curVel;
            double _accel = dv/Gains.CONTROL_LOOP_TIME_SECONDS;
            accel = _accel;
            maxAccel = (Math.abs(accel) > Math.abs(maxAccel))? accel : maxAccel;
        }


    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Turret Encoder", turretPositionEncoder.getDistance());
        //max vel is 500Ticks per second then it was 5000
        SmartDashboard.putNumber("Wanted angle turret", getWantedAngle());
        SmartDashboard.putNumber("Turret Velocity", turretPositionEncoder.getRate());
        SmartDashboard.putNumber("Turret Acceleration", accel);
        SmartDashboard.putNumber("Turret Max Acceleration", maxAccel);
        SmartDashboard.putBoolean("Turret Hall Effect Sensor", isTurretHome());
        SmartDashboard.putNumber("Camera angle to goal", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
        SmartDashboard.putBoolean("Camera has valid goal?",  NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) ==1? true:false);

    }
}