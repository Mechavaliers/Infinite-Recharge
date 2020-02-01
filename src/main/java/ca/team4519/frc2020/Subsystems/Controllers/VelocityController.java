package ca.team4519.frc2020.Subsystems.Controllers;

import ca.team4519.frc2020.Subsystems.Flywheel.Controllers;



public class VelocityController implements Controllers {

	public double kP, kI, kD, kF;
	
	public VelocityController(double kP, double kI, double kD, double kF) {
		super();

		this.kP = kP;
		this.kI = kI;
        this.kD = kD;
        this.kF = kF;
	}

	public double pid(final double target) {
        double kErr;
        double pOut;
        double iOut;
        double dOut;
        double output;

        kErr = target - (sensor.getRate() * tickConvert);

        double prevError = 0;
		double deltaError = prevError - kErr;
        prevError = kErr;
        double integralError = kErr;

        pOut = kErr * kP;
        iOut = integralError * kI;
        dOut = deltaError * kD;

        if (iOut > 1.0f) {
            iOut = 1.0f;
        }

        output = (pOut + iOut + dOut);

        if (output > 1.0f) {
            return 1.0f;
        }
        if (output < -1.0f) {
            return -1.0f;
        }
        return output;
    }

    public void setTarget(double targetVelocity) {

    }

    public void update(double currentVelocity) {

    }
    
}