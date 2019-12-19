package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

public class DetectSkyStoneVuforia extends LinearOpMode {

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

        //gett the id of the Android view used to display camera output
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId","id", hardwareMap.appContext.getPackageName());

        //parameters used to initialize the vuforia engine
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //vuforia License key

        parameters.vuforiaLicenseKey = " ASdsTxf/////AAABmZw/PPj4rUoWufL2OaojY6cC2kk82CBuA/bbNlY1m0SGcNwtn6eH9YKfAHORCMx/wgnXsU+viSXyw2bm9OrkS5SEPloAVjW6vlEqP/QK+/cpFmpF+IQYUgrrfkpnWZUZZYw57a9Bwt20CPnCXMv2YSFM9Q0l7DRH98GWT88KbNaieVZBn0EcaMfzuovWCr42F86cAOntuwSIMttj+NorzwstiYHm//sqq3mt+eC2U8P1rw7kVLpIa+/W/s42hLIbolPZP3FDo2lCxsEPzTQvWfvz852zvF7xayb+QJSQKIxxSMm8HIA1/Ei/noXpoKLv4gkUNrrX8/qbFBmO4fNEfOrcn9oJqpzPB5xdrqyCA8oz ";

        

    }
}
