package ca.team4519.frc2020.subsystems.controllers;

import java.util.ArrayList;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.Drivebase.Controllers;
import ca.team4519.lib.pose.DrivebasePose;
import ca.team4519.lib.DrivetrainOutput;
import edu.wpi.first.wpilibj.Controller;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

public class PathFollowerController implements Controllers {

    Trajectory trajectory;
    RamseteController controller;
    double time = 0;

    public void generateTrajectory(){
        var startPos = new Pose2d(Units.feetToMeters(13), Units.feetToMeters(15), Rotation2d.fromDegrees(70-1));
        var endPos = new Pose2d(Units.feetToMeters(15), Units.feetToMeters(13), Rotation2d.fromDegrees(50));

        var interiorWaypoints = new ArrayList<Translation2d>();
        interiorWaypoints.add(new Translation2d(Units.feetToMeters(15), Units.feetToMeters(13)));
        interiorWaypoints.add(new Translation2d(Units.feetToMeters(15.2), Units.feetToMeters(36.2)));

        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(Gains.Drive.ROBOT_MAX_VELOCITY), Units.feetToMeters(Gains.Drive.ROBOT_MAX_ACCELERATION));
        config.setReversed(false);

        trajectory = TrajectoryGenerator.generateTrajectory(startPos, interiorWaypoints, endPos, config);

        controller = new RamseteController();

    }



	@Override
	public DrivetrainOutput update(DrivebasePose pose) {
        time += Gains.CONTROL_LOOP_TIME;
        Trajectory.State goal = trajectory.sample(time);
        controller.calculate(pose.getRobotPose2d(), goal);
        SmartDashboard.putNumber("time tracker - PathFollowerController.java", time);

		return new DrivetrainOutput(0.0, 0.0);
	}
    
}