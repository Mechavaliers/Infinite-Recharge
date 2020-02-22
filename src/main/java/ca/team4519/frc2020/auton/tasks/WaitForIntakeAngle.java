package ca.team4519.frc2020.auton.tasks;

public class WaitForIntakeAngle extends TimeoutTask {

	protected double intakeAngle;
	
	public WaitForIntakeAngle(double intakeAngle, double timeout) {
		super(timeout);
		this.intakeAngle = intakeAngle;
	}
	
	@Override
	public boolean done() {
		return (intake.getPotConvertedValue() >= intakeAngle);
	}
}