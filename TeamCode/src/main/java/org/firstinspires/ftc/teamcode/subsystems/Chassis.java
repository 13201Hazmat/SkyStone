package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Chassis extends Subsystem {
    /**
     * Fields
     */
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public Double wheelRadius;
    public Double robotRadius;

    /**
     * Initializes a new Chassis
     * 
     * @param hardwareMap the HardwareMap that is passed in through the op mode
     */
    public Chassis(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.dcMotor.get("front_left_drive");
        frontRight = hardwareMap.dcMotor.get("front_right_drive");
        backLeft = hardwareMap.dcMotor.get("back_left_drive");
        backRight = hardwareMap.dcMotor.get("back_right_drive");
        initMotors(new DcMotor[] { frontLeft, frontRight, backLeft, backRight });
        initChassis();
    }

    /**
     * Helper method for the chassis initialization
     */
    public void initChassis() {
        reset();
        setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Should be run every loop to set the chassis motor powers
     * 
     * @param targetAngle the angle for the chassis to strafe at; 0 is straight
     *                    forward
     * @param turn        the angle for the chassis to rotate at; 0 is no rotation
     * @param power       the power for all of the wheels to be set at
     */
    public void run(double targetAngle, double turn, double power) {
        final double turnAngle = targetAngle - Math.PI / 4;
        frontLeft.setPower(power * Math.cos(turnAngle) + turn);
        frontRight.setPower(power * Math.sin(turnAngle) - turn);
        backLeft.setPower(power * Math.sin(turnAngle) + turn);
        backRight.setPower(power * Math.cos(turnAngle) - turn);
    }

    /**
     * **Does not work as expected************************************************
     * Runs the chassis a specific distance at a strafe angle and will turn the
     * chassis a given angle
     * 
     * @param distance    the total distance(radius) to move the chassis
     * @param targetAngle the angle the chassis will strafe at
     * @param turn        the angle the chassis will turn at
     * @param power       the power all of the motors will be set to
     */
    public void runDistance(double distance, double targetAngle, double turn, double power) {
        final double turnAngle = targetAngle - Math.PI / 4;
        final double wheelDistance = (Math.sqrt(2) / wheelRadius) * distance;
        final double robotTurn = robotRadius * turn;
        frontLeft.setTargetPosition((int) (wheelDistance * Math.cos(turnAngle) + robotTurn));
        frontRight.setTargetPosition((int) (wheelDistance * Math.sin(turnAngle) - robotTurn));
        backLeft.setTargetPosition((int) (wheelDistance * Math.sin(turnAngle) + robotTurn));
        backRight.setTargetPosition((int) (wheelDistance * Math.cos(turnAngle) - robotTurn));
        setMotorPowers(power);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Will run all of the motors on the chassis a given number of rotations at a
     * given power
     * 
     * @param rotations the number of rotations all of the motors will run till (can
     *                  be positive or negative)
     * @param power     the power all of the motors will run at (can be positive or
     *                  negative)
     */
    public void runRotations(double rotations, double power) {
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        reset();
        setMotorPowers(power);
        while (Math.abs(backLeft.getCurrentPosition()) < Math.abs(1440 * rotations))
            ;
        setMotorPowers(0);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
