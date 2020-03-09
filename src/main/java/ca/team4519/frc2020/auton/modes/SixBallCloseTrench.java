package ca.team4519.frc2020.auton.modes;

import ca.team4519.frc2020.auton.AutoMode;
import ca.team4519.frc2020.auton.AutonException;

public class SixBallCloseTrench extends AutoMode {

    @Override
    protected void sequence() throws AutonException {
       //Reverse
        drive.wantDriveLine(-20);
        waitForDist(-16, true, 0.5);
        turret.aimTurretAtPos(148);
        flywheel.wantFlywheel();
        waitForTurretAngle(148, 1.25);
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        waitForTurretAngle(turret.getWantedAngle(), 0.5);
        feeder.dump();
        wait(2.0);
        flywheel.wantOff();
        feeder.off();
        //Drive to get three balls
        drive.wantDriveLine(200, 65);
        intake.deployIntake();
        feeder.slowSuck();
        waitForDist(200, false, 4.5);
        feeder.off();
        intake.stowIntake();
        drive.wantDriveLine(-20);
        flywheel.wantFlywheel();
        //Return home
        waitForDist(-20, true, 3);
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        waitForTurretAngle(turret.getWantedAngle(), 0.25);
        feeder.dump();
        wait(2.0);
        flywheel.wantOff();
        feeder.off();
        

    }

    @Override
    public void init() {
        drive.zeroSensors();

    }
    
}