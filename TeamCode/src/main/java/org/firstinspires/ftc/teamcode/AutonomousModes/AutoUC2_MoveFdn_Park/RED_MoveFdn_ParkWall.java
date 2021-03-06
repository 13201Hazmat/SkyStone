package org.firstinspires.ftc.teamcode.AutonomousModes.AutoUC2_MoveFdn_Park;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.OpModes.AutoUC2_MoveFdn_Park;
import org.firstinspires.ftc.teamcode.SubSystems.Arm;
import org.firstinspires.ftc.teamcode.SubSystems.Chassis;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

/**
 * Autonomous Mode Usecase 2<BR>
 * Description : Start on wall in Building Zone, Grip Foundation with arm, and position it into Building Site.
 *              Using Centrifugal force to hold foundation on its father end and rotate to end position.<BR>
 * <BR>
 * Uses playingAlliance variable to select as 1 for Blue, -1 for Red Alliance<BR>
 * Uses parkingPlaceNearSkyBridge variable false for near wall, true for near NeutralSkybridge<BR>
 */

@Autonomous(name = "RED-MoveFdn-ParkWall", group = "MoveFdn")
public class RED_MoveFdn_ParkWall extends LinearOpMode {

    Intake autoIntake;
    Arm autoArm;
    Chassis autoChassis;

    public int playingAlliance = -1; //1 for Blue, -1 for Red
    public boolean parkingPlaceNearSkyBridge = false;//false for near wall, true for near NeutralSkybridge

    boolean parked = false; // Will be true once robot is parked

    AutoUC2_MoveFdn_Park AutoMode;

    /**
     * Template runOpMode code. Only change Usecase function and call here.
     * Refer to Autonomous Command Syntax to put right values
     * <p>
     * All Usecases written assuming playingAlliance = 1 meaning Blue, -1 for Red.
     *
     * throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {

        AutoMode = new AutoUC2_MoveFdn_Park();
        autoIntake = new Intake(hardwareMap);
        autoArm = new Arm(hardwareMap);
        autoChassis = new Chassis(hardwareMap);

        //Robot starts on A2
        waitForStart();

        //Initialize on press of play
        autoChassis.initChassis();
        autoArm.initArm();
        autoIntake.initIntake();

        while (opModeIsActive()&& !isStopRequested() && !parked) {
           parked = AutoMode.AutoUC2_MoveFdn_Park_Method(
                    this,
                    playingAlliance,
                    parkingPlaceNearSkyBridge,
                    autoChassis,
                    autoArm,
                    autoIntake);
        }
    }
}