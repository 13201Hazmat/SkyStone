package org.firstinspires.ftc.teamcode.UnitTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.SubSystems.Chassis;
import org.firstinspires.ftc.teamcode.SubSystems.HzGamepad1NoWrist;

@TeleOp(name = "TestGrip", group = "TeleOp")
public class TestGrip extends LinearOpMode {

    public boolean HzDEBUG_FLAG = true;

    public Servo grip;
    public Servo grip2;

    HzGamepad1NoWrist hzGamepad1NoWrist;



    @Override
    public void runOpMode() throws InterruptedException {

        hzGamepad1NoWrist = new HzGamepad1NoWrist(gamepad1);

        grip = hardwareMap.servo.get("grip");
        grip2 = hardwareMap.servo.get("grip2");

        waitForStart();

        while (opModeIsActive()) {

            if (hzGamepad1NoWrist.getButtonAPress()){
                grip2.setPosition(0.75);
                grip.setPosition(0.25);
            }
            //If Y is pressed, open grip
            if (hzGamepad1NoWrist.getButtonYPress()){
                grip2.setPosition(0.25);
                grip.setPosition(0.75);
            }

            if(HzDEBUG_FLAG) printDebugMessages();
            telemetry.update();
        }
    }

    /**
     * Method to add debug messages. Update as telemetry.addData.
     * Use public attributes or methods if needs to be called here.
     */
    public void printDebugMessages() {
        telemetry.setAutoClear(true);
        telemetry.addData("HzDEBUG_FLAG is : ", HzDEBUG_FLAG);
    }
}
