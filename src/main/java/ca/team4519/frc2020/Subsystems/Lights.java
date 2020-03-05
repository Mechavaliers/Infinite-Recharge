package ca.team4519.frc2020.subsystems;

import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Lights extends Subsystem implements Thread
{
    private static Lights thisInstance;
    private final Solenoid lights;

    public synchronized static Lights GrabInstance()
    {
        if (thisInstance == null) thisInstance = new Lights();
        return thisInstance;
    }

    private Lights()
    {
        lights = new Solenoid(5);
    }

    public void blinkController(int ballCount)
    {
        if(ballCount == 5)
        {
            for (int i = 0; i < 5; i++)
            {
                lights.set(!lights.get());    
            }
        }
    }

    public void wantOn()
    {
        lights.set(true);
    }

    public void wantOff()
    {
        lights.set(false);
    }

    public void wantBlink()
    {
        lights.set(!lights.get());
        Timer.delay(0.5);
        lights.set(!lights.get());
    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableSubsystem() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateDashboard() {
        // TODO Auto-generated method stub

    }

    @Override
    public void loops() {
        //blinkController(Feeder.GrabInstance().getBallCount());


    }

}