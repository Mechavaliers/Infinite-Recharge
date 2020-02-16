package ca.team4519.frc2020.subsystems.controllers;

import ca.team4519.frc2020.Gains;
//import ca.team4519.lib.pid.TurningPID;
import ca.team4519.lib.DrivetrainOutput;
import ca.team4519.lib.DrivebasePose;
import ca.team4519.frc2020.subsystems.Drivebase.Controllers;

import edu.wpi.first.wpilibj.controller.PIDController;

public class FlywheelController implements Controllers{

	private PIDController controller;

	
    public FlywheelController(double goalRPM)
    {
        controller = new PIDController(Gains.Flywheel.Flywheel_P, Gains.Flywheel.Flywheel_I, Gains.Flywheel.Flywheel_D);
        controller.setSetpoint(goalRPM);
    }


	public DrivetrainOutput update(DrivebasePose pose) {
		controller.calculate(measurement)
		double power = -controller.get();
		double turn = /*-turningPIDLoop.calculate(pose.getAngle());*/ 1.5;
		
		return new DrivetrainOutput(power+turn, power-turn);
	}
	
}