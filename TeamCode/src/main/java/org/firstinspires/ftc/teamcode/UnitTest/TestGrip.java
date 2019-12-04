package org.firstinspires.ftc.teamcode.UnitTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.SubSystems.Chassis;
import org.firstinspires.ftc.teamcode.SubSystems.HzGamepad1NoWrist;

@TeleOp(name = "TestGrip", group = "TeleOp")
public class TestGrip extends LinearOpMode {

    public boolean HzDEBUG_FLAG = true;

    public Servo left_grip;
    public Servo right_grip;

    final static double GRIP_HOME = 0.0;
    final static double GRIP_MIN_RANGE = 0;
    final static double GRIP_MAX_RANGE = 1;
    final static double GRIP_OPEN_LEFT = 0.24;
    final static double GRIP_OPEN_RIGHT = 0.74;
    final static double GRIP_SPEED=0.01;

    final static double GRIP_CLOSE_LEFT = 0.73;
    final static double GRIP_CLOSE_RIGHT= 0.23;


    HzGamepad1NoWrist hzGamepad1NoWrist;



    @Override
    public void runOpMode() throws InterruptedException {

        hzGamepad1NoWrist = new HzGamepad1NoWrist(gamepad1);

        left_grip = hardwareMap.servo.get("left_grip");
        right_grip = hardwareMap.servo.get("right_grip");
        //grip1.setDirection(Servo.Direction.FORWARD);
        //grip2.setDirection(Servo.Direction.REVERSE);

        //left_grip.scaleRange(GRIP_MIN_RANGE,GRIP_MAX_RANGE);
        //right_grip.scaleRange(GRIP_MIN_RANGE,GRIP_MAX_RANGE);




        waitForStart();

        while (opModeIsActive()) {


            //when A is pressed, grip is moved down
            if (hzGamepad1NoWrist.getButtonAPress()){

                left_grip.setPosition(GRIP_CLOSE_LEFT);
                right_grip.setPosition(GRIP_CLOSE_RIGHT);
            }
            //If Y is pressed, grip is moved up
            if (hzGamepad1NoWrist.getButtonYPress()){

                left_grip.setPosition(GRIP_OPEN_LEFT);
                right_grip.setPosition(GRIP_OPEN_RIGHT);
            }
            if (hzGamepad1NoWrist.getButtonBPress()){

                initGrip();
            }

            if (hzGamepad1NoWrist.getButtonXPress()){

                moveGrip();
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
        telemetry.addData("GRIP1 position", left_grip.getPosition());
        telemetry.addData("GRIP2 position", right_grip.getPosition());
    }

    public void initGrip(){

        left_grip.setPosition(0);
        right_grip.setPosition(1);

    }

    public void moveGrip(){

        left_grip.setPosition(0.53);
        right_grip.setPosition(0.43);
    }
}
