package ca.team4519.frc2020.subsystems;

import ca.team4519.Constants;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;

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

    public Drivebase()
    {
        thisInstance = this;

        rightDriveNeoA = new CANSparkMax(Constants.rightDriveNeoA, CANSparkMaxLowLevel.kBrushless);
        rightDriveNeoA.setMotorType(CANSparkMaxLowLevel.kBrushless);
        rightDriveNeoA.setSmartCurrentLimit(Constants.DriveNeoCurrentLimit);

        rightDriveNeoB = new CANSparkMax(Constants.rightDriveNeoB, CANSparkMaxLowLevel.kBrushless);
        rightDriveNeoB.setMotorType(CANSparkMaxLowLevel.kBrushless);
        rightDriveNeoB.setSmartCurrentLimit(Constants.DriveNeoCurrentLimit);
        rightDriveNeoB.follow(rightDriveNeoA);

        leftDriveNeoA = new CANSparkMax(Constants.leftDriveNeoA, CANSparkMaxLowLevel.kBrushless);
        leftDriveNeoA.setMotorType(CANSparkMaxLowLevel.kBrushless);
        leftDriveNeoA.setSmartCurrentLimit(Constants.DriveNeoCurrentLimit);

        leftDriveNeoB = new CANSparkMax(Constants.leftDriveNeoB, CANSparkMaxLowLevel.kBrushless);
        leftDriveNeoB.setMotorType(CANSparkMaxLowLevel.kBrushless);
        leftDriveNeoB.setSmartCurrentLimit(Constants.DriveNeoCurrentLimit);
        leftDriveNeoB.follow(rightDriveNeoA);

        leftDriveGrayhill = new Encoder(Constants.leftDriveGrayhillA, Constants.leftDriveGrayhillB, Constants.isLeftDriveGrayhillFlipped, CounterBase.EncodingType.k4X);
        leftDriveGrayhill.setDistancePerPulse(Gains.DrivebaseGains.EncoderTicksPerRev);
        rightDriveGrayhill = new encoder(Constants.rightDriveGrayhillA, Constants.rightDriveGrayhillB, Constants.isRightDriveGrayhilllipped, CounterBase.EncodingType.k4X);
        rightDriveGrayhill.setDistancePerPulse(Gains.DrivebaseGains.EncoderTicksPerRev);

        shifter = new Solenoid(Constants.shifter);
        
    }


}