package ca.team4519.frc2020.auton.modes;

import ca.team4519.frc2020.auton.AutonException;
import ca.team4519.frc2020.auton.AutoMode;

public class TestMode extends AutoMode {

    @Override
    protected void sequence() throws AutonException {
        turret.aimTurretAtPos(turret.getWantedAngle());
        waitForTurretAngle(turret.getWantedAngle(), 1.5);
        flywheel.wantFlywheel();
        wait(0.5);
        feeder.dump();
        wait(2.0);
        flywheel.wantOff();
        feeder.off();
        drive.wantDriveLine(-36);
        waitForDist(-36, false, 1.5);

    }

    @Override
    public void init() {
        drive.zeroSensors();

    }
    
}