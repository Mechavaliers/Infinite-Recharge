package ca.team4519.frc2020.auton;

import ca.team4519.frc2020.auton.tasks.TimeoutTask;
import ca.team4519.frc2020.auton.tasks.WaitForBall;
import ca.team4519.frc2020.auton.tasks.WaitForDriveDistance;
import ca.team4519.frc2020.auton.tasks.WaitForTurretAngle;
import ca.team4519.frc2020.subsystems.Drivebase;
import ca.team4519.frc2020.subsystems.Feeder;
import ca.team4519.frc2020.subsystems.Flywheel;
import ca.team4519.frc2020.subsystems.Intake;
import ca.team4519.frc2020.subsystems.Turret;

public abstract class AutoMode extends BaseAutoMode
{
    protected Drivebase drive = Drivebase.GrabInstance();
    protected Turret turret = Turret.grabInstance();
    protected Intake intake = Intake.GrabInstance();
    protected Feeder feeder = Feeder.GrabInstance();
    protected Flywheel flywheel = Flywheel.GrabInstance();

    public void wait(double seconds) throws AutonException {
		runTask(new TimeoutTask(seconds));
	}

	public void waitForDist(double distance, boolean forwards, double timeout) throws AutonException {
		runTask(new WaitForDriveDistance(distance, forwards, timeout));
    }
    
    public void waitForTurretAngle(double wantedAngle, double timeout) throws AutonException {
        runTask(new WaitForTurretAngle(wantedAngle, timeout));
    }

    public void waitForBall(double timeout) throws AutonException{
        runTask(new WaitForBall(timeout));
    }
}