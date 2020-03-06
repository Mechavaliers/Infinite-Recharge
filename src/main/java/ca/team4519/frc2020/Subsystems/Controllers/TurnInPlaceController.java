package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.lib.DrivetrainOutput;
import ca.team4519.lib.pose.DrivebasePose;
import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.Drivebase.Controllers;

import com.team254.lib.trajectory.TrajectoryFollower;

public class TurnInPlaceController implements Controllers{
	
	private final TrajectoryFollowingController controller;
	
	public TurnInPlaceController(DrivebasePose startingPos, double angleGoal, double maxVel){
		TrajectoryFollower.TrajectoryConfig configuration = new TrajectoryFollower.TrajectoryConfig();
		configuration.dt = Gains.CONTROL_LOOP_TIME_SECONDS;
		configuration.max_acc = Gains.Drive.ROBOT_MAX_ROTATIONAL_ACCELERATION;
		configuration.max_vel = maxVel;
		controller = new TrajectoryFollowingController(
				Gains.Drive.Turn_P,
				Gains.Drive.Turn_I,
				Gains.Drive.Turn_D,
				Gains.Drive.Turn_V,
				Gains.Drive.Turn_A,
				Gains.Drive.Turn_Tollerance,
				configuration);
		
		TrajectoryFollower.TrajectorySetpoint startingPosition = new TrajectoryFollower.TrajectorySetpoint();
		startingPosition.pos = startingPos.getAngularPosition();
		startingPosition.vel = startingPos.getAngularVelocity();
		controller.setTarget(startingPosition, angleGoal);
	}

    @Override
    public DrivetrainOutput update(DrivebasePose pose) {
        controller.update(pose.getAngularPosition(), pose.getAngularVelocity());
		double turn = controller.get() + Gains.Drive.Turn_F;
		return new DrivetrainOutput(-turn, turn);
    }

}