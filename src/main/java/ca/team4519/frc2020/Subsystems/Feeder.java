package ca.team4519.frc2020.subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Feeder extends Subsystem implements Thread
{

    private static Feeder thisInstance;

    private VictorSPX feederx; 

    private VictorSPX feedery; 

    private final DigitalInput ballDetector;

    private boolean toggle = false;
    private boolean safeReverse = true;
    private int ballCount = 0;

    public synchronized static Feeder GrabInstance()
    {

       if(thisInstance == null) thisInstance = new Feeder();
       return thisInstance;
    }
     
    private Feeder()
    {

        feederx = new VictorSPX(Constants.feeder1); 
        feedery = new VictorSPX(Constants.feeder2); 
        feedery.setInverted(true);

        ballDetector = new DigitalInput(Constants.ballDetector);

     }

    public boolean handoffReady()
    {
        return !ballDetector.get();
    }

    public void insertName(boolean feed)
    {if(feed){
        feederx.set(ControlMode.PercentOutput, -0.4);
     feedery.set(ControlMode.PercentOutput, -0.4);
    }else{
        feederx.set(ControlMode.PercentOutput, 0.0);
     feedery.set(ControlMode.PercentOutput, 0.0);
    }
     
    }

    public void indexOne(){

        switch (ballCount) {
            case 1:
                feederx.set(ControlMode.PercentOutput, -1.0);
                feedery.set(ControlMode.PercentOutput, 0.2);
                Timer.delay(Gains.Feeder.IndexTime+0.0625);
                feederx.set(ControlMode.PercentOutput, 0.0);
                feedery.set(ControlMode.PercentOutput, 0.0);
                break;
            case 2:
                feederx.set(ControlMode.PercentOutput, -1.0);
                feedery.set(ControlMode.PercentOutput, 0.2);
                Timer.delay(Gains.Feeder.IndexTime+0.0625);
                feederx.set(ControlMode.PercentOutput, 0.0);
                feedery.set(ControlMode.PercentOutput, 0.0);
                break;
            case 3:
                feederx.set(ControlMode.PercentOutput, -1.0);
                feedery.set(ControlMode.PercentOutput, 0.2);
                Timer.delay(Gains.Feeder.IndexTime+0.0625);
                feederx.set(ControlMode.PercentOutput, 0.0);
                feedery.set(ControlMode.PercentOutput, 0.0);
                break;
            case 4:
                feederx.set(ControlMode.PercentOutput, -1.0);
                feedery.set(ControlMode.PercentOutput, 0.2);
                Timer.delay(Gains.Feeder.IndexTime+0.0625);
                feederx.set(ControlMode.PercentOutput, 0.0);
                feedery.set(ControlMode.PercentOutput, 0.0);
                break;
            case 5:
                feederx.set(ControlMode.PercentOutput, -1.0);
                feedery.set(ControlMode.PercentOutput, -0.2);
                Timer.delay(Gains.Feeder.IndexTime+0.0625);
                feederx.set(ControlMode.PercentOutput, 0.375);
                feedery.set(ControlMode.PercentOutput, 0.375);
                Timer.delay(Gains.Feeder.IndexTime+0.0625);
                feederx.set(ControlMode.PercentOutput, 0.0);
                feedery.set(ControlMode.PercentOutput, 0.0);
                Lights.GrabInstance().wantBlink();
                break;
            default:
                feederx.set(ControlMode.PercentOutput, 0.0);
                feedery.set(ControlMode.PercentOutput, 0.0);  
                break;
        }
    }

    public int getBallCount()
    {
        return ballCount;
    }

    public void setBallCount(int assignBallCount)
    {
        ballCount = assignBallCount;
    }

    public void indexToggle(boolean button) {
        if (!button) {
			toggle=true;
		}else if(toggle){
            ballCount++;
			indexOne();
			toggle=false;
		}
     }
     
     public void autoIndex(boolean wantShoot)
     {
         if(!wantShoot)
         {
            feederx.set(ControlMode.PercentOutput, 0.0);
            feedery.set(ControlMode.PercentOutput, 0.0);  
            indexToggle(handoffReady());
         }
         else if(wantShoot)
         {
             ballCount = 0;
             safeReverse = true;
             feederx.set(ControlMode.PercentOutput, -0.4);
             feedery.set(ControlMode.PercentOutput, -0.4);
         }
     }

    @Override
    public void loops()
    {
        // TODO add any method that needs to be run in the robots main loop (200hz)

    }

    @Override
    public void zeroSensors()
    {
        // TODO reset/home any sensors in this subsystem

    }

    @Override
    public void disableSubsystem()
    {
        // TODO disable all motor outputs
    }

    @Override
    public void updateDashboard()
    {
        SmartDashboard.putBoolean("Handoff Ready", handoffReady());
        SmartDashboard.putNumber("Ball Count", ballCount);
        SmartDashboard.putBoolean("SafeReverse", safeReverse);

    }
}