 package ca.team4519.frc2020.auton.tasks;


public class WaitForFlywheelSpeed extends TimeoutTask {

	protected double goalRPM;
	
	public WaitForFlywheelSpeed(double goalRPM, double timeout) {
		super(timeout);
		this.goalRPM = goalRPM;
	}
	
	@Override
	public boolean done() {
		return (flywheel.getFlywheelSpeed() >= goalRPM);
	}
}