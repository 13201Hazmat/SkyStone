package org.firstinspires.ftc.teamcode.UnitTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.SubSystems.Arm;
import org.firstinspires.ftc.teamcode.SubSystems.HzGamepad1NoWrist;

@TeleOp(name = "ArmTest", group = "UnitTest")
public class TestArm extends LinearOpMode {

    HzGamepad1NoWrist hzGamepad1NoWrist;
    Arm robotArm;

    DcMotor armMotor;

    int MAX_ENCODER_COUNT_ARMMOTOR = 690;
    int groundLevel = 0;
    int encoderIncrrement = 150;
    int currentLevel = 0;
    int levelUp;
    int levelDown;
    @Override
    public void runOpMode() throws InterruptedException {

        hzGamepad1NoWrist = new HzGamepad1NoWrist(gamepad1);
        robotArm = new Arm(hardwareMap);

        armMotor = hardwareMap.dcMotor.get("arm");
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);



        waitForStart();

        while(opModeIsActive()){

            if(hzGamepad1NoWrist.getRightBumperPress()){
                armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                if (currentLevel < MAX_ENCODER_COUNT_ARMMOTOR) {
                    levelUp = currentLevel + encoderIncrrement;
                    armMotor.setTargetPosition(currentLevel);
                    currentLevel = levelUp;
                    runArmToLevel();
                }

            }
            if(hzGamepad1NoWrist.getLeftBumperPress()){


                if(currentLevel>encoderIncrrement){
                    levelDown = currentLevel-encoderIncrrement;
                    armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    armMotor.setTargetPosition(levelDown);
                    currentLevel = levelDown;
                    runArmToLevel();
                }
                else {
                    armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                    moveArm_groundLevel();
                    currentLevel = 0;
                    armMotor.setPower(0.0);
                }
            }

        }
    }

    public void runArmToLevel() {
        //armMotor.setTargetPosition(blockLevel[level]);;
        //ArmMotionTimeOut.reset();
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //Turn Motors on
        armMotor.setPower(0.75);
        //Move motor till target Position is achieved, or timeout in 3000 milliseconds
        //while (armMotor.isBusy() && (ArmMotionTimeOut.milliseconds()<3000)) {
        //Wait
        //}
        //Turn Motor to BRAKE
        //armMotor.setPower(0.0);
    }

    public void moveArm_groundLevel(){
        armMotor.setTargetPosition(groundLevel);

        runArmToLevel();
    }
}
