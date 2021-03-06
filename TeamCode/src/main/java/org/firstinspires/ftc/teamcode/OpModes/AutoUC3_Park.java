package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SubSystems.Arm;
import org.firstinspires.ftc.teamcode.SubSystems.Chassis;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

/**
 * Autonomous Mode Usecase 3<BR>
 * Description : Start on wall in Building Zone or Loading zone, Park near wall or near neutral skybridge<BR>
 *
 * Code asssumes Blue Alliance, and at end can park either on wall side or near the neutral skybridge<BR>
 * Steps:<BR>
 * <ol>
 * <li>Robot starts between A2, A3 if on loading zone facing outwards or on A4, A5 in building zone
 * <li>On start, robot opens wrist to front position
 * <li>(Alternate usecase) : Move forward to align to parking location near neatural sybridge, else stay
 * to park along the wall.
 * <li>Move right by distance or till Chassis light sensor does not detect Blue line to be under blue skybridge
 * </ol>
 * Uses startInBuildingZone variable as true for building zone, false for loading zone<BR>
 * Uses playingAlliance variable to select as 1 for Blue, -1 for Red Alliance<BR>
 * Uses parkingPlaceNearSkyBridge variable false for near wall, true for near NeutralSkybridge<BR>
 */

public class AutoUC3_Park {

    boolean parkedStatus = false; // Will be true once robot is parked

    public boolean AutoUC3_Park_Method(
            LinearOpMode callingOpMode,
            int playingAlliance,
            boolean parkingPlaceNearSkyBridge,
            boolean startInBuildingZone,
            Chassis autoUCChassis,
            Arm autoUCArm,
            Intake autoUCIntake) {

        //Robot starts between A4, A5 such that it can slight in front of skybridge neutral zone floor

        //On start, Lift arm and robot opens wrist to front position
        //initArm() and initIntake() should do this on class initialization

        //Lift Arm to AboveFoundation level
        autoUCArm.moveArm_aboveFoundationLevel();
        callingOpMode.sleep(500);

        //Move Arm to ground Level
        autoUCArm.turnArmBrakeModeOn();
        callingOpMode.sleep(500);

        /***************************************/
        //Delay to move only by end of autonomous mode
        callingOpMode.sleep(15000);
        /***************************************/


        //Park near wall
        //Move back by distance or till Chassis light sensor does not detect Blue line to be under blue skybridge
        if (startInBuildingZone) {
            if (playingAlliance == 1) {
                //Optional : Move to park near skybridge Neutral
                if (parkingPlaceNearSkyBridge){
                    autoUCChassis.runFwdBackLeftRight(27, playingAlliance,0.25, callingOpMode);
                }
                //Blue Alliance
                autoUCChassis.runTill_ChassisRightColorSensorIsBlue(-30, 0, 0.2, callingOpMode);
            } else {
                //Optional : Move to park near skybridge Neutral
                if (parkingPlaceNearSkyBridge){
                    autoUCChassis.runFwdBackLeftRight(27, playingAlliance,0.25, callingOpMode);
                }
                //Red Alliance
                autoUCChassis.runTill_ChassisRightColorSensorIsRed(-30, 0, 0.2, callingOpMode);
            }
        } else {
            if (playingAlliance == 1) {
                //Optional : Move to park near skybridge Neutral
                if (parkingPlaceNearSkyBridge){
                    autoUCChassis.runFwdBackLeftRight(27, -playingAlliance,0.25, callingOpMode); // Was 22
                }
                //Blue Alliance
                //runTill_ChassisLeftColorSensorIsBlue(30, -1, 0.2);
                autoUCChassis.runTill_ChassisRightColorSensorIsBlue(-30, 0, 0.2, callingOpMode);
            } else {
                //Optional : Move to park near skybridge Neutral
                if (parkingPlaceNearSkyBridge){
                    autoUCChassis.runFwdBackLeftRight(27, -playingAlliance,0.25, callingOpMode);
                }
                //Red Alliance
                autoUCChassis.runTill_ChassisRightColorSensorIsRed(-30, 0, 0.2, callingOpMode);
            }
        }
        //Reached Parking position
        return parkedStatus = true;
    }
}