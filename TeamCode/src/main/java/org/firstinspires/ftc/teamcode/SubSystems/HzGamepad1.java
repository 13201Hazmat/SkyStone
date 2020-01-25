package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Defenition of the HzGamepad Class <BR>
 *
 * HzGamepad consists of system provided gamepad(s) and adds functionality to the selection 
 * made on gamepads <BR>
 * 
 * For Hazmat Skystone, only one Gamepad is used (gamepad1) <BR>
 *
 * The controls are as follows: <BR>
 *      <emsp>Left Stick for pan motion (gamepad1.left_stick_x and gamepad1.left_stick_y) <BR>
 *      <emsp>Right Stick for turn motion (only uses the x direction : gamepad1.right_stick_y) <BR>
 *      <emsp>Right Bumper for increasing level of arm by 1 block (gamepad1.right_bumper) <BR>
 *      <emsp>Left Bumper for decreasing level of arm by 1 block (gamepad1.left_bumper) <BR>
 *      <emsp>Right Trigger for increasing speed to double (gamepad1.right_trigger) <BR>
 *      <emsp>Button A to close / pull back linear actuator Intake-Grp (gamepad1.a) <BR>
 *      <emsp>Button Y to open / extend linear actuator Intake-Grip (gamepad1.y) <BR>
 *      <emsp>Button X to move Arm to place block at level (gamepad1.x) <BR>
 *      <emsp>Button B to move Arm to lift after block placement (gamepad1.b) <BR>
 *      <emsp>Button Dpad_up to open wrist (gamepad1.dpad_up) <BR>
 *      <emsp>Button Dpad_down to close wrist (gamepad1.dpad_down) <BR>
 */

public class HzGamepad1 {

    //Create gamepad object reference to connect to gamepad1
    public Gamepad gpGamepad1;

    /**
     * Constructor for HzGamepad1 class that extends gamepad.
     * Assign the gamepad1 given in OpMode to the gamepad used here.
     *
     * @param gamepadPassedfromOpMode from OpMode. In the case of Hazmat Skystone, this is gamepad1
     */
    public HzGamepad1(Gamepad gamepadPassedfromOpMode) {
        gpGamepad1 = gamepadPassedfromOpMode;
    }

    /**
     * Methods to get the value of gamepad Left stick X for Pan motion X direction.
     * This is the method to apply any directional modifiers to match to the X plane of robot.
     * No modifier needed for Hazmat Skystone Robot.
     *
     * @return gpGamepad1.left_stick_x
     */
    public double getLeftStickX() {
        return gpGamepad1.left_stick_x;
    }

    /**
     * Methods to get the value of gamepad Left stick Y for Pan motion Y direction.
     * This is the method to apply any directional modifiers to match to the Y plane of robot.
     * For Hazmat Skystone Robot, Y direction needs to be inverted.
     *
     * @return gpGamepad1.left_stick_y * (-1)
     */
    public double getLeftStickY() {
        return gpGamepad1.left_stick_y * (-1);
    }

    /**
     * Methods to get the value of gamepad Right stick X to keep turning.
     * This is the method to apply any directional modifiers to match to the turn direction robot.
     * No modifier needed for Hazmat Skystone Robot.
     *
     * @return gpGamepad1.right_stick_x
     */
    public double getRightStickX() {
        return gpGamepad1.right_stick_x;
    }

    /**
     * Methods to get the value of gamepad Right Trigger for turbo mode (max speed).
     * This is the method to apply any modifiers to match to action of turbo mode for each driver preference.
     * For Hazmat Skystone Right Trigger pressed means turbo mode on.
     *
     * @return gpGamepad1.right_trigger
     */
    public double getRightTrigger() {
        return gpGamepad1.right_trigger;
    }

    /**
     * Method to convert linear map from gamepad1 stick input to a cubic map
     *
     * @param stickInput input value of button stick vector
     * @return Cube of the stick input reduced to 25% speed
     */
    public double limitStick(double stickInput) {
        return (stickInput * stickInput * stickInput * 0.25);
    }

    /**
     * Method to implement turbo speed mode - from reduced speed of 25% of cubic factor to
     * 100% speed, but controlled by acceleration of the force of pressing the Right Tigger.
     *
     * @param stickInput input value of button stick vector
     * @return modified value of button stick vector
     */
    public double turboMode(double stickInput) {
        double acceleration_factor;
        double rightTriggerValue = getRightTrigger();
        acceleration_factor = 1.0 + 3.0 * rightTriggerValue;
        return limitStick(stickInput) * acceleration_factor;
    }


    /**
     * Method to convert Gamepad commands to actions on Robot
     */
    public void runSubsystemByGamepadInput(Chassis gpChassis/*, Arm gpArm, Intake gpIntake*/) {

        /*Chassis actions :
        Convert Left Stick and Right Stick motion to power, target Angle and turn for Chassis
         */
        double leftStickX = turboMode(getLeftStickX());
        double leftStickY = turboMode(getLeftStickY());
        double rightStickX = turboMode(getRightStickX());
        double power = Math.hypot(leftStickX, leftStickY);
        double targetAngle = Math.atan2(leftStickY, leftStickX);
        double turn = rightStickX;
        gpChassis.runByGamepadCommand(targetAngle, turn, power);

    }
}

