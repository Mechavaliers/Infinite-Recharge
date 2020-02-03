package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.frc2020.Gains;
import ca.team4519.lib.pid.TurningPID;
import ca.team4519.lib.DrivetrainOutput;
import ca.team4519.lib.DrivebasePose;
import ca.team4519.frc2020.Subsystems.Drivebase.Controllers;

import com.team254.lib.trajectory.TrajectoryFollower;
import com.team254.lib.trajectory.TrajectoryFollower.TrajectorySetpoint;


public class DriveLineController implements Controllers{

	private TrajectoryFollowingController controller;
	private TurningPID turningPIDLoop;
	
	public DriveLineController(DrivebasePose startingPos, double goalPos, double maxVel){
		TrajectoryFollower.TrajectoryConfig configuration = new TrajectoryFollower.TrajectoryConfig();
		configuration.dt = Gains.Drive.CONTROL_LOOP_TIME;
		configuration.max_acc = Gains.Drive.ROBOT_MAX_ACCELERATION;
		configuration.max_vel = maxVel;
		
		controller = new TrajectoryFollowingController(
				Gains.Drive.Dist_P, 
				Gains.Drive.Dist_I, 
				Gains.Drive.Dist_D, 
				Gains.Drive.Dist_V, 
				Gains.Drive.Dist_A, 
				Gains.Drive.Dist_Tollerance, 
				configuration);
		
		TrajectorySetpoint startingPosition = new TrajectorySetpoint();
		startingPosition.pos = encoderDistance(startingPos);
		startingPosition.vel = encoderVelocity(startingPos);
		controller.setTarget(startingPosition, goalPos);
		
		turningPIDLoop = new TurningPID(
				Gains.Drive.DistTurn_P,
				Gains.Drive.DistTurn_I,
				Gains.Drive.DistTurn_D);
		turningPIDLoop.setSetpoint(startingPos.getAngle());
		
	}

	public static double encoderDistance(DrivebasePose pose){
		return (pose.getLeftDistance() + pose.getRightDistance()) / 2.0;
	}

	public static double encoderVelocity(DrivebasePose pose){
		return (pose.getLeftVelocity() + pose.getRightVelocity()) / 2.0;
	}

	public DrivetrainOutput update(DrivebasePose pose) {
		controller.update(
				(pose.getLeftDistance() + pose.getRightDistance()) / 2.0,
				(pose.getLeftVelocity() + pose.getRightVelocity()) / 2.0);
		double power = -controller.get();
		double turn = -turningPIDLoop.calculate(pose.getAngle());
		
		return new DrivetrainOutput(power+turn, power-turn);
	}
	
}