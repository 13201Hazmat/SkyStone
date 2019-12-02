package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends Subsystem {
    /**
     * Fields
     */
    Servo wrist;
    Servo main;
    double wristHighPosition;
    double wristLowPosition;
    double mainHighPosition = .75;
    double mainLowPosition = .25;

    /**
     * Initializes a new Intake
     * 
     * @param hardwareMap the HardwareMap that is passed in through the op mode
     */
    public Intake(HardwareMap hardwareMap) {
        wrist = hardwareMap.servo.get("wrist");
        main = hardwareMap.servo.get("arm");
        initServos(new Servo[] { wrist, main });
        initIntake();
    }

    /**
     * Helper method for the chassis initialization
     */
    public void initIntake() {
        setServoPositions(0);
    }

    /**
     * Sets the wrist to a given position
     * 
     * @param position the position of the wrist to be set to
     */
    public void setWrist(double position) {
        wrist.setPosition(position);
    }

    /**
     * Sets the main intake to a given position
     * 
     * @param position the position of the main intake to be set to
     */
    public void setMain(double position) {
        main.setPosition(position);
    }

    /**
     * Sets the wrist to its hight position
     */
    public void setWristToHighPosition() {
        wrist.setPosition(wristHighPosition);
    }

    /**
     * Sets the wrist to its low position
     */
    public void setWristToLowPosition() {
        wrist.setPosition(wristLowPosition);
    }

    /**
     * Sets the main intake to its hight position
     */
    public void setMainToHighPosition() {
        main.setPosition(mainHighPosition);
    }

    /**
     * Sets the main intake to its low position
     */
    public void setMainToLowPosition() {
        main.setPosition(mainLowPosition);
    }

    /**
     * Can be run every loop, will change the main position if switchMain is true
     * 
     * @param switchMain if true: the main will switch from low position to high
     *                   position, or high position to low position. if false:
     *                   nothing will happen
     */
    public void run(boolean switchMain) {
        if (switchMain) {
            if (main.getPosition() == mainHighPosition) {
                setMainToLowPosition();
            } else {
                setMainToHighPosition();
            }
        }
    }
}
