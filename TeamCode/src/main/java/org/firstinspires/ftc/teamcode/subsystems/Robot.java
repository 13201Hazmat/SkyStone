package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.autoRes.commands.CommandList;

public class Robot {
    /**
     * Fields
     */
    public int level = 0;
    public Arm arm;
    public Hook hook;
    public Chassis chassis;
    public Intake intake;
    public CommandList commands;

    /**
     * Initializes a new generic robot that can be customized through setter methods
     * @param hardwareMap the hardware map given through the op mode
     */
    public Robot(HardwareMap hardwareMap) {
        // constructors
        arm = new Arm(hardwareMap);
        hook = new Hook(hardwareMap);
        chassis = new Chassis(hardwareMap);
        intake = new Intake(hardwareMap);
    }

    /**
     *
     * @param controller
     */
    public void run(Controller controller) {
        // Chassis get
        final double leftStickX = controller.sensitiveStick(controller.getLeftStickX());
        final double leftStickY = controller.sensitiveStick(controller.getLeftStickY());
        final double rightStickX = controller.sensitiveStick(controller.getRightStickX());
        final double power = Math.hypot(leftStickX, leftStickY);
        final double targetAngle = Math.atan2(leftStickY, leftStickX);
        final double turn = rightStickX;
        // Arm get
        // Put in all run methods
        setLevel(controller);
        arm.run(level);
        chassis.run(targetAngle, turn, power);
        intake.run(controller.getBPress());
        if (controller.getA()) {
            autoPlace(controller);
        }
        // hook.run();
        // intake.run();
    }

    public void test(double rot, double power) {
        chassis.runRotations(rot, power);
    }

    public void setLevel(Controller controller) {
        if (controller.getRightBumper()) {
            level++;
            if (level == arm.levelAngles.length) {
                level = arm.levelAngles.length - 1;
            }
            while (controller.getRightBumper())
                ;
        }
        if (controller.getLeftBumper()) {
            level--;
            if (level < 0) {
                level = 0;
            }
            while (controller.getLeftBumper())
                ;
        }
    }

    public void autoPlace(Controller controller) {
        chassis.runRotations(.25, -.25);
        arm.run(level);
        while (!controller.getAPress())
            ;
        chassis.runRotations(.3, .25);
        while (!controller.getAPress())
            ;
        arm.run(level);
        while (!controller.getAPress())
            ;
        intake.setMainToHighPosition();
        while (!controller.getAPress())
            ;
        arm.run(arm.levelAngles.length - 1);
        while (!controller.getAPress())
            ;
        chassis.runRotations(.4, -.25);
    }
}
