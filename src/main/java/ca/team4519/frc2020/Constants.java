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

    public static final boolean isLeftDriveGrayhillFlipped = false;
    public static final boolean isRightDriveGrayhillFlipped = false;

    //Turret
    public static final int turretPivotMotor = 6; //TODO Get CANID of talonSRX
    public static final int rightWheelNeo = 2;
    public static final int leftWheelNeo = 3;
    public static final int flywheelNeoCurrentLimit = 60;
        //Turret Sensors
    public static final int turretEncoderA = 2;
    public static final int turretEncoderB = 3;
    public static final int turretLimitSwitchL = 0;
    public static final int turretLimitSwitchR = 1;
    public static final int turretHomeDetector = 2;

    //Intake
    public static final int intakeLinkageMotor = 0; //TODO Get CANID of talonSRX
    public static final int intakeLinkageEncoderA = 0; //TODO Get DIO_A for Encoder
    public static final int intakeLinkageEncoderB = 1; //TODO Get DIO_B for Encoder
    public static final int IntakeArmPot = 0; //TODO Get AnalogIn for Pot
    public static final int intakeRoller = 0; //TODO Get CANID of talonSRX

    //Feeder
    public static final int intakeLinkageWheel = 0;
    public static final int feeder1 = 0; //nicole - don't know if this should go here
    public static final int feeder2 = 0; //same as above, ok this isn't working

     //Climber
    public static final int winchMotorL = 0;    //TODO Update with actual canid
    public static final int winchMotorR = 6;    //TODO Update with actual canid
    public static final int hookRelease = 0;    //TODO Update with actual canid

}