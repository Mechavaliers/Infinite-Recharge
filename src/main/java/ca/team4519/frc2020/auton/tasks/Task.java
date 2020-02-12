package ca.team4519.frc2020.auton.tasks;

import ca.team4519.frc2020.subsystems.Drivebase;
import ca.team4519.frc2020.subsystems.Intake;
import ca.team4519.frc2020.subsystems.Feeder;
import ca.team4519.frc2020.subsystems.Turret;
import ca.team4519.frc2020.subsystems.Flywheel;

public abstract class Task
{
    protected Drivebase drive = Drivebase.GrabInstance();
    protected Intake intake = Intake.GrabInstance();
    protected Feeder feeder = Feeder.GrabInstance();
    protected Turret turret = Turret.grabInstance();
    protected Flywheel flywheel = Flywheel.GrabInstance();

    public abstract boolean completed();
    public abstract void update();
    public abstract boolean done();
    public abstract void start();
}