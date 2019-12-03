package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcode.SubSystems.*;


/**
 * TeleOpMode for testing Autonomous mode functionality
 * Write test methods as separate functions and call in OpModeisActive().
 * Comment out the ones not run at the time, but dont delete the test methods written.
 * Autonomous commands :
 *  * @ChassisAutoMethods : runDistance()
 *  * @ChassisAutoMethods : runTill_frontleftBumperSensor_Pressed(max stop distance)
 *  * @ChassisAutoMethods : runTill_chassisLocationSensorIdentifiesLine(color)
 *  * @ChassisAutoMethods : turnRobotByAngle()
 *  * @ChassisAutoMethods : resetColorSensorEnabled()
 *  * @ChassisAutoMethods : leftColorSensorIsRed()
 *  * @ChassisAutoMethods : rightColorSensorIsBlue()
 *  * @ChassisAutoMethods : leftColorSensorIsBlue()
 *  * @ChassisAutoMethods : rightColorSensorIsRed()
 *  * @ChassisAutoMethods : frontleftBumperSensorIsPressed()
 *  * @ArmMethods : moveArm_groundLevel()
 *  * @ArmMethods : moveArm_blockLevelUp()
 *  * @ArmMethods : moveArm_blockLevelDown()
 *  * @ArmMethods : moveArmToPlaceBlockAtLevel()
 *  * @ArmMethods : moveArmToLiftAfterBlockPlacement()
 *  * @ArmMethods : runArmToLevel()
 *  * @ArmAutoMethods : moveArm_detectSkystoneLevel()
 *  * @ArmAutoMethods : moveArm_aboveFoundationLevel(()
 *  * @ArmAutoMethods : moveArm_onFoundationLevel()
 *  * @IntakeMethods : moveWristToInitialPosition()
 *  * @IntakeMethods : moveWristToHorizontalPosition()
 *  * @IntakeMethods : moveWristToVerticalPosition()
 *  * @IntakeAutoMethods : openGrip()
 *  * @IntakeAutoMethods : closeGrip()
 *  * @IntakeAutoMethods : detectSkystoneColorSensorIsYellow()
 *  * @IntakeAutoMethods : detectSkystoneColorSensorIsBlack()
 */

@Autonomous(name = "HzAutoTest", group = "Autonomous")
public class AutonomousTests extends LinearOpMode{

    public boolean HzDEBUG_FLAG = true;

    HzGamepad1 hzGamepad1;
    Chassis hzChassis;
    Arm hzArm;
    Intake hzIntake;

    //Timer for timing Autonomous mode
    ElapsedTime AutonomousTimeOut = new ElapsedTime();

    @Override
    public void runOpMode() {
        //Instantiate Subsystems : Chassis, Arm, Intake, Gamepad1
        hzChassis = new Chassis(hardwareMap);
        hzArm = new Arm(hardwareMap);
        hzIntake = new Intake(hardwareMap);
        hzGamepad1 = new HzGamepad1(gamepad1);

        telemetry.setAutoClear(false);
        telemetry.addData("Init Autonomous Tests", "v:1.0");

        //Wait for pressing Run on controller
        waitForStart();
        AutonomousTimeOut.reset();

        //Write test methods as separate functions and call in OpModeisActive().
        //Comment out the ones not run at the time

        //Run Robot till opMode is Active or 30 seconds
        while (opModeIsActive() && AutonomousTimeOut.milliseconds()<30000) {
            //hzGamepad1.runSubsystemByGamepadInput(hzChassis, hzArm, hzIntake);
            hzArm.moveArm_aboveFoundationLevel();
            hzChassis.runDistance(20, 0, 0, 0.25);
            //hzChassis.runDistance(0, 0, Math.PI/4, 0.25);

            //hzChassis.runRotations(1, 0.25);
            //hzChassis.runStraightDistanceByRotations(12, 0.25);

            if(HzDEBUG_FLAG) printDebugMessages();
            telemetry.update();
            idle();

        }

    }
    /**
     * Method to add debug messages. Update as telemetry.addData.
     * Use public attributes or methods if needs to be called here.
     */
    public void printDebugMessages(){
        telemetry.setAutoClear(true);
        telemetry.addData("HzDEBUG_FLAG is : ", HzDEBUG_FLAG);

        telemetry.addData("hzChassis.backLeft.isBusy : ", hzChassis.backLeft.isBusy());
        telemetry.addData("hzChassis.backLeft.getTargetPosition : ", hzChassis.backLeft.getTargetPosition());
        telemetry.addData("hzChassis.backLeft.getCurrentPosition : ", hzChassis.backLeft.getCurrentPosition());
        telemetry.addData("hzChassis.backRight.getCurrentPosition : ", hzChassis.backRight.getCurrentPosition());
        telemetry.addData("hzChassis.frontLeft.getCurrentPosition : ", hzChassis.frontLeft.getCurrentPosition());
        telemetry.addData("hzChassis.frontRight.getCurrentPosition : ", hzChassis.frontRight.getCurrentPosition());
        telemetry.addData("hzChassis.backLeft.getMode : ", hzChassis.backLeft.getMode());
        //telemetry.addData("hzChassis.frontLeft.currentLevel : ", hzArm.currentLevel);
        //telemetry.addData("Arm.blockLevel[hzArm.currentLevel] : ", hzArm.blockLevel[hzArm.currentLevel]);

    }
}