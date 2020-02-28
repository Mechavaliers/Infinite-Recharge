package ca.team4519.frc2020.auton.tasks;

public class WaitForTurretAngle extends TimeoutTask {

	protected double goalAngle;
	
	public WaitForTurretAngle(double goalAngle, double timeout) {
		super(timeout);
		this.goalAngle = goalAngle;
	}
	
	@Override
	public boolean done() {
		return (turret.turretAngle() >= goalAngle);
	}
}