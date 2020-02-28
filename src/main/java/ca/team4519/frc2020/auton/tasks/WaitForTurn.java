package ca.team4519.frc2020.auton.tasks;

import ca.team4519.frc2020.Gains;

public class WaitForTurn extends TimeoutTask {
	
	protected double angle;
	protected boolean forwards;

	public WaitForTurn(double angle, boolean forwards, double timeout) {
		super(timeout);
		this.angle = angle;
		this.forwards = forwards;
	}

	@Override
	public boolean done() {
		return ((forwards ? drive.getRobotPose().getAngularPosition() >= angle-Gains.Drive.Turn_Tollerance : drive.getRobotPose().getAngularPosition() <= angle-Gains.Drive.Turn_Tollerance) || super.done());
	}
}