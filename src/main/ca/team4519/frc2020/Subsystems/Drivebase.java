package ca.team4519.frc2020.subsystems;

import ca.team4519.Constants;
import ca.team4519.lib.Subsystem;
import ca.team4519.lib.Thread;
import edu.wpi.first.wpilibj.Encoder;

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

    public Drivebase()
    {

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
        
    }


}