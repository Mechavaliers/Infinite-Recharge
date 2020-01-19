package ca.team4519.frc2020.Subsystems;

import ca.team4519.frc2020.Constants;
import ca.team4519.frc2020.Gains;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;;

public class Drivebase extends Subsystem implements Thread {

    public static Drivebase thisInstance = new Drivebase();

    private final CANSparkMax rightDriveNeoA;
    private final CANSparkMax rightDriveNeoB;
    private final CANSparkMax leftDriveNeoA;
    private final CANSparkMax leftDriveNeoB;

    private final Encoder leftDriveGrayhill;
    private final Encoder rightDriveGrayhill;

    private final Solenoid shifter;

    private final AHRS navX;

    private final boolean isShifting;

    public static Drivebase GrabInstance()
    {

        return thisInstance;

    }

    public Drivebase()
    {
        thisInstance = this;

        rightDriveNeoA = new CANSparkMax(Constants.rightDriveNeoA, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoA.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoA.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);

        rightDriveNeoB = new CANSparkMax(Constants.rightDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        rightDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        rightDriveNeoB.follow(rightDriveNeoA);

        leftDriveNeoA = new CANSparkMax(Constants.leftDriveNeoA, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoA.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);

        leftDriveNeoB = new CANSparkMax(Constants.leftDriveNeoB, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setMotorType(CANSparkMaxLowLevel.MotorType.kBrushless);
        leftDriveNeoB.setSmartCurrentLimit(Constants.driveNeoCurrentLimit);
        leftDriveNeoB.follow(rightDriveNeoA);

        leftDriveGrayhill = new Encoder(Constants.leftDriveGrayhillA, Constants.leftDriveGrayhillB, Constants.isLeftDriveGrayhillFlipped, CounterBase.EncodingType.k4X);
        leftDriveGrayhill.setDistancePerPulse(Gains.Drive.EncoderTicksPerRev);
        rightDriveGrayhill = new Encoder(Constants.rightDriveGrayhillA, Constants.rightDriveGrayhillB, Constants.isRightDriveGrayhillFlipped, CounterBase.EncodingType.k4X);
        rightDriveGrayhill.setDistancePerPulse(Gains.Drive.EncoderTicksPerRev);

        shifter = new Solenoid(Constants.shifter);

        navX = new AHRS(SerialPort.Port.kMXP);
        
    }

    public void  shift(boolean triggerShift)
    {
        if(triggerShift)
        {
            shifter.set(Gains.Drive.Shifter_LOW_GEAR);
            
        }
        else
        {
            shifter.set(Gains.Drive.Shifter_HIGH_GEAR);
        }
    }

    @Override
    public void loops() {
        // TODO Auto-generated method stub

    }

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableSubsystem() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}