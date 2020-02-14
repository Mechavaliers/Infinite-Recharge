package ca.team4519.frc2020;

public class Gains{

	public static double CONTROL_LOOP_TIME = 0.005;	//200hz
	public static double NEO_TicksPerRev = 42.0;

	public static final class Drive {
		private Drive() {}
		
		public static boolean Shifter_HIGH_GEAR = true;
		public static boolean Shifter_LOW_GEAR = false;
		
		public static double HANDLING_MODIFIER = 1.0;	//TODO Update this
		public static double PATH_TOLLERANCE = 0.25;	//TODO Update this

		public static double NEO_CorrectedTicksPerRev = NEO_TicksPerRev + 5; // TODO Put the high gear ratio in here, only used in auto...for now?
		
		//	Inches/Seconds
		public static double ROBOT_MAX_VELOCITY= 180.0;			//
		public static double ROBOT_MAX_ACCELERATION = 45.0;	//TODO Update this
		public static double ROBOT_MAX_JERK = 23; //TOT UPDATE THis
		public static double ROBOT_MAX_ROTATIONAL_VELOCITY = 360.0;	//TODO Update this
		public static double ROBOT_MAX_ROTATIONAL_ACCELERATION = 250.0;	//TODO Update this
		public static double Wheelbase_Width = 21.868502;	//Inches
		public static double Wheelbase_Length = 39.25; 	//TODO Update this
		public static double WheelSize_Inches = 6.250;
		public static double EncoderTicksPerRev =(( 2 * Math.PI * WheelSize_Inches ) / NEO_CorrectedTicksPerRev);	
	
		public static double Dist_P = 0.0;	//TODO Tune this
		public static double Dist_I = 0.0;	//TODO tune this
		public static double Dist_D = 0.0;	//TODO Tune this
		public static double Dist_V = 1 / ROBOT_MAX_VELOCITY;			//CHANGE AT COMP
		public static double Dist_A = 0;	//TODO Tune this
		public static double DistTurn_P = 1.0 / 22.5;	//TODO Tune this
		public static double DistTurn_I = 0.0;	//TODO Tune this
		public static double DistTurn_D = 0.0;	//TODO Tune this
		public static double Dist_Tollerance = 0.0;	// + or - target distance

		//This works in Radians
		public static double Turn_P = 0.0175; //0.0825	//TODO Tune this
		public static double Turn_I = 0.0;	//TODO Tune this
		public static double Turn_D = 0.00; // 0.015	//TODO Tune this
		public static double Turn_V = 1 / 300; //0.05	//TODO Tune this
		public static double Turn_A = 0.0; //0.00225	//TODO Tune this
		public static double Turn_F = 0.01; //TODO Tune This
		public static double Turn_Tollerance = 0.0225; 
	}

	public static final class Intake {
		private Intake() {}

		public static double IntakeSpeed = 0.0;	//TODO Tune This
	
		public static double LinkagePot_MinVolage = 0.0;	//TODO Tune This
		public static double LinkagePot_MaxVoltage = 0.0;	//TODO Tune This
		public static double LinkagePot_MinAngle = 0.0;	//TODO Tune This
		public static double LinkagePot_MaxAngle = 0.0;	//TODO Tune This

		public static double LinkagePos_Stowed = 0.0;	//TODO Tune This
		public static double LinkagePos_Deployed = 0.0;	//TODO Tune This
		public static double LinkagePos_DeployedOnAngle = 0.0;	//TODO Tune This

		public static double LINKAGE_MAX_VELOCITY = 0.0;	//TODO Tune This
		public static double LINKAGE_MAX_ACCELERATION = 0.0;	//TODO Tune This

		public static double Linkage_P = 0.0;	//TODO Tune This
		public static double Linkage_I = 0.0;	//TODO Tune This
		public static double Linkage_D = 0.0;	//TODO Tune This
		public static double Linkage_V = 0.0;	//TODO Tune This
		public static double Linakge_A = 0.0;	//TODO Tune This
		public static double Linkage_Tollerance = 0.0;	//TODO Tune This
	}

	public static final class Turret {
		private Turret() {}

		public static double TURRET_MAX_VELOCITY = 0.0;	//TODO Tune This
		public static double TURRET_MAX_ACCELERATION = 0.0;	//TODO Tune This

		public static double Turret_P = 0.0;	//TODO Tune This
		public static double Turret_I = 0.0;	//TODO Tune This
		public static double Turret_D = 0.0;	//TODO Tune This
		public static double Turret_V = 0.0;	//TODO Tune This
		public static double Turret_A = 0.0;	//TODO Tune This
		public static double Turret_Tollerance = 0.0;	//TODO Tune This

		public static double turretAngle_ConvertedHigh = 0.0;	//TODO Tune this
		public static double turretAngle_ConvertedLow = 0.0;	//TODO Tune This
		public static double turretAngle_EncoderHigh = 0.0;	//TODO Tune this
		public static double turretAngle_EncoderLow = 0.0;	//TODO Tune This
		public static double turretAngle_Zero = 0.0;

		public static double slope = (turretAngle_ConvertedHigh - turretAngle_ConvertedLow)/(turretAngle_EncoderHigh - turretAngle_EncoderLow);
		public static double offset = (turretAngle_EncoderLow - slope * turretAngle_EncoderHigh);


		public static double Intent_RightConverted = 6;	//TODO Get Value from turret after its calibrated
		public static double Intent_ForwardConverted = 5;	//TODO Get Value from turret after its calibrated
		public static double Intent_ReverseConverted = 56;	//TODO Get Value from turret after its calibrated
		public static double Intent_LeftConverted = 65;


	}
	public static final class Feeder {
		private Feeder() {}
		public static double BallDetectorRange = 0.0; //TODO Tune This if it even becomes a thing

	}

	public static final class PDP {
		private PDP() {}

		public static final int LeftDriveNeoA = 0;
		public static final int LeftDriveNeoB = 0;
		public static final int RightDriveNeoA = 0;
		public static final int RightDriveNeoB = 0;

		public static final int IntakeRollers = 0;
		public static final int IntakePivot = 0;
		public static final int FeederLower = 0;
		public static final int FeederUpper = 0;

		public static final int TurretPivot = 0;
		public static final int LeftFlywheel = 0;
		public static final int RightFlywheel = 0;
		
	}
}