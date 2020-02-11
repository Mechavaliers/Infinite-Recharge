package ca.team4519.frc2020.subsystems;

import java.util.Locale.IsoCountryCode;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.subsystems.controllers.TurretRotationController;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import ca.team4519.lib.TurretPose;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.networktables.*;

public class Turret extends Subsystem implements Thread{

    public static Turret thisInstance;

    private Encoder turretPositionEncoder;
    private DigitalInput turretLimitL;
    private DigitalInput turretLimitR;
    private DigitalInput turretLimitHome;
    private TurretPose storedPose = new TurretPose(0.0, 0.0, 0.0, 0.0);
    private double wantedAngle = 0.0;

    private VictorSPX turretPivot; //nicole


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
        turretPivot = new VictorSPX(Constants.intakeLinkageWheel); //this is a motor, only takes a single number
        turretLimitL = new DigitalInput(Constants.turretLimitSwitchL);
        turretLimitR = new DigitalInput(Constants.turretLimitSwitchR);
        turretLimitHome = new  DigitalInput(Constants.turretHomeDetector);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);

    }

    public void wantAimedTurret()
    {
        controller = new TurretRotationController(getTurretPose(), (double) NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
    }

    public void aimControl()
    {
        getTurretPose();
        if(storedPose.hasValidGoal())
        {
            wantAimedTurret();
        }

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

    public double turretAngle() // t theta
    {
        return 8; //TODO update with real math
    }

    public double cameraToGoalAngle() // tx
    {
        return storedPose.getGoalOffset(); //difference between camera and goal
    }

    public double wantedAngleRetriever() { 
        return cameraToGoalAngle() + turretAngle();
    }

    public void snapToGoal() {

        double wantedAngle = wantedAngleRetriever(); 

        if((wantedAngle <= Gains.Turret.NegativeACtoAA) || (wantedAngle >= Gains.Turret.PositiveABtoAA))
        {
            //Possible dead zone
            //TurretRotationController(getTurretPose(), (double) wantedAngle);
           
        }
        else
        {
            //aim and fire to wantedangle
        }

    } //end of method


    public TurretPose getTurretPose()
    {
        storedPose.reset(
            turretPositionEncoder.get(),
            turretPositionEncoder.getRate(),
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0), //1 retu
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0)
        );
        return storedPose;
    }

    @Override
    public void loops() {
        getTurretPose();

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
        // TODO Auto-generated method stub

    }

    @Override
    public void feedLogger() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}