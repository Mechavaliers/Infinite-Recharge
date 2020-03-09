package ca.team4519.frc2020.auton.modes;

import ca.team4519.frc2020.auton.AutoMode;
import ca.team4519.frc2020.auton.AutonException;

public class AimBackShootThree extends AutoMode {

    @Override
    protected void sequence() throws AutonException {
        
        flywheel.wantFlywheel();
        drive.wantDriveLine(22.5, 65);
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        turret.aimTurretAtPos(turret.getWantedAngle());
        wait(0.5);
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