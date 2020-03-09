package ca.team4519.frc2020;

import com.ctre.phoenix.motorcontrol.ControlMode;

import ca.team4519.frc2020.Gains.Drive;
import ca.team4519.frc2020.auton.AutoMode;
import ca.team4519.lib.AirPressureSensor; 
import ca.team4519.frc2020.auton.AutonRunner;
import ca.team4519.frc2020.auton.modes.AimBackShootThree;
import ca.team4519.frc2020.auton.modes.SixBallCloseTrench;
import ca.team4519.frc2020.auton.modes.TestMode;
import ca.team4519.frc2020.subsystems.Climber;
import ca.team4519.frc2020.subsystems.Drivebase;
import ca.team4519.frc2020.subsystems.Feeder;
import ca.team4519.frc2020.subsystems.Flywheel;
import ca.team4519.frc2020.subsystems.Intake;
import ca.team4519.frc2020.subsystems.Lights;
import ca.team4519.frc2020.subsystems.Turret;
import ca.team4519.lib.MechaTimedRobot;
import ca.team4519.lib.MultiThreader;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;

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
    LiveWindow.disableAllTelemetry();
    CameraServer.getInstance().startAutomaticCapture();
    autonLoop.addThread(Drivebase.GrabInstance());
    autonLoop.addThread(Feeder.GrabInstance());
    autonLoop.addThread(Turret.grabInstance());
    autonLoop.addThread(Flywheel.GrabInstance());

    telepLoop.addThread(Drivebase.GrabInstance());
    telepLoop.addThread(Feeder.GrabInstance());
    telepLoop.addThread(Turret.grabInstance());
    telepLoop.addThread(Flywheel.GrabInstance());

    auton.addOption("test", new TestMode());
  
    auton.addOption("Forward only shoot three", new AimBackShootThree());
    auton.setDefaultOption("6 Ball from trench", new SixBallCloseTrench());
    SmartDashboard.putData(auton);
  }

  @Override
  public void autonomousInit()
  {
    //Drivebase.GrabInstance().zeroSensors();
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
    Drivebase.GrabInstance().zeroSensors();
    telepLoop.start();
    
  }

  @Override
  public void teleopPeriodic()
  {
    Feeder.GrabInstance().autoIndex(driver.getRawButton(1));
    if(operator1.getRawButton(1)) Flywheel.GrabInstance().wantFlywheel();
    if(operator1.getRawButton(3)) Flywheel.GrabInstance().wantOff();
    Intake.GrabInstance().setPower(operator1.getRawAxis(4)); //Wheel of fortune
    Intake.GrabInstance().wantIntake(operator1.getRawButton(2), operator1.getRawButton(4), Feeder.GrabInstance().getBallCount());
    Turret.grabInstance().SetTurretIntent(operator1);
    Drivebase.GrabInstance().setLeftRightPower(Drivebase.GrabInstance().arcade(driver.getRawAxis(1), driver.getRawAxis(4)));
    Drivebase.GrabInstance().shift(driver.getRawButton(5));

    Climber.GrabInstance().climberControl(operator1.getRawAxis(1), operator1.getRawAxis(5), operator1.getRawButton(5), operator1.getRawButton(6));
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
    SmartDashboard.putNumber("Stored Pressure", airPressure.getAirPressurePsi());
  }

}
