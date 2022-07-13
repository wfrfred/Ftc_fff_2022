package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.cvModule.CvModule;
import org.firstinspires.ftc.teamcode.cvModule.CvModuleImpl;

@TeleOp(name = "TeamFFFCameraTestOpMode", group = "FFF")
public class CameraTest extends LinearOpMode {
    private CvModule cvModule;

    @Override
    public void runOpMode() throws InterruptedException {
        cvModule = new CvModuleImpl(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            System.out.println(cvModule.getMode());
        }
    }

}
