package org.firstinspires.ftc.teamcode.autoRes.commands;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.SubSystems.Arm;
import org.firstinspires.ftc.teamcode.SubSystems.Chassis;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

import java.util.Locale;

/**
 * Autonomous Mode Usecase 1
 *
 * Description : Start on wall in Loading Zone, identify and move 1 skystone to Building zone and park next to skybridge
 *
 * Steps:
 * Robot starts on SB5
 * On start, robot opens wrist to front position
 * Lift Arm to Sense Position
 * Move by distance X forward near SB5
 * Check on color sensor, for Skystone
 * If Skystone, record Skystone position as SB5, Go to Step 10
 * Else move robot to SB4. Check on color sensor for Skystone.
 * If Skystone, record Skystone position as SB4, Go to Step 10
 * Else move robot to SB3. record skystone position as SB3.
 * Grip and pick the block.
 * Lift Arm to Level 2 Tray Height
 * Slide back to edge of B2,
 * Turn 90 degrees Left
 * Move to B4
 * Drop block
 * Move in between B4 and B3 (Parking)
 *
 *
 */

@Autonomous(name = "AutoUseCaseTester", group = "Autonomous")
public class AutonomousTester extends LinearOpMode {

    Intake autoIntake;
    Arm autoArm;
    Chassis autoChassis;

    int skystonePosition;

    public double robotDepth = 15.7; // Ball on wall to Edge of Chassis Touch sensor
    public double robotWidth = 17.0; // Wheel edge to wheel edge

    int playingAlliance = 1; //1 for Blue, -1 for Red

    public boolean finalStateAchieved = false; //1 when reached final parking state

    ElapsedTime AutonomousTimeOut = new ElapsedTime();

    boolean parked = false;

    boolean HzDEBUG_FLAG = true;

    /**
     * Template runOpMode code. Only change Usecase function and call here.
     * Refer to Autonomous Command Syntax to put right values
     * <p>
     * All Usecases written assuming playingAlliance = 1 meaning Blue, -1 for Red.
     *
     * throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {
        
        autoIntake = new Intake(hardwareMap);
        autoArm = new Arm(hardwareMap);
        autoChassis = new Chassis(hardwareMap);
        
        telemetry.setAutoClear(false);
        telemetry.addData("Init Autonomous Tests", "v:1.0");
        
        //Robot starts on A2
        waitForStart();

        //Initialize on press of play
        autoChassis.initChassis();
        autoArm.initArm();
        autoIntake.initIntake();

        while (opModeIsActive() && !parked) {
            AutonomousTester();
        }

        if(HzDEBUG_FLAG) printDebugMessages();
        telemetry.update();

        sleep(10000);

    }

    void AutonomousTester() {


        // Robot starts on SB5

        //On start, Lift arm and robot opens wrist to front position
        //initArm() and initIntake() should do this on class initialization

        // Lift Arm to Sense Position

        sleep(1000);
        autoArm.moveArm_groundLevel();
        sleep(1000);
        autoArm.moveArm_detectSkystoneLevel();
        sleep(1000);
        // Move by distance X forward near SB5 : 6 inches to skystone
        double robotToNearSkystone = 20;
        autoChassis.runFwdBackLeftRight(robotToNearSkystone,0,0.1);

        sleep(100);

        // Check on color sensor, for Skystone
        moveTillStoneDetected();
        sleep(1000);
        // If Skystone, record Skystone position as SB5, Go to Step 10

        skystonePosition = 5; // Assume current position is skystone
        double stoneTostone = 8;
        if ((autoIntake.stoneDetected) && (!autoIntake.skystoneDetected)) {
            //Skystone not detected, move to SB4
            skystonePosition = 4;
            autoChassis.runFwdBackLeftRight(stoneTostone,playingAlliance,0.1);
        }
        sleep(1000);

        // Check on color sensor, for Skystone
        moveTillStoneDetected();
        sleep(1000);

        if ((autoIntake.stoneDetected) && (!autoIntake.skystoneDetected)) {
            //Skystone not detected, move to SB3
            skystonePosition = 3;
            autoChassis.runFwdBackLeftRight(stoneTostone,playingAlliance,0.1);
        }
        sleep(1000);

        // Drop Arm and Grip the block.
        autoArm.moveArm_groundLevel();
        sleep(1000);
        autoIntake.closeGrip();

        // Slide back to edge of B2, 10 inches
        autoChassis.runFwdBackLeftRight(-10,0,0.1);

        sleep(1000);
        // Turn 90 degrees Left
        autoChassis.turnby90degree(playingAlliance*(-1),0.1);
        sleep(1000);

        parked = true;

        //End of Usecase : Should be parked at this time.
    }


    /**
     * Method to move till Skystone is detected using color sensor and distance sensor
     */
    void moveTillStoneDetected(){
        //public void runTill_ChassisLeftColorSensorIsBlue(double max_stop_distance, double straveDirection, double power){

        double stoneDetect_max_stop_distance = 5; //max is 6"
        autoChassis.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
        autoChassis.resetChassis();

        //Max Total Rotations of wheel = distance / circumference of wheel
        double targetRotations = stoneDetect_max_stop_distance/(2*Math.PI*autoChassis.wheelRadius);

        while (!autoIntake.detectSkytoneAndType() && (Math.abs(autoChassis.backLeft.getCurrentPosition()) < Math.abs(autoChassis.ChassisMotorEncoderCount * targetRotations))) {
            autoChassis.frontLeft.setPower(0.05);
            autoChassis.frontRight.setPower(0.05);
            autoChassis.backLeft.setPower(0.05);
            autoChassis.backRight.setPower(0.05);
        }

        autoChassis.setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //#TOBECHECKED TO AVOID JERK
        autoChassis.frontLeft.setPower(0.0);
        autoChassis.frontRight.setPower(0.0);
        autoChassis.backLeft.setPower(0.0);
        autoChassis.backRight.setPower(0.0);

    } //return stone detected autoIntake.stoneDetected and if skystone autoIntake.SkystoneDetected

    void printDebugMessages(){
        telemetry.setAutoClear(true);
        telemetry.addData("HzDEBUG_FLAG is : ", HzDEBUG_FLAG);

        telemetry.addData("Intake.detectSkystoneColor.Red ", autoIntake.detectSkystoneColor.red() );
        telemetry.addData("Intake.detectSkystoneColor.Green", autoIntake.detectSkystoneColor.green() );
        telemetry.addData("Intake.detectSkystoneColor.Blue", autoIntake.detectSkystoneColor.blue() );
        telemetry.addData("Intake.detectSkystoneColor.Alpha", autoIntake.detectSkystoneColor.alpha() );
        telemetry.addData("Intake.detectSkystonedistance.Distance", String.format(Locale.US, "%.02f", autoIntake.detectSkystoneDistance.getDistance(DistanceUnit.INCH)));
        telemetry.addData("Intake.detectSkystonedetected", autoIntake.stoneDetected);
        telemetry.addData("Intake.detectSkystonedetected", autoIntake.skystoneDetected);

    }

}
