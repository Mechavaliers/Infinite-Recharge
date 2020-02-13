package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.frc2020.Gains;
import ca.team4519.lib.TurretPose;

import com.team254.lib.trajectory.TrajectoryFollower;
import com.team254.lib.trajectory.TrajectoryFollower.TrajectorySetpoint;

import ca.team4519.frc2020.subsystems.Turret.Controllers;

public class TurretRotationController implements Controllers{
	
	public TrajectoryFollowingController controller;
	
	public TurretRotationController(TurretPose startingPos, double goalPos) {
		TrajectoryFollower.TrajectoryConfig configuration = new TrajectoryFollower.TrajectoryConfig();
		configuration.dt = Gains.CONTROL_LOOP_TIME;
		configuration.max_acc = Gains.Turret.TURRET_MAX_VELOCITY;
		configuration.max_vel = Gains.Turret.TURRET_MAX_VELOCITY;
		
		controller = new TrajectoryFollowingController(
				Gains.Turret.Turret_P, 
				Gains.Turret.Turret_I, 
				Gains.Turret.Turret_D, 
				Gains.Turret.Turret_V, 
				Gains.Turret.Turret_A, 
				Gains.Turret.Turret_Tollerance, 
				configuration);
		
		TrajectorySetpoint startingPosition = new TrajectorySetpoint();
		startingPosition.pos = startingPos.getPosition();
		startingPosition.vel = startingPos.getVelocity();
		controller.setTarget(startingPosition, goalPos);
	}

	public void changeSetpoint(TrajectoryFollower.TrajectorySetpoint currentSetpoint, double newSetpoint) {
		controller.setTarget(currentSetpoint, newSetpoint);
	}
	
	public TrajectoryFollower.TrajectorySetpoint getSetpoint() {
		return controller.getSetpoint();
	}
	
	public double update(TurretPose pose) {
		controller.update(pose.getPosition(), pose.getVelocity());
		return controller.get();
		
	}
	
}