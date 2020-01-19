package ca.team4519.frc2020.Subsystems.Controllers;

import ca.team4519.frc2020.Gains;
import ca.team4519.lib.controllers.Controller;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectoryFollowingController extends Controller
{
    double left;
    double right;

    EncoderFollower leftEncoderFollower;
    EncoderFollower rightEncoderFollower;

    public TrajectoryFollowingController(double kP, double kI, double kD, double kV, double kA, double tollerance)
    {
        leftEncoderFollower = new EncoderFollower();
        leftEncoderFollower.configurePIDVA(kP, kI, kD, kV, kA);
        rightEncoderFollower = new EncoderFollower();
        rightEncoderFollower.configurePIDVA(kP, kI, kD, kV, kA);
    }

    public void setTarget(Trajectory trajectory)
    {        
        TankModifier modifier = new TankModifier(trajectory);


        modifier.modify(Gains.Drive.Wheelbase_Width);

        Trajectory left = modifier.getLeftTrajectory();
        Trajectory right = modifier.getRightTrajectory();

        leftEncoderFollower.setTrajectory(left);
        rightEncoderFollower.setTrajectory(right);
    }

    public void update(int leftPosition, int rightPosition)
    {
        left = leftEncoderFollower.calculate(leftPosition);
        right = rightEncoderFollower.calculate(rightPosition);
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
        leftEncoderFollower.reset();
        rightEncoderFollower.reset();

    }

    @Override
    public boolean onTarget() {
        
        return (leftEncoderFollower.isFinished() && rightEncoderFollower.isFinished());
    }
}