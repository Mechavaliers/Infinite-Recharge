package ca.team4519.frc2020.auton;

import ca.team4519.frc2020.subsystems.Drivebase;

public abstract class AutoMode extends BaseAutoMode
{
    protected Drivebase drive = Drivebase.GrabInstance();
}