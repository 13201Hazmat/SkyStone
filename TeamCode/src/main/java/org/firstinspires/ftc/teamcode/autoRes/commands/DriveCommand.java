package org.firstinspires.ftc.teamcode.autoRes.commands;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Chassis;

public class DriveCommand implements ICommand {
    /**
     * Fields
     */
    Chassis chassis;
    double targetAngle;
    double turn;
    int distance;
    double power;
    int error;
    boolean hasRun;

    /**
     * Initializes a new drive command, all fields should be set here
     * 
     * @param chassis     the chassis to be driven
     * @param targetAngle the angle relative to the chassis current angle that the
     *                    chassis will strafe to
     * @param turn        the angle relative to the chassis current angle that the
     *                    chassis will turn to
     * @param distance    the distance (radius) the chassis will travel, not
     *                    including any turn distance
     * @param power       the power the wheel motors will be set to
     * @param error       the error in encoder counts that the chassis will run
     *                    until it gets within the error range
     */
    public DriveCommand(Chassis chassis, double targetAngle, double turn, int distance, double power, double error) {
        this.chassis = chassis;
        this.targetAngle = targetAngle;
        this.turn = turn;
        this.distance = distance;
        this.power = power;
        this.error = error;
        hasRun = false;
    }

    /**
     * Returns if the command has been run or not
     * 
     * @return will return true if the command has been run, false in not
     */
    public boolean hasRun() {
        return hasRun;
    }

    /**
     * Will start running the drive command, should only be run once after
     * initialization
     */
    public void run() {
        chassis.runDistance(distance, targetAngle, turn, power);
        hasRun = true;
    }

    /**
     * Returns if the chassis has finished moving the distance within its error
     * 
     * @return returns true if the command is done or false if not
     */
    public boolean isDone() {
        return chassis.motorErrorCheck(error);
    }

}
