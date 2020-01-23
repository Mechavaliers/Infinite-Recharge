package ca.team4519.frc2020.Subsystems.Controllers;

import java.io.File;
import java.io.IOException;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.Subsystems.Drivebase.AutoPaths;
import ca.team4519.frc2020.Subsystems.Drivebase.Controllers;
import ca.team4519.lib.DrivetrainOutput;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

public class PathFollowingController implements Controllers {

    private TrajectoryFollowingController controller;
    private Trajectory.Config config;
    private File pathFile;

    public PathFollowingController(AutoPaths path, double maxVel) throws IOException {
        String correctPath = path.name() + ".traj";
        pathFile = new File(correctPath);

        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
        Trajectory.Config.SAMPLES_HIGH,
        Gains.Drive.CONTROL_LOOP_TIME,
        Gains.Drive.ROBOT_MAX_VELOCITY,
        maxVel,
        Gains.Drive.ROBOT_MAX_JERK);

        Trajectory trajectoryLoaded = Pathfinder.readFromCSV(pathFile);

        controller = new TrajectoryFollowingController(
        Gains.Drive.Dist_P,
        Gains.Drive.Dist_I,
        Gains.Drive.Dist_D,
        Gains.Drive.Dist_V,
        Gains.Drive.Dist_A,
        Gains.Drive.Dist_Tollerance);

        controller.setTarget(trajectoryLoaded);
    }

    public static double leftEncoderTicks()
    {
        return 1;
    }

    @Override
    public DrivetrainOutput update(DifferentialDriveOdometry odometry) {
        //controller.update(odometry.getPoseMeters(), ); //TODO Implement new 2016style pose controller

        return new DrivetrainOutput(controller.getLeft(), controller.getRight());
    }

}