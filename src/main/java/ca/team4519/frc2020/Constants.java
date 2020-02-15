package ca.team4519.frc2020;

public class Constants
{

    public static final double inchesToMeters = 0.0254;

    //Drivebase
    public static final int driveNeoCurrentLimit = 80; //In Amps
        //Drive Motors
    public static final int leftDriveNeoA = 6;
    public static final int leftDriveNeoB = 4;
    public static final int rightDriveNeoA = 5;
    public static final int rightDriveNeoB = 1;
        //Shifter
    public static final int shifter = 0;    

    //Turret
    public static final int turretPivotMotor = 9; //TODO Get CANID of talonSRX
    public static final int rightWheelNeo = 2;
    public static final int leftWheelNeo = 3;
    public static final int flywheelNeoCurrentLimit = 60;
        //Turret Sensors
    public static final int turretEncoderA = 0;
    public static final int turretEncoderB = 1;
    public static final int turretLimitSwitchL = 4;
    public static final int turretLimitSwitchR = 5;
    public static final int turretHomeDetector = 9;

    //Intake
    public static final int intakeLinkageMotor = 8; //TODO Get CANID of talonSRX
    public static final int intakeLinkageEncoderA = 2; //TODO Get DIO_A for Encoder
    public static final int intakeLinkageEncoderB = 3; //TODO Get DIO_B for Encoder
    public static final int IntakeArmPot = 0; //TODO Get AnalogIn for Pot
    public static final int intakeRoller = 11; //TODO Get CANID of talonSRX

    //Feeder
    public static final int intakeLinkageWheel = 0;
    public static final int feeder1 = 10; //nicole - don't know if this should go here
    public static final int feeder2 = 7; //same as above, ok this isn't working uuper?

     //Climber
    public static final int winchMotorL = 0;    //TODO Update with actual canid
    public static final int winchMotorR = 6;    //TODO Update with actual canid
    public static final int hookRelease = 0;    //TODO Update with actual canid

}