package ca.team4519.frc2020.auton.tasks;

import ca.team4519.frc2020.Gains;

public class WaitForDriveDistance extends TimeoutTask {

	protected double distance;
	protected boolean forwards;
	
	public WaitForDriveDistance(double distance, boolean forwards, double timeout) {
		super(timeout);
		this.distance = distance;
		this.forwards = forwards;
	}
	
	@Override
	public boolean done() {
		return ((forwards ? drive.getRobotPose().getRobotDistance() >= distance-Gains.Drive.Dist_Tollerance : drive.getRobotPose().getRobotDistance() <= distance-Gains.Drive.Dist_Tollerance) || super.done());
	}
}