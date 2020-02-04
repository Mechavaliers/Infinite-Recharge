package ca.team4519.frc2020;

public class Constants {

    public static final double inchesToMeters = 0.0254;

    //Drivebase
    public static final int driveNeoCurrentLimit = 80; //In Amps
        //Drive Motors
    public static final int leftDriveNeoA = 1;  
    public static final int leftDriveNeoB = 0; 
    public static final int rightDriveNeoA = 2;
    public static final int rightDriveNeoB = 3;
        //Shifter
    public static final int shifter = 0;    
        //Encoders
    public static final int leftDriveGrayhillA = 0;  
    public static final int leftDriveGrayhillB = 1;  
    public static final int rightDriveGrayhillA = 2; 
    public static final int rightDriveGrayhillB = 3;

    public static final boolean isLeftDriveGrayhillFlipped = false;
    public static final boolean isRightDriveGrayhillFlipped = false;

    //Flywheel
    public static final int rightWheelNeo = 4;
    public static final int leftWheelNeo = 5;
    public static final int flywheelNeoCurrentLimit = 60;

    //Intake
    public static final int intakeLinkageMotor = 0; //TODO Get CANID of talonSRX
    public static final int IntakeArmPot = 0;
    public static final int intakeLinkageEncoderA = 0;
    public static final int intakeLinkageEncoderB = 1;

}