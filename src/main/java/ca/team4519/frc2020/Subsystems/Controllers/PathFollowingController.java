package ca.team4519.frc2020.Subsystems.Controllers;

import java.io.File;
import java.io.IOException;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.Subsystems.Drivebase.AutoPaths;
import ca.team4519.frc2020.Subsystems.Drivebase.Controllers;
import ca.team4519.lib.DrivetrainOutput;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;

public class PathFollowingController implements Controllers {

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

        TankModifier modifier = new TankModifier(trajectoryLoaded);
        modifier.modify(Gains.Drive.Wheelbase_Width);

        Trajectory left = modifier.getLeftTrajectory();
        Trajectory right = modifier.getRightTrajectory();




    }




    @Override
    public DrivetrainOutput update() {
        
        // TODO Auto-generated method stub
        return new DrivetrainOutput(0.0, 0.0);
    }

}