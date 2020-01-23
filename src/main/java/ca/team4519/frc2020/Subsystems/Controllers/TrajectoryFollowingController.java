package ca.team4519.frc2020.Subsystems.Controllers;

import ca.team4519.frc2020.Gains;
import ca.team4519.lib.controllers.Controller;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectoryFollowingController extends Controller
{
    double left;
    double right;

    DistanceFollower leftDistanceFollower;
    DistanceFollower rightDistanceFollower;

    public TrajectoryFollowingController(double kP, double kI, double kD, double kV, double kA, double tollerance)
    {
        leftDistanceFollower = new DistanceFollower();
        leftDistanceFollower.configurePIDVA(kP, kI, kD, kV, kA);
        rightDistanceFollower = new DistanceFollower();
        rightDistanceFollower.configurePIDVA(kP, kI, kD, kV, kA);
    }

    public void setTarget(Trajectory trajectory)
    {        
        TankModifier modifier = new TankModifier(trajectory);


        modifier.modify(Gains.Drive.Wheelbase_Width);

        Trajectory left = modifier.getLeftTrajectory();
        Trajectory right = modifier.getRightTrajectory();

        leftDistanceFollower.setTrajectory(left);
        rightDistanceFollower.setTrajectory(right);
    }

    public void update(int leftPosition, int rightPosition)
    {
        left = leftDistanceFollower.calculate(leftPosition);
        right = rightDistanceFollower.calculate(rightPosition);
    }

    public double getLeft()
    {
        return left;
    }
    public double getRight()
    {
        return right;
    }

    @Override
    public void reset() {
        leftDistanceFollower.reset();
        rightDistanceFollower.reset();

    }

    @Override
    public boolean onTarget() {
        
        return (leftDistanceFollower.isFinished() && rightDistanceFollower.isFinished());
    }
}