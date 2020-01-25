package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.*;

/**
 * TeleOpMode for Team Hazmat<BR>
 * Includes autoplacement routince for automatically placing block on tower
 */
@TeleOp(name = "HazmatTeleOpMode", group = "Teleop")
public class HazmatTeleOpMode extends LinearOpMode{

    public boolean HzDEBUG_FLAG = true;

    int hzRobotNum; //Set to 1 for main robot, 2 for second robot

    HzGamepad1 hzGamepad1;
    Chassis hzChassis;

    @Override
    public void runOpMode() {

        //Instantiate Subsystems : Chassis, Arm, Intake, Gamepad1
        hzChassis = new Chassis(hardwareMap);
        hzGamepad1 = new HzGamepad1(gamepad1);

        telemetry.addData("Hazmat TeleOp Mode", "v:1.0");

        //Wait for pressing plan on controller
        waitForStart();

        //Initialize on press of play
        hzChassis.initChassis();

        //Run Robot based on Gamepad1 inputs
        while (opModeIsActive()) {
            //Run per Gamepad input
            hzGamepad1.runSubsystemByGamepadInput(hzChassis);

            if(HzDEBUG_FLAG) {
                printDebugMessages();
                telemetry.update();
            }
        }
    }

    /**
     * Method to add debug messages. Update as telemetry.addData.
     * Use public attributes or methods if needs to be called here.
     */
    public void printDebugMessages(){
        telemetry.setAutoClear(true);
        telemetry.addData("HzDEBUG_FLAG is : ", HzDEBUG_FLAG);
        telemetry.addData("backRightDrive.getCurrentPosition()", hzChassis.backRight.getCurrentPosition());
        telemetry.addData("backRightDrive.getCurrentPosition()", hzChassis.backLeft.getCurrentPosition());
        telemetry.addData("backRightDrive.getCurrentPosition()", hzChassis.frontRight.getCurrentPosition());
        telemetry.addData("backRightDrive.getCurrentPosition()", hzChassis.frontLeft.getCurrentPosition());
    }

}


