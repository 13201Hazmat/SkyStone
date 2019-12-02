package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class Subsystem {
    /**
     * Fields
     */
    DcMotor[] motors;
    Servo[] servos;

    /**
     * Sets the motors
     * 
     * @param motors all of the motors in the subsystem
     */
    public void initMotors(DcMotor[] motors) {
        this.motors = motors;
    }

    /**
     * Sets the servos
     * 
     * @param servos all of the servos in the subsystem
     */
    public void initServos(Servo[] servos) {
        this.servos = servos;
    }

    /**
     * Sets the same power for all of the motors in the subsystem
     * 
     * @param power the power all of the motors are to be set at
     */
    public void setMotorPowers(double power) {
        for (DcMotor motor : motors) {
            motor.setPower(power);
        }
    }

    /**
     * Gets all of the motors current positions
     * 
     * @return returns an int array list of all of the positions of the motors
     *         indexed the same as the motors
     */
    public int[] getMotorPositions() {
        int[] positions = new int[motors.length];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = motors[i].getCurrentPosition();
        }
        return positions;
    }

    /**
     * Sets the zero power behavior for all for all of the motors
     * 
     * @param zeroPowerBehavior an enum from the DcMotor class of type
     *                          ZeroPowerBehavior that is to be set on all motors
     */
    public void setZeroBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        for (DcMotor motor : motors) {
            motor.setZeroPowerBehavior(zeroPowerBehavior);
        }
    }

    /**
     * Reverses the direction of given motors
     * 
     * @param reverseMotors all of the motors that are to be reversed
     */
    public void reverseMotors(DcMotor[] reverseMotors) {
        for (DcMotor reverseMotor : reverseMotors) {
            reverseMotor.setDirection(DcMotor.Direction.REVERSE);
        }
    }

    /**
     * Sets all of the servos in the subsystem to the same given position
     * 
     * @param position the positions to set all of the servos to
     */
    public void setServoPositions(double position) {
        for (Servo servo : servos) {
            servo.setPosition(position);
        }
    }

    /**
     * "Resets" all of the motors. Switch all of the motors run mode to
     * STOP_AND_REEST_ENCODER and then switch its run mode back to what is was
     * before
     */
    public void reset() {
        for (DcMotor motor : motors) {
            DcMotor.RunMode runMode = motor.getMode();
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(runMode);
        }
    }

    /**
     * Sets the run mode for all motors
     * 
     * @param runMode the run mode that all motors will be set to
     */
    public void setMode(DcMotor.RunMode runMode) {
        for (DcMotor motor : motors) {
            motor.setMode(runMode);
        }
    }

    /**
     * Returns an array of the motors error
     * 
     * @return returns an int array of the motor error index the same way as the
     *         motors
     */
    public int[] getMotorError() {
        int[] errors = new int[motors.length];
        for (int i = 0; i < errors.length; i++) {
            DcMotor motor = motors[i];
            errors[i] = Math.abs(motor.getTargetPosition() - motor.getCurrentPosition());
        }
        return errors;
    }

    /**
     * Gets the average motor error of the motors in the subsystem
     * 
     * @return returns the average motor error as a double
     */
    public double getAverageMotorError() {
        int[] motorErrors = getMotorError();
        double sum = 0;
        for (int motorError : motorErrors) {
            sum += motorError;
        }
        return sum / motorErrors.length;
    }

    /**
     * A method to check if the subsystem's motors are within a specified error
     * 
     * @param targetError the error to check if the subsystem's motors are within
     * @return will return true if the average motor error is within the target
     *         error
     */
    public boolean motorErrorCheck(double targetError) {
        return targetError > getAverageMotorError();
    }

}
