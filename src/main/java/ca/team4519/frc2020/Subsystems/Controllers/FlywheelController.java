package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.frc2020.Gains;
import ca.team4519.frc2020.subsystems.Flywheel.Controllers;

import edu.wpi.first.wpilibj.controller.PIDController;

public class FlywheelController implements Controllers{

	private PIDController controller;

	
    public FlywheelController(double goalRPM)
    {
        controller = new PIDController(Gains.Flywheel.Flywheel_P, Gains.Flywheel.Flywheel_I, Gains.Flywheel.Flywheel_D, Gains.CONTROL_LOOP_TIME_SECONDS);
        controller.setSetpoint(goalRPM);
    }

    @Override
    public double update(double rpm)
    {
        return controller.calculate(rpm);
    }
	
}