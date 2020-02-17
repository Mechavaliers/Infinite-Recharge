package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.frc2020.Gains;
import ca.team4519.lib.DrivetrainOutput;
import ca.team4519.lib.pose.DrivebasePose;
import ca.team4519.frc2020.subsystems.Drivebase.Controllers;

import com.team254.lib.trajectory.TrajectoryFollower;
import com.team254.lib.trajectory.TrajectoryFollower.TrajectorySetpoint;

import edu.wpi.first.wpilibj.controller.PIDController;


public class DrivebaseLockController implements Controllers{

	private TrajectoryFollowingController controller;
	private PIDController turningPIDLoop;
	
	public DrivebaseLockController(DrivebasePose poseToHold){
		TrajectoryFollower.TrajectoryConfig configuration = new TrajectoryFollower.TrajectoryConfig();
		configuration.dt = Gains.CONTROL_LOOP_TIME_SECONDS;
		configuration.max_acc = Gains.Drive.ROBOT_MAX_ACCELERATION;
		configuration.max_vel = Gains.Drive.ROBOT_MAX_VELOCITY;
		
		controller = new TrajectoryFollowingController(
				Gains.Drive.Dist_P, 
				Gains.Drive.Dist_I, 
				Gains.Drive.Dist_D, 
				Gains.Drive.Dist_V, 
				Gains.Drive.Dist_A, 
				Gains.Drive.Dist_Tollerance, 
				configuration);
		
		TrajectorySetpoint startingPosition = new TrajectorySetpoint();
		startingPosition.pos = poseToHold.getRobotDistance();
		startingPosition.vel = poseToHold.getRobotVelocity();
		controller.setTarget(startingPosition, poseToHold.getRobotDistance());
		
		turningPIDLoop = new PIDController(
				Gains.Drive.DistTurn_P,
				Gains.Drive.DistTurn_I,
                Gains.Drive.DistTurn_D,
                Gains.CONTROL_LOOP_TIME_SECONDS);
		turningPIDLoop.setSetpoint(poseToHold.getAngularPosition());		
	}

	public DrivetrainOutput update(DrivebasePose pose) {
		controller.update(pose.getRobotDistance(), pose.getRightVelocity());
		double power = controller.get();
		double turn = turningPIDLoop.calculate(pose.getAngularPosition());;
		
		return new DrivetrainOutput(power+turn, power-turn);
	}
	
}