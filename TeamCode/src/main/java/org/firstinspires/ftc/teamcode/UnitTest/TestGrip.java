package org.firstinspires.ftc.teamcode.UnitTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.SubSystems.Chassis;
import org.firstinspires.ftc.teamcode.SubSystems.HzGamepad1NoWrist;

@TeleOp(name = "TestGrip", group = "TeleOp")
public class TestGrip extends LinearOpMode {

    public boolean HzDEBUG_FLAG = true;

    public Servo grip1;
    public Servo grip2;

    final static double GRIP_HOME = 0.0;
    final static double GRIP_MIN_RANGE = 0.25;
    final static double GRIP_MAX_RANGE = 0.75;
    final static double GRIP_SPEED=0.01;

    HzGamepad1NoWrist hzGamepad1NoWrist;

    @Override
    public void runOpMode() throws InterruptedException {

        hzGamepad1NoWrist = new HzGamepad1NoWrist(gamepad1);

        grip1 = hardwareMap.servo.get("grip1");
        grip2 = hardwareMap.servo.get("grip2");
        //grip1.setDirection(Servo.Direction.FORWARD);
        //grip2.setDirection(Servo.Direction.REVERSE);

        grip1.scaleRange(GRIP_MIN_RANGE,GRIP_MAX_RANGE);
        grip2.scaleRange(GRIP_MIN_RANGE,GRIP_MAX_RANGE);


        waitForStart();

        while (opModeIsActive()) {

            if (hzGamepad1NoWrist.getButtonAPress()){
                //grip1.setDirection(Servo.Direction.FORWARD);
                //grip2.setDirection(Servo.Direction.REVERSE);
                grip1.setPosition(GRIP_MIN_RANGE);
                grip2.setPosition(GRIP_MAX_RANGE);
            }
            //If Y is pressed, open grip
            if (hzGamepad1NoWrist.getButtonYPress()){
                //grip1.setDirection(Servo.Direction.FORWARD);
                //grip2.setDirection(Servo.Direction.REVERSE);
                grip1.setPosition(GRIP_MAX_RANGE);
                grip2.setPosition(GRIP_MIN_RANGE);
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
        telemetry.addData("GRIP1 position", grip1.getPosition());
        telemetry.addData("GRIP2 position", grip2.getPosition());
    }
}
