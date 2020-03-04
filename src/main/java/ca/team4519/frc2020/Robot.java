package ca.team4519.frc2020;

import com.ctre.phoenix.motorcontrol.ControlMode;

import ca.team4519.frc2020.Gains.Drive;
import ca.team4519.frc2020.auton.AutoMode;
import ca.team4519.lib.AirPressureSensor; 
import ca.team4519.frc2020.auton.AutonRunner;
import ca.team4519.frc2020.subsystems.Climber;
import ca.team4519.frc2020.subsystems.Drivebase;
import ca.team4519.frc2020.subsystems.Feeder;
import ca.team4519.frc2020.subsystems.Flywheel;
import ca.team4519.frc2020.subsystems.Intake;
import ca.team4519.frc2020.subsystems.Lights;
import ca.team4519.frc2020.subsystems.PDPMonitor;
import ca.team4519.frc2020.subsystems.Turret;
import ca.team4519.lib.MechaTimedRobot;
import ca.team4519.lib.MultiThreader;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends MechaTimedRobot
{
  MultiThreader autonLoop = new MultiThreader("200Hz - Auto Loop", 1.0 / 200.0);
  MultiThreader telepLoop = new MultiThreader("200Hz - TeleopLoop", 1.0 / 200.0);

  AutonRunner autonLoopRunner = new AutonRunner();

  SendableChooser<AutoMode> auton = new SendableChooser<AutoMode>();
  Joystick driver = new Joystick(0);
  Joystick operator1 = new Joystick(1);

  AirPressureSensor airPressure = new AirPressureSensor(0);
  
  @Override
  public void robotInit()
  {
    autonLoop.addThread(Drivebase.GrabInstance());
    autonLoop.addThread(Intake.GrabInstance());
    autonLoop.addThread(Feeder.GrabInstance());
    autonLoop.addThread(Turret.grabInstance());
    autonLoop.addThread(Flywheel.GrabInstance());

    telepLoop.addThread(Drivebase.GrabInstance());
    telepLoop.addThread(Intake.GrabInstance());
    telepLoop.addThread(Feeder.GrabInstance());
    telepLoop.addThread(Turret.grabInstance());
    telepLoop.addThread(Flywheel.GrabInstance());
    //updateLoop.addThread(thread);

    auton.addOption("test", null);
    SmartDashboard.putData(auton);

  }

  @Override
  public void autonomousInit()
  {
    //Get the selected mode from the dashboard
    AutoMode mode = auton.getSelected();
    //Then feed the selected mode to our auto loop runner
    autonLoopRunner.selectAuto(mode);
    //start our auton control loops
    autonLoop.start();
    //run any mode specific init commands
    mode.init();
    //start the loop runner - actually begins the auto mode
    autonLoopRunner.start();

  }

  @Override
  public void autonomousPeriodic()
  {
    //this stays empty because our auto loop runner handles what this normally would
  }

  @Override
  public void disabledInit()
  {
    //disabled init gets called when the robot is transitioning to a new state
    // i.e. disabledPeriodic -> autoInit -> autoPeriodic -> disabledInit -> teleopInit -> so on and so forth

    //we kill any active control loops so they dont keep going when the robot is re-enabled
    Drivebase.GrabInstance().disableSubsystem();
    Feeder.GrabInstance().disableSubsystem();
    Turret.grabInstance().disableSubsystem();
    Flywheel.GrabInstance().disableSubsystem();
    autonLoop.stop();
    telepLoop.stop();
  }

  @Override
  public void teleopInit()
  {
    //Lights.GrabInstance().wantBlink();
    Drivebase.GrabInstance().zeroSensors();
    //Turret.grabInstance().zeroSensors();
    telepLoop.start();
    
  }

  @Override
  public void teleopPeriodic()
  {
    //Lights.GrabInstance().wantBlink();
   // SmartDashboard.putNumber("joystick input", driver.getRawAxis(0));
  // Turret.grabInstance().setPower(operator1.getRawAxis(0));
   // Turret.grabInstance().turretPivot.set(ControlMode.PercentOutput, operator1.getRawAxis(0));
    //Flywheel.GrabInstance().testing(driver.getRawAxis(3));

    Feeder.GrabInstance().autoIndex(driver.getRawButton(1));
    if(operator1.getRawButton(2)) Flywheel.GrabInstance().wantFlywheel();
    if(operator1.getRawButton(1)) Flywheel.GrabInstance().wantOff();
    //Intake.GrabInstance().setPower(operator1.getRawAxis(4)); //Wheel of fortune
    Intake.GrabInstance().wantIntake(operator1.getRawButton(3), Feeder.GrabInstance().getBallCount());
    Turret.grabInstance().SetTurretIntent(operator1);
    Drivebase.GrabInstance().setLeftRightPower(Drivebase.GrabInstance().arcade(driver.getRawAxis(1), driver.getRawAxis(4)));
    Drivebase.GrabInstance().shift(driver.getRawButton(5));

    //Climber.GrabInstance().climberControl(operator1.getRawAxis(1), operator1.getRawAxis(5), operator1.getRawButton(5), operator1.getRawButton(6));
  }

  @Override
  public void testInit()
  {

  }

  @Override
  public void testPeriodic()
  {

  }

  @Override
  public void allPeriodic()
  {
    Drivebase.GrabInstance().updateDashboard();;
    Turret.grabInstance().updateDashboard();
    Intake.GrabInstance().updateDashboard();
    Flywheel.GrabInstance().updateDashboard();
    Feeder.GrabInstance().updateDashboard();
    PDPMonitor.GrabInstance().updateDashboard();
  }

}
