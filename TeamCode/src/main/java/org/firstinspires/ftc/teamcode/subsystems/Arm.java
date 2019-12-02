package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm extends Subsystem {
    /**
     * Fields
     */
    public DcMotor arm;
    // These angles should be set in the op mode, they should be raw encoder counts
    // range:(-∞,∞)
    int[] levelAngles = { 0, -40, -85, -136, -189, -245, -311 };

    /**
     * Initializes a new Arm
     * 
     * @param hardwareMap the HardwareMap that is passed in through the op mode
     */
    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.dcMotor.get("test");
        initMotors(new DcMotor[] { arm });
        initArm();
    }

    /**
     * Helper method for the arm initialization
     */
    public void initArm() {
        reset();
        setZeroBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Sets the level angles. Should be passed in before using the arm
     * 
     * @param levelAngles the angles the robot arm will be set to
     */
    public void setLevelAngles(int[] levelAngles) {
        this.levelAngles = levelAngles;
    }

    /**
     * Returns the motor being used by this class
     * 
     * @return returns the arm motor being used by this class
     */
    public DcMotor getArm() {
        return arm;
    }

    /**
     * Sets the arm to a certain position based upon the levelAngles array
     * 
     * @param level the index of the levelAngles array the arm should be set to
     */
    public void setArmPosition(int level) {
        arm.setTargetPosition(levelAngles[level]);
    }

    /**
     * (Unfinished) Sets the error needed for the arm PID loop
     * 
     * @param error the error that the arm will move until it is in this range
     */
    public void setArmCheck(double error) {

    }

    /**
     * Should be run every loop**
     * 
     * @param level the level the arm is to be set at
     */
    public void run(int level) {
        setArmPosition(level);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (level != 0) {
            arm.setPower(1);
        } else {
            arm.setPower(0);
            arm.reset();
        }
    }
}
