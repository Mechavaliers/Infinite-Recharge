package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.frc2020.Gains;
import ca.team4519.lib.IntakeLinkagePose;

import com.team254.lib.trajectory.TrajectoryFollower;
import com.team254.lib.trajectory.TrajectoryFollower.TrajectorySetpoint;

import ca.team4519.frc2020.subsystems.Intake.Controllers;

public class IntakeLinkageController implements Controllers{
	
	public TrajectoryFollowingController controller;
	
	public IntakeLinkageController(IntakeLinkagePose startingPos, double goalPos) {
		TrajectoryFollower.TrajectoryConfig configuration = new TrajectoryFollower.TrajectoryConfig();
		configuration.dt = Gains.CONTROL_LOOP_TIME_SECONDS;
		configuration.max_acc = Gains.Intake.LINKAGE_MAX_ACCELERATION;
		configuration.max_vel = Gains.Intake.LINKAGE_MAX_VELOCITY;
		
		controller = new TrajectoryFollowingController(
				Gains.Intake.Linkage_P, 
				Gains.Intake.Linkage_I, 
				Gains.Intake.Linkage_D, 
				Gains.Intake.Linkage_V, 
				Gains.Intake.Linakge_A, 
				Gains.Intake.Linkage_Tollerance, 
				configuration);
		
		TrajectorySetpoint startingPosition = new TrajectorySetpoint();
		startingPosition.pos = startingPos.getAngularPosition();
		startingPosition.vel = startingPos.getAngularVelocity();
		controller.setTarget(startingPosition, goalPos);
	}

	public void changeSetpoint(TrajectoryFollower.TrajectorySetpoint currentSetpoint, double newSetpoint) {
		controller.setTarget(currentSetpoint, newSetpoint);
	}
	
	public TrajectoryFollower.TrajectorySetpoint getSetpoint() {
		return controller.getSetpoint();
	}
	
	public double update(IntakeLinkagePose pose) {
		controller.update(pose.getAngularPosition(), pose.getAngularVelocity());
		return controller.get();
		
	}
	
}