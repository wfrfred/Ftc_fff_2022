package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.teamcode.controller.FtcController;

@TeleOp(name = "TeamFFFTestOpMode", group = "FFF")
public class Test extends OpMode {
    private FtcController ftcController;

    @Override
    public void init() {
        ftcController = new FtcControllerImpl(gamepad1,hardwareMap,telemetry);
    }

    @Override
    public void loop() {
        //ftcController.setIsListening(true);
    }

    @Override
    public void stop() {
        ftcController.stop();
    }
}
