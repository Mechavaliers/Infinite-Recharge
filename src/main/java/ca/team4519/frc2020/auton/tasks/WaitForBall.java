//works with feeder, keeping track of how many balls there are. Intake, tell if robot has picked up ball 
package ca.team4519.frc2020.auton.tasks;

public class WaitForBall extends TimeoutTask {

    double startBall;
	
	public WaitForBall(double timeout) {
		super(timeout);
	}
	
	@Override
	public boolean done() {
		return (feeder.getBallCount() == (startBall + 1));
    }
    
    @Override
	public void start() {
		startBall = feeder.getBallCount();
	}
}


