package ca.team4519.frc2020.auton;

public class AutonRunner {
	
	private BaseAutoMode autonMode;
	private Thread runningThread = null;
	
	public void selectAuto(BaseAutoMode autoToRun){
		autonMode = autoToRun;
	}
	
	public void start() {
		if (runningThread == null) {
			runningThread = new Thread(new Runnable() {
				@Override
				public void run() {
					if (autonMode != null) {
						autonMode.run();
					}
				}
			});
			runningThread.start();
		}
	}
	
	public void stop() {
		if(autonMode != null) {
			autonMode.stop();
		}
		runningThread = null;
	}
	
}