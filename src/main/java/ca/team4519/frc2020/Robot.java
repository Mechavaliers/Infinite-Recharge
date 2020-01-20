/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package ca.team4519.frc2020;

import ca.team4519.frc2020.Subsystems.Drivebase;
import ca.team4519.lib.MechaTimedRobot;

import edu.wpi.first.wpilibj.Joystick;

public class Robot extends MechaTimedRobot
{
  

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
    Drivebase.GrabInstance().setLeftRightPower(Drivebase.GrabInstance().arcade(driver.getRawAxis(1), driver.getRawAxis(4)));
  }

  @Override
  public void teleopPeriodic()
  {

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

  }

}
