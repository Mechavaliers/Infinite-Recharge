package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.frc2020.Gains;

import com.team254.lib.trajectory.TrajectoryFollower;
import com.team254.lib.trajectory.TrajectoryFollower.TrajectorySetpoint;

import ca.team4519.frc2020.subsystems.Intake.Controllers;

public class IntakeLinkageController implements Controllers{
	
	public TrajectoryFollowingController controller;
	
	public IntakeLinkageController(double startingPos, double goalPos, double maxVel) {
		TrajectoryFollower.TrajectoryConfig configuration = new TrajectoryFollower.TrajectoryConfig();
		configuration.dt = Gains.Intake.CONTROL_LOOP_TIME;
		configuration.max_acc = Gains.Intake.LINKAGE_MAX_ACCELERATION;
		configuration.max_vel = maxVel;
		
		controller = new TrajectoryFollowingController(
				Gains.Intake.Linkage_P, 
				Gains.Intake.Linkage_I, 
				Gains.Intake.Linkage_D, 
				Gains.Intake.Linkage_V, 
				Gains.Intake.Linakge_A, 
				Gains.Intake.Linkage_Tollerance, 
				configuration);
		
		TrajectorySetpoint startingPosition = new TrajectorySetpoint();
		startingPosition.pos = startingPos.height();
		startingPosition.vel = startingPos.getLiftVelocity();
		controller.setTarget(startingPosition, goalPos);
	}

	public void changeSetpoint(TrajectoryFollower.TrajectorySetpoint currentSetpoint, double newSetpoint) {
		controller.setTarget(currentSetpoint, newSetpoint);
	}
	
	public TrajectoryFollower.TrajectorySetpoint getSetpoint() {
		return controller.getSetpoint();
	}
	
	public double update(LiftPose pose) {
		controller.update(pose.height(), pose.getLiftVelocity());
		return controller.get();
		
	}
	
}