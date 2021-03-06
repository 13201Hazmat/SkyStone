package org.firstinspires.ftc.teamcode.UnitTesting;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
@Disabled
@Autonomous(name = "AutoUseCaseTester", group = "AutoTest")
public class AutonomousTester extends LinearOpMode {

    Intake autoIntake;
    Arm autoArm;
    Chassis autoChassis;

    public int skystonePosition;

    public int robotDepth = 17; // Ball on wall to Edge of Chassis Touch sensor
    public int robotWidth = 17; // Wheel edge to wheel edge

    int playingAlliance = 1; //1 for Blue, -1 for Red
    boolean parkingPlaceNearSkyBridge = true;//false for near wall, true for near NeutralSkybridge

    boolean parked = false; // Will be true once robot is parked

    ElapsedTime AutonomousTimeOut = new ElapsedTime();

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

        //Robot starts on A2
        waitForStart();

        //Initialize on press of play
        autoChassis.initChassis();
        autoArm.initArm();
        autoIntake.initIntake();

        while (opModeIsActive() && !isStopRequested() && !parked) {
            AutonomousUC2Commands();
        }
    }

    void AutonomousUC2Commands() {

        //Robot starts between A4, A5 such that it can slight in front of skybridge neutral zone floor

        //On start, Lift arm and robot opens wrist to front position
        //initArm() and initIntake() should do this on class initialization

        //Lift Arm to AboveFoundation level
        //moveArm_aboveFoundationLevel();
        //sleep(500);
        //Move robot to in between C5 and C6
        double robotToFoundation = 15;
        runFwdBackLeftRight(playingAlliance*robotToFoundation,1,0.25);
        sleep(500);



        //Move forward till Chassis bumber limit switch is pressed.
        runFwdTill_frontleftChassisTouchSensor_Pressed(7, 0.1);
        sleep(500);

        //Drop Arm to OnFoundation level
        //moveArm_onFoundationLevel();
        sleep(500);

        //Move foundation to wall and then turn
        runFwdBackLeftRight(6,0,0.25);
        sleep(500);

        /*
        //Move Robot Left toward A4 (for XX rotations). Friction will cause Robot to rotate towards A6
        double foundationTurnDistance = 85;
        runFwdBackLeftRight(playingAlliance*foundationTurnDistance,-1,0.25);
        sleep(500);

        //Drop Arm to OnFoundation level
        moveArm_onFoundationLevel();
        sleep(500);

        //Pull back till wall is hit (Motor does not move)
        double foundationBackToWall = 15; // #TOBECORRECTED WITH ENCODER NOT MOVING CODE
        runFwdBackLeftRight(-foundationBackToWall,0,0.1);
        sleep(100);
        //Slide left till Motor does not move (Foundation corner on Edge)
        //#TOBEWRITTEN

        //Lift Arm to Above foundation level
        moveArm_aboveFoundationLevel();
        sleep(500);

        //Push forward to move foundation to end of line
        double foundationtoEdgeofBuildingSite = 1;
        runFwdBackLeftRight(foundationtoEdgeofBuildingSite,0,0.1);
        sleep(100);

        //Move back till wall is hit
        runFwdBackLeftRight(-foundationtoEdgeofBuildingSite,0,0.25);
        sleep(500);

        //Move out of foundation area
        runTill_ChassisRightColorSensorIsRed(30, playingAlliance, 0.25);

        //Close Grip
        moveWristToClose();
        sleep(100);

        turnArmBrakeModeOn();
        sleep(500);


        //Optional : Move to park near skybridge Neutral
        if (parkingPlaceNearSkyBridge){
            runFwdBackLeftRight(25,0,0.25);
        }

        //Park near wall
        //Move right by distance or till Chassis light sensor does not detect Blue line to be under blue skybridge
        if (playingAlliance == 1) {
            //Blue Alliance
            runTill_ChassisRightColorSensorIsBlue(40, 1, 0.25);
        } else {
            //Red Alliance
            runTill_ChassisLeftColorSensorIsRed(40, -1, 0.25);
        }
    */
        //Reached Parking position
        parked = true;
    }


    /* Method to move chassis based on computed vector inputs for a set distance
     * To be used in Autonomous mode for moving by distance or turning by angle
     * Uses PID loop in motors to ensure motion without errors
     *
     * @param distance +ve for forward, -ve for backward
     * @param strafeDirection 0 for forward or backward, 1 for right, -1 for left
     * @param power to run motors
     */
    public void runFwdBackLeftRight(double distance, double strafeDirection, double power){
        autoChassis.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
        autoChassis.resetChassis();

        //Max Total Rotations of wheel = distance / circumference of wheel
        double targetRotations = distance/(2*Math.PI*autoChassis.wheelRadius);

        //set fwdbackdirection, +ve for forward and negative for backward
        double fwdbackdirection = distance /Math.abs(distance);

        while (!isStopRequested() && (Math.abs(autoChassis.backLeft.getCurrentPosition()) < Math.abs(autoChassis.ChassisMotorEncoderCount * targetRotations))) {
            if(strafeDirection == 0) {
                //Go forward or backward
                autoChassis.frontLeft.setPower(fwdbackdirection*power);
                autoChassis.frontRight.setPower(fwdbackdirection*power);
                autoChassis.backLeft.setPower(fwdbackdirection*power);
                autoChassis.backRight.setPower(fwdbackdirection*power);
            } else {
                autoChassis.frontLeft.setPower(strafeDirection* power);
                autoChassis.frontRight.setPower(-strafeDirection* power);
                autoChassis.backLeft.setPower(-strafeDirection* power);
                autoChassis.backRight.setPower(strafeDirection* power);
            }
        }
        autoChassis.setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //#TOBECHECKED TO AVOID JERK
        autoChassis.frontLeft.setPower(0.0);
        autoChassis.frontRight.setPower(0.0);
        autoChassis.backLeft.setPower(0.0);
        autoChassis.backRight.setPower(0.0);
    }

    /**
     * Method to move chassis based on computed vector inputs for a set max_stop_distance
     * Till frontleftChassisTouchSensor is pressed.
     * To be used in Autonomous mode for moving by distance or turning by angle
     * Uses PID loop in motors to ensure motion without errors
     * @param max_stop_distance in same unit of measure as wheelRadius
     * @param power to run motors
     */
    public void runFwdTill_frontleftChassisTouchSensor_Pressed(double max_stop_distance, double power) {
        autoChassis.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
        autoChassis.resetChassis();

        //Max Total Rotations of wheel = distance / circumference of wheel
        double targetRotations = max_stop_distance/(2*Math.PI*autoChassis.wheelRadius);

        while (!isStopRequested() && !autoChassis.frontleftChassisTouchSensor.isPressed() &&
                (Math.abs(autoChassis.backLeft.getCurrentPosition()) < Math.abs(autoChassis.ChassisMotorEncoderCount * targetRotations))) {
            autoChassis.frontLeft.setPower(power);
            autoChassis.frontRight.setPower(power);
            autoChassis.backLeft.setPower(power);
            autoChassis.backRight.setPower(power);
        }
        autoChassis.setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //#TOBECHECKED TO AVOID JERK
        autoChassis.frontLeft.setPower(0.0);
        autoChassis.frontRight.setPower(0.0);
        autoChassis.backLeft.setPower(0.0);
        autoChassis.backRight.setPower(0.0);
    }

    /**
     * Method to move Arm to onFoundationLevel and turn Brake Mode ON
     */
    public void moveArm_onFoundationLevel(){
        while (!isStopRequested()){
            autoArm.armMotor.setTargetPosition(autoArm.onFoundationLevel);
        }
        autoArm.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        autoArm.runArmToLevel();
    }

    /**
     * Method to move Arm to aboveFoundationLevel and turn Brake Mode ON
     */
    public void moveArm_aboveFoundationLevel(){
        while (!isStopRequested()){
            autoArm.armMotor.setTargetPosition(autoArm.aboveFoundationLevel);
        }
        turnArmBrakeModeOn();
        autoArm.runArmToLevel();
    }

    /**
     * Method to move chassis based on computed vector inputs for a set max_stop_distance
     * Till team color is identified below Chassis
     * To be used in Autonomous mode for moving by distance or turning by angle
     * Uses PID loop in motors to ensure motion without errors
     * @param max_stop_distance Max distance to stop
     * @param strafeDirection 0 for forward or backward, 1 for right, -1 for left
     * @param power to run motors at
     */
    public void runTill_ChassisRightColorSensorIsRed(double max_stop_distance, double strafeDirection, double power){
        autoChassis.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
        autoChassis.resetChassis();

        //Max Total Rotations of wheel = distance / circumference of wheel
        double targetRotations = max_stop_distance/(2*Math.PI*autoChassis.wheelRadius);

        //set fwdbackdirection, +ve for forward and negative for backward
        double fwdbackdirection = max_stop_distance /Math.abs(max_stop_distance);

        while (!isStopRequested() && !autoChassis.rightColorSensorIsRed() &&
                (Math.abs(autoChassis.backLeft.getCurrentPosition()) < Math.abs(autoChassis.ChassisMotorEncoderCount * targetRotations))) {
            if(strafeDirection == 0) {
                //Go forward or backward
                autoChassis.frontLeft.setPower(fwdbackdirection*power);
                autoChassis.frontRight.setPower(fwdbackdirection*power);
                autoChassis.backLeft.setPower(fwdbackdirection*power);
                autoChassis.backRight.setPower(fwdbackdirection*power);
            } else {
                autoChassis.frontLeft.setPower(strafeDirection* power);
                autoChassis.frontRight.setPower(-strafeDirection* power);
                autoChassis.backLeft.setPower(-strafeDirection* power);
                autoChassis.backRight.setPower(strafeDirection* power);
            }
        }
        autoChassis.setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //#TOBECHECKED TO AVOID JERK
        autoChassis.frontLeft.setPower(0.0);
        autoChassis.frontRight.setPower(0.0);
        autoChassis.backLeft.setPower(0.0);
        autoChassis.backRight.setPower(0.0);
    }

    /**
     * Method to move wrist to Initial position
     */
    public void moveWristToClose() {
        while (!isStopRequested()){
            autoIntake.wrist.setPosition(autoIntake.wristPosition[0]);//close position = 0.2
        }
        autoIntake.wristCurrentPosition = 0;
    }

    /**
     * Method to set Arm brake mode to ON when Zero (0.0) power is applied.
     * To be used when arm is above groundlevel
     * setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
     */
    public void turnArmBrakeModeOn(){
        autoArm.armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        autoArm.armMotor.setPower(0.0);
    }

    /**
     * Method to move chassis based on computed vector inputs for a set max_stop_distance
     * Till team color is identified below Chassis
     * To be used in Autonomous mode for moving by distance or turning by angle
     * Uses PID loop in motors to ensure motion without errors
     * @param max_stop_distance Max distance to stop
     * @param strafeDirection 0 for forward or backward, 1 for right, -1 for left
     * @param power to run motors at
     */
    public void runTill_ChassisRightColorSensorIsBlue(double max_stop_distance, double strafeDirection, double power){
        autoChassis.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
        autoChassis.resetChassis();

        //Max Total Rotations of wheel = distance / circumference of wheel
        double targetRotations = max_stop_distance/(2*Math.PI*autoChassis.wheelRadius);

        //set fwdbackdirection, +ve for forward and negative for backward
        double fwdbackdirection = max_stop_distance /Math.abs(max_stop_distance);

        while (!isStopRequested() && !autoChassis.rightColorSensorIsBlue() &&
                (Math.abs(autoChassis.backLeft.getCurrentPosition()) < Math.abs(autoChassis.ChassisMotorEncoderCount * targetRotations))) {
            if(strafeDirection == 0) {
                //Go forward or backward
                autoChassis.frontLeft.setPower(fwdbackdirection*power);
                autoChassis.frontRight.setPower(fwdbackdirection*power);
                autoChassis.backLeft.setPower(fwdbackdirection*power);
                autoChassis.backRight.setPower(fwdbackdirection*power);
            } else {
                autoChassis.frontLeft.setPower(strafeDirection* power);
                autoChassis.frontRight.setPower(-strafeDirection* power);
                autoChassis.backLeft.setPower(-strafeDirection* power);
                autoChassis.backRight.setPower(strafeDirection* power);
            }
        }
        autoChassis.setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //#TOBECHECKED TO AVOID JERK
        autoChassis.frontLeft.setPower(0.0);
        autoChassis.frontRight.setPower(0.0);
        autoChassis.backLeft.setPower(0.0);
        autoChassis.backRight.setPower(0.0);
    }

    /**
     * Method to move chassis based on computed vector inputs for a set max_stop_distance
     * Till team color is identified below Chassis
     * To be used in Autonomous mode for moving by distance or turning by angle
     * Uses PID loop in motors to ensure motion without errors
     * @param max_stop_distance Max distance to stop
     * @param strafeDirection 0 for forward or backward, 1 for right, -1 for left
     * @param power to run motors at
     */
    public void runTill_ChassisLeftColorSensorIsRed(double max_stop_distance, double strafeDirection, double power){
        autoChassis.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
        autoChassis.resetChassis();

        //Max Total Rotations of wheel = distance / circumference of wheel
        double targetRotations = max_stop_distance/(2*Math.PI*autoChassis.wheelRadius);

        //set fwdbackdirection, +ve for forward and negative for backward
        double fwdbackdirection = max_stop_distance /Math.abs(max_stop_distance);

        while (!isStopRequested() && !autoChassis.leftColorSensorIsRed() &&
                (Math.abs(autoChassis.backLeft.getCurrentPosition()) < Math.abs(autoChassis.ChassisMotorEncoderCount * targetRotations))) {
            if(strafeDirection == 0) {
                //Go forward or backward
                autoChassis.frontLeft.setPower(fwdbackdirection*power);
                autoChassis.frontRight.setPower(fwdbackdirection*power);
                autoChassis.backLeft.setPower(fwdbackdirection*power);
                autoChassis.backRight.setPower(fwdbackdirection*power);
            } else {
                autoChassis.frontLeft.setPower(strafeDirection* power);
                autoChassis.frontRight.setPower(-strafeDirection* power);
                autoChassis.backLeft.setPower(-strafeDirection* power);
                autoChassis.backRight.setPower(strafeDirection* power);
            }
        }
        autoChassis.setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //#TOBECHECKED TO AVOID JERK
        autoChassis.frontLeft.setPower(0.0);
        autoChassis.frontRight.setPower(0.0);
        autoChassis.backLeft.setPower(0.0);
        autoChassis.backRight.setPower(0.0);
    }

    /**
     * Method to move chassis based on computed vector inputs for a set max_stop_distance
     * Till team color is identified below Chassis
     * To be used in Autonomous mode for moving by distance or turning by angle
     * Uses PID loop in motors to ensure motion without errors
     * @param max_stop_distance Max distance to stop
     * @param strafeDirection 0 for forward or backward, 1 for right, -1 for left
     * @param power to run motors at
     */
    public void runTill_ChassisLeftColorSensorIsBlue(double max_stop_distance, double strafeDirection, double power){
        autoChassis.setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
        autoChassis.resetChassis();

        //Max Total Rotations of wheel = distance / circumference of wheel
        double targetRotations = max_stop_distance/(2*Math.PI*autoChassis.wheelRadius);

        //set fwdbackdirection, +ve for forward and negative for backward
        double fwdbackdirection = max_stop_distance /Math.abs(max_stop_distance);

        while (!isStopRequested() && !autoChassis.leftColorSensorIsBlue() &&
                (Math.abs(autoChassis.backLeft.getCurrentPosition()) < Math.abs(autoChassis.ChassisMotorEncoderCount * targetRotations))) {
            if(strafeDirection == 0) {
                //Go forward or backward
                autoChassis.frontLeft.setPower(fwdbackdirection*power);
                autoChassis.frontRight.setPower(fwdbackdirection*power);
                autoChassis.backLeft.setPower(fwdbackdirection*power);
                autoChassis.backRight.setPower(fwdbackdirection*power);
            } else {
                autoChassis.frontLeft.setPower(strafeDirection* power);
                autoChassis.frontRight.setPower(-strafeDirection* power);
                autoChassis.backLeft.setPower(-strafeDirection* power);
                autoChassis.backRight.setPower(strafeDirection* power);
            }
        }
        autoChassis.setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //#TOBECHECKED TO AVOID JERK
        autoChassis.frontLeft.setPower(0.0);
        autoChassis.frontRight.setPower(0.0);
        autoChassis.backLeft.setPower(0.0);
        autoChassis.backRight.setPower(0.0);
    }
}
