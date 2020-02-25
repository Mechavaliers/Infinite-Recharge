package ca.team4519.frc2020;

public class Gains{

	public static double CONTROL_LOOP_TIME_SECONDS = 0.005;	//200hz
	public static int CONTROL_LOOP_TIME_MILLISECOND = 5;	//Neos require this conversion
	public static double NEO_TicksPerRev = 1/42.0;

	public static final class Drive {
		private Drive() {}
		
		public static final boolean Shifter_HIGH_GEAR = true;
		public static final boolean Shifter_LOW_GEAR = false;
		
		public static final double HANDLING_MODIFIER = 1.0;	//TODO Update this
		public static final double PATH_TOLLERANCE = 0.25;	//TODO Update this

		public static double NEO_HIGH_CorrectedTicksPerRev = NEO_TicksPerRev + 5; // TODO Put the high gear ratio in here, only used in auto...for now?
		public static double NEO_LOW_CorrectedTicksPerRev = NEO_TicksPerRev + 5; // TODO Put the low gear ratio in here - For drivebase lock
		//	Inches/Seconds
		public static double ROBOT_MAX_VELOCITY= 180.0;			//
		public static double ROBOT_MAX_ACCELERATION = 45.0;	//TODO Update this
		public static double ROBOT_MAX_JERK = 23; //TOT UPDATE THis
		public static double ROBOT_MAX_ROTATIONAL_VELOCITY = 360.0;	//TODO Update this
		public static double ROBOT_MAX_ROTATIONAL_ACCELERATION = 250.0;	//TODO Update this
		public static double Wheelbase_Width = 21.868502;	//Inches
		public static double Wheelbase_Length = 39.25; 	//TODO Update this
		public static double WheelSize_Inches = 6.250; //TODO Measure wheel OD
		public static double HIGH_EncoderTicksPerRev =(( 2 * Math.PI * WheelSize_Inches ) / NEO_HIGH_CorrectedTicksPerRev);	
		public static double LOW_EncoderTicksPerRev =(( 2 * Math.PI * WheelSize_Inches ) / NEO_LOW_CorrectedTicksPerRev);
	
		public static final double Dist_P = 0.0;	//TODO Tune this
		public static final double Dist_I = 0.0;	//TODO tune this
		public static final double Dist_D = 0.0;	//TODO Tune this
		public static final double Dist_V = 1 / ROBOT_MAX_VELOCITY;			//CHANGE AT COMP
		public static final double Dist_A = 0;	//TODO Tune this
		public static final double DistTurn_P = 1.0 / 22.5;	//TODO Tune this
		public static final double DistTurn_I = 0.0;	//TODO Tune this
		public static final double DistTurn_D = 0.0;	//TODO Tune this
		public static final double Dist_Tollerance = 0.0;	// + or - target distance

		//This works in Radians
		public static final double Turn_P = 0.0175; //0.0825	//TODO Tune this
		public static final double Turn_I = 0.0;	//TODO Tune this
		public static final double Turn_D = 0.00; // 0.015	//TODO Tune this
		public static final double Turn_V = 1 / 300; //0.05	//TODO Tune this
		public static final double Turn_A = 0.0; //0.00225	//TODO Tune this
		public static final double Turn_F = 0.01; //TODO Tune This
		public static final double Turn_Tollerance = 0.0225; 
	}

	public static final class Intake {
		private Intake() {}

		public static final double IntakeSpeed = 0.0;	//TODO Tune This
	
		public static final double LinkagePot_MinVolage = 0.283;	//TODO Tune This
		public static final double LinkagePot_MaxVoltage = 0.302;	//TODO Tune This
		public static final double LinkagePot_MinAngle = 0.0;	//TODO Tune This
		public static final double LinkagePot_MaxAngle = 100.0;	//TODO Tune This

		public static final double LinkagePos_Stowed = -2.0;	//TODO Tune This
		public static final double LinkagePos_Deployed = 85.0;	//TODO Tune This
		public static final double LinkagePos_DeployedOnAngle = 45.0;	//TODO Tune This

		public static final double LINKAGE_MAX_VELOCITY = 0.0;	//TODO Tune This
		public static final double LINKAGE_MAX_ACCELERATION = 0.0;	//TODO Tune This

		public static final double Linkage_P = 0.005;	//TODO Tune This
		public static final double Linkage_I = 0.0;	//TODO Tune This
		public static final double Linkage_D = 0.0;	//TODO Tune This
		public static final double Linkage_V = 0.001;	//TODO Tune This
		public static final double Linakge_A = 0.0;	//TODO Tune This
		public static final double Linkage_Tollerance = 0.0;	//TODO Tune This
	}

	public static final class Turret {
		private Turret() {}

		public static final double TURRET_MAX_VELOCITY = 0.0;	//TODO Tune This
		public static final double TURRET_MAX_ACCELERATION = 0.0;	//TODO Tune This

		public static final double Turret_P = 0.00525;	//TODO Tune This
		public static final double Turret_I = 0.0;	//TODO Tune This
		public static final double Turret_D = 0.0;	//TODO Tune This
		public static final double Turret_V = 0.0;	//TODO Tune This
		public static final double Turret_A = 0.0;	//TODO Tune This
		public static final double Turret_Tollerance = 0.0;	//TODO Tune This

		public static final double turretAngle_ConvertedHigh = -180.0;	//TODO Tune this
		public static final double turretAngle_ConvertedLow = 90.0;	//TODO Tune This
		public static final double turretAngle_EncoderHigh = 1500.0;	//TODO Tune this
		public static final double turretAngle_EncoderLow = -675.0;	//TODO Tune This -675
		public static final double turretAngle_Zero = 0.0;

		public static final double slope = (turretAngle_ConvertedHigh - turretAngle_ConvertedLow)/(turretAngle_EncoderHigh - turretAngle_EncoderLow);
		public static final double offset = (turretAngle_EncoderLow - slope * turretAngle_EncoderHigh);


		public static final double Intent_RightConverted = 90;	//TODO Get Value from turret after its calibrated
		public static final double Intent_ForwardConverted = 0;	//TODO Get Value from turret after its calibrated
		public static final double Intent_ReverseConverted = 175;	//TODO Get Value from turret after its calibrated
		public static final double Intent_LeftConverted = -90 ;
	}

	public static final class Flywheel
	{
		private Flywheel() {}

		public static final double Flywheel_P = 0.0;	//TODO Tune this
		public static final double Flywheel_I = 0.03825;	//TODO Tune This 0.035
		public static final double Flywheel_D = 0.0;	//TODO Tune This
		public static final double Flywheel_F = 0.75;
		public static final double ShotRPM = 125; //TODO Tune This
	}

	public static final class Feeder {
		private Feeder() {}
		public static final double BallDetectorRange = 0.0; //TODO Tune This if it even becomes a thing

	}

	public static final class PDP {
		private PDP() {}

		public static final int LeftDriveNeoA = 2;
		public static final int LeftDriveNeoB = 1;
		public static final int RightDriveNeoA = 13;
		public static final int RightDriveNeoB = 14;

		public static final int IntakeRollers = 0;
		public static final int IntakePivot = 15;
		public static final int FeederLower = 4;
		public static final int FeederUpper = 11;

		public static final int TurretPivot = 10;
		public static final int LeftFlywheel = 3;	//TODO Tune This
		public static final int RightFlywheel = 12;	//TODO Tune This
		
	}
}