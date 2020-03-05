package ca.team4519.frc2020.auton.modes;

import ca.team4519.frc2020.auton.AutonException;
import ca.team4519.frc2020.auton.AutoMode;

public class TestMode extends AutoMode {

    @Override
    protected void sequence() throws AutonException {
        System.out.println("Mode started");
        drive.wantDriveLine(60);
        System.out.println("did anything happen?");
    }

    @Override
    public void init() {
        drive.zeroSensors();

    }
    
}