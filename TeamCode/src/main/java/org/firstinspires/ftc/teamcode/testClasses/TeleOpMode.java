package org.firstinspires.ftc.teamcode.testClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Controller;
import org.firstinspires.ftc.teamcode.subsystems.Robot;

@TeleOp(name = "TeleOpMode", group = "Teleop")
public class TeleOpMode extends LinearOpMode {
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap);
        telemetry.addData("Init", "v:1.0");
        waitForStart();
        while (opModeIsActive()) {
            Controller controller = new Controller(gamepad1);
            robot.run(controller);
            telemetry.update();
        }
    }
}  
