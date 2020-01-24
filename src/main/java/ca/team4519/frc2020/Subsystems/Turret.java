package ca.team4519.frc2020.Subsystems;

import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;

public class Turret extends Subsystem implements Thread{

    public static Turret thisInstance;

    private Turret()
    {

    }

    public synchronized static Turret grabInstance()
    {
        if(thisInstance == null)
        {
            thisInstance = new Turret();
        }

        return thisInstance;
    }

    @Override
    public void loops() {
        // TODO Auto-generated method stub

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
    public void feedLogger() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}