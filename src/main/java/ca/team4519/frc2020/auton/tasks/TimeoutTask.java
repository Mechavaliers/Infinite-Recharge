package ca.team4519.frc2020.auton.tasks;

import edu.wpi.first.wpilibj.Timer;

public class TimeoutTask extends Task {
	
	double timeout;
	double startTime;
	
	public TimeoutTask(double timeout) {
		this.timeout = timeout;
	}

	@Override
	public boolean completed() {
		return ((Timer.getFPGATimestamp() - startTime) >= timeout)? true : false;
	}

	@Override
	public void update() {
	}

	@Override
	public boolean done() {
		return false;
		
		
	}

	@Override
	public void start() {
		startTime = Timer.getFPGATimestamp();
	}
	
}