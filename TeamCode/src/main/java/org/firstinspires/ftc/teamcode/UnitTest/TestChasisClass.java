package org.firstinspires.ftc.teamcode.UnitTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.Chassis;
import org.firstinspires.ftc.teamcode.SubSystems.HzGamepad1NoWrist;

@TeleOp(name = "TestChassisClass",group = "UnitTesting")
public class TestChasisClass extends LinearOpMode {

    public boolean HzDEBUG_FLAG = true;

    HzGamepad1NoWrist hzGamepad1NoWrist;
    Chassis hzChassis;

    @Override
    public void runOpMode() throws InterruptedException {

        hzChassis = new Chassis(hardwareMap);

        while (opModeIsActive()) {

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
