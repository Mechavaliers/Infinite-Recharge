package ca.team4519.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team254.lib.trajectory.TrajectoryFollower;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.subsystems.controllers.TurretRotationController;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import ca.team4519.lib.TurretPose;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turret extends Subsystem implements Thread{

    public static Turret thisInstance;

    private Encoder turretPositionEncoder;
    private DigitalInput turretLimitL;
    private DigitalInput turretLimitR;
    private DigitalInput turretLimitHome;
    private TurretPose storedPose = new TurretPose(0.0, 0.0, 0.0, 0.0);

    public VictorSPX turretPivot;


    public synchronized static Turret grabInstance()
    {
        if(thisInstance == null)
        {
            thisInstance = new Turret();
        }

        return thisInstance;
    }

    public interface Controllers
    {
        double update(TurretPose pose);

    }

    private Controllers controller = null;

    private Turret()
    {
        turretPositionEncoder = new Encoder(Constants.turretEncoderA, Constants.turretEncoderB);
        turretPivot = new VictorSPX(Constants.turretPivotMotor);
        turretLimitL = new DigitalInput(Constants.turretLimitSwitchL);
        turretLimitR = new DigitalInput(Constants.turretLimitSwitchR);
        turretLimitHome = new  DigitalInput(Constants.turretHomeDetector);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);

    }

    //gets the controllers current setpoint, if the controller doesnt exist then it gets the turrets position
    public TrajectoryFollower.TrajectorySetpoint getSetpoint() {
		TrajectoryFollower.TrajectorySetpoint setpoint = null;
		if (controller instanceof TurretRotationController) {
			setpoint = ((TurretRotationController) controller).getSetpoint();
		}else {
			setpoint = new TrajectoryFollower.TrajectorySetpoint();
			setpoint.pos = storedPose.getConvertedValue();
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

    public boolean isTurretBoundHigh()
    {
        return turretLimitL.get();
    }

    public boolean isturretBoundLow()
    {
        return turretLimitR.get();
    }

    public boolean isTurretHome()
    {
        return turretLimitHome.get();
    }

    public double turretAngle()
    {
        return storedPose.getConvertedValue();
    }

    public double cameraToGoalAngle() // tx
    {
        return storedPose.getGoalOffset(); //difference between camera and goal
    }

    public double getWantedAngle() { 
        return cameraToGoalAngle() + turretAngle();
    }

    //Master override of turret aim, will update control loop if operator assigns new intent
    public void SetTurretIntent(boolean ForwardIntent, boolean RightIntent, boolean ReverseIntent, boolean LeftIntent)
    {
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
    }

    public double getHomedAngle()
    {
       double encoderPos;

        if(isTurretBoundHigh())
        {
            turretPositionEncoder.reset();
            encoderPos = Gains.Turret.turretAngle_EncoderHigh;
        }
        else if (isTurretHome())
        {
            turretPositionEncoder.reset();
            encoderPos = Gains.Turret.turretAngle_Zero;
        }
        else if (isturretBoundLow())
        {
           turretPositionEncoder.reset();
           encoderPos = Gains.Turret.turretAngle_EncoderLow;
        }
        else
        {
            encoderPos = turretPositionEncoder.get();
        }

        return encoderPos;
    }

    public void setPower(double power)
    {
      /*  if(isTurretBoundHigh() || isturretBoundLow())
        {
            if(storedPose.getConvertedValue() >= Gains.Turret.turretAngle_ConvertedHigh)
            {

            }
            else if (storedPose.getConvertedValue() <= Gains.Turret.turretAngle_ConvertedLow)
            {

            }
        }*/
        turretPivot.set(ControlMode.PercentOutput, power);

    }

    public TurretPose getTurretPose()
    {
        storedPose.reset(
            getHomedAngle(),
            turretPositionEncoder.getRate(),
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0), //1 retu
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0)
        );
        return storedPose;
    }

    @Override
    public void loops()
    {
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

        if(controller != null)
        {
            controller = null;
        }
        // TODO populate

    }

    @Override
    public void updateDashboard() {
        SmartDashboard.putNumber("Turret Encoder", turretPositionEncoder.get());
        //max vel is 500Ticks per second
        SmartDashboard.putNumber("Turret Velocity", turretPositionEncoder.getRate());
        SmartDashboard.putBoolean("Turret Hall Effect Sensor", isTurretHome());

    }

    @Override
    public void feedLogger() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        updateDashboard();

    }

}