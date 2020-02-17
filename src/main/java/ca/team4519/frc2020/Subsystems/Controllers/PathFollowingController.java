package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.Drivebase.AutoPaths;
import ca.team4519.frc2020.subsystems.Drivebase.Controllers;
import ca.team4519.lib.pose.DrivebasePose;
import ca.team4519.lib.DrivetrainOutput;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;
import java.io.IOException;

public class PathFollowingController implements Controllers {

    private PathfinderTrajectoryFollowingController controller;
    private Trajectory.Config config;
    private File pathFile;

    public PathFollowingController(AutoPaths path, double maxVel) throws IOException {
        String correctPath = path.name() + ".traj";
        pathFile = new File(correctPath);

        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
        Trajectory.Config.SAMPLES_HIGH,
        Gains.CONTROL_LOOP_TIME,
        Gains.Drive.ROBOT_MAX_VELOCITY,
        maxVel,
        Gains.Drive.ROBOT_MAX_JERK);

        Trajectory trajectoryLoaded = Pathfinder.readFromCSV(pathFile);

        controller = new PathfinderTrajectoryFollowingController(
        Gains.Drive.Dist_P,
        Gains.Drive.Dist_I,
        Gains.Drive.Dist_D,
        Gains.Drive.Dist_V,
        Gains.Drive.Dist_A);

        controller.setTarget(trajectoryLoaded);
    }

    public static double leftEncoderTicks()
    {
        return 1;
    }

    @Override
    public DrivetrainOutput update(DrivebasePose pose) {
        controller.update(1, 1); // update with reall variables

        return new DrivetrainOutput(controller.getLeft(), controller.getRight());
    }

}