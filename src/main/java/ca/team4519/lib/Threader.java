package ca.team4519.lib;

import java.util.Timer;
import java.util.TimerTask;

public class Threader {

	private double period = 1.0 / 100.0;
	protected Thread thread;
	private Timer threadUpdater;
	protected String name;
	
	public Threader(String name, Thread thread, double period) {
		this.thread = thread;
		this.period = period;
		this.name = name;
	}
	
	private class UpdaterTask extends TimerTask {
		
		private Threader threader;
		
		public UpdaterTask(Threader threader) {
			if(threader == null) {
				throw new NullPointerException("No Thread");
			}
			this.threader = threader;
		}
		
		public void run() {
			thread.loops();
		}
	}
	
	public void start() {
		if(threadUpdater == null) {
			threadUpdater = new Timer("Thread - " + this.name);
			threadUpdater.schedule(new UpdaterTask(this),  0L, (long) (this.period * 1000));
		}
	}
	
	public void stop() {
		if(threadUpdater != null) {
			threadUpdater.cancel();
			threadUpdater = null;
		}
	}
	
	private void update() {
		thread.loops();
	}
	
}