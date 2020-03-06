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

    private final VictorSPX upperFeeder;

    private final VictorSPX lowerFeeder;

    private final DigitalInput ballDetector;

    private boolean toggle = false;
    private boolean safeReverse = true;
    private int ballCount = 0;

    public synchronized static Feeder GrabInstance() {

        if (thisInstance == null)
            thisInstance = new Feeder();
        return thisInstance;
    }

    private Feeder() {

        upperFeeder = new VictorSPX(Constants.feeder1);
        lowerFeeder = new VictorSPX(Constants.feeder2);
        lowerFeeder.setInverted(true);

        ballDetector = new DigitalInput(Constants.ballDetector);

    }

    public boolean handoffReady() {
        return !ballDetector.get();
    }

    public void insertName(final boolean feed) {
        if (feed) {
            upperFeeder.set(ControlMode.PercentOutput, -0.4);
            lowerFeeder.set(ControlMode.PercentOutput, -0.4);
        } else {
            upperFeeder.set(ControlMode.PercentOutput, 0.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.0);
        }

    }

    public void indexOne() {

        switch (ballCount) {
        case 1:
            upperFeeder.set(ControlMode.PercentOutput, -1.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.2);
            Timer.delay(Gains.Feeder.IndexTime + 0.0625);
            upperFeeder.set(ControlMode.PercentOutput, 0.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.0);
            break;
        case 2:
            upperFeeder.set(ControlMode.PercentOutput, -1.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.2);
            Timer.delay(Gains.Feeder.IndexTime + 0.0625);
            upperFeeder.set(ControlMode.PercentOutput, 0.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.0);
            break;
        case 3:
            upperFeeder.set(ControlMode.PercentOutput, -1.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.2);
            Timer.delay(Gains.Feeder.IndexTime + 0.0625);
            upperFeeder.set(ControlMode.PercentOutput, 0.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.0);
            break;
        case 4:
            upperFeeder.set(ControlMode.PercentOutput, -1.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.2);
            Timer.delay(Gains.Feeder.IndexTime + 0.0625);
            upperFeeder.set(ControlMode.PercentOutput, 0.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.0);
            break;
        case 5:
            upperFeeder.set(ControlMode.PercentOutput, -1.0);
            lowerFeeder.set(ControlMode.PercentOutput, -0.2);
            Timer.delay(Gains.Feeder.IndexTime + 0.0625);
            upperFeeder.set(ControlMode.PercentOutput, 0.375);
            lowerFeeder.set(ControlMode.PercentOutput, 0.375);
            Timer.delay(Gains.Feeder.IndexTime + 0.0625);
            upperFeeder.set(ControlMode.PercentOutput, 0.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.0);
            Lights.GrabInstance().wantBlink();
            break;
        default:
            upperFeeder.set(ControlMode.PercentOutput, 0.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.0);
            break;
        }
    }

    public int getBallCount() {
        return ballCount;
    }

    public void setBallCount(final int assignBallCount) {
        ballCount = assignBallCount;
    }

    public void indexToggle(final boolean button) {
        if (!button) {
            toggle = true;
        } else if (toggle) {
            ballCount++;
            indexOne();
            toggle = false;
        }
    }

    public void dump() {
        upperFeeder.set(ControlMode.PercentOutput, -0.25);
        lowerFeeder.set(ControlMode.PercentOutput, -0.25);
    }

    public void off() {
        upperFeeder.set(ControlMode.PercentOutput, 0.0);
        lowerFeeder.set(ControlMode.PercentOutput, 0.0);
    }

    public void autoIndex(final boolean wantShoot)
     {
         if(!wantShoot)
         {
            upperFeeder.set(ControlMode.PercentOutput, 0.0);
            lowerFeeder.set(ControlMode.PercentOutput, 0.0);  
            indexToggle(handoffReady());
         }
         else if(wantShoot)
         {
             ballCount = 0;
             safeReverse = true;
             upperFeeder.set(ControlMode.PercentOutput, -0.4);
             lowerFeeder.set(ControlMode.PercentOutput, -0.4);
         }
     }

     public void slowSuck()
     {
        upperFeeder.set(ControlMode.PercentOutput, -0.3);
        lowerFeeder.set(ControlMode.PercentOutput, 0.2);
     }

     public void commandIndex ()
     {
        upperFeeder.set(ControlMode.PercentOutput, -1.0);
        lowerFeeder.set(ControlMode.PercentOutput, 0.2);
        Timer.delay(Gains.Feeder.IndexTime+0.0625);
        upperFeeder.set(ControlMode.PercentOutput, 0.0);
        lowerFeeder.set(ControlMode.PercentOutput, 0.0);
     }

    @Override
    public void loops()
    {

    }

    @Override
    public void zeroSensors()
    {
        ballCount = 0;
    }

    @Override
    public void disableSubsystem()
    {
        
    }

    @Override
    public void updateDashboard()
    {
        SmartDashboard.putBoolean("Handoff Ready", handoffReady());
        SmartDashboard.putNumber("Ball Count", ballCount);
        SmartDashboard.putBoolean("SafeReverse", safeReverse);

    }
}