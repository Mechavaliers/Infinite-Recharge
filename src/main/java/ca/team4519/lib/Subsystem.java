package ca.team4519.lib;

public abstract class Subsystem {

    public abstract void zeroSensors();

    public abstract void disableSubsystem();

    public abstract void updateDashboard();
    public abstract void feedLogger();

    public abstract void update();
    
}