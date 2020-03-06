package ca.team4519.frc2020.auton.modes;

import ca.team4519.frc2020.auton.AutoMode;
import ca.team4519.frc2020.auton.AutonException;

public class SixBallCloseTrench extends AutoMode {

    @Override
    protected void sequence() throws AutonException {
        turret.aimTurretAtPos(148);
        flywheel.wantFlywheel();
        waitForTurretAngle(148, 1.25);
        turret.aimTurretAtPos(turret.getWantedAngle());
        feeder.dump();
        wait(2.0);
        flywheel.wantOff();
        feeder.off();
        drive.wantDriveLine(180, 65);
        intake.deployIntake();
        feeder.slowSuck();
        waitForDist(180, false, 5);
        feeder.off();
        intake.stowIntake();

        drive.wantDriveLine(0);
        flywheel.wantFlywheel();
        waitForDist(0, true, 3);
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