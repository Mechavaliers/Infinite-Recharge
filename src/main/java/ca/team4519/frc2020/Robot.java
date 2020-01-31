package ca.team4519.frc2020;

import ca.team4519.frc2020.Subsystems.Drivebase;
import ca.team4519.lib.MechaLogger;
import ca.team4519.lib.MechaTimedRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends MechaTimedRobot
{
  // hellooooo

  Joystick driver = new Joystick(0);
  
  @Override
  public void robotInit()
  {

  }

  @Override
  public void autonomousInit()
  {

  }

  @Override
  public void autonomousPeriodic()
  {

  }

  @Override
  public void teleopInit()
  {

  }

  @Override
  public void teleopPeriodic()
  {
   // Drivebase.GrabInstance().setLeftRightPower(Drivebase.GrabInstance().arcade(driver.getRawAxis(1), driver.getRawAxis(4)));
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
    System.out.println("All Periodic Loop");
    MechaLogger.grabInstance().saveLogs();
    Drivebase.GrabInstance().update();
  }

}
