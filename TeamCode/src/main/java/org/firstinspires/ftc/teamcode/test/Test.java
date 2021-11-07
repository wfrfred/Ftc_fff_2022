package org.firstinspires.ftc.teamcode.test;

import com.fff.controller.FtcController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motionModule.MotionModule;

@TeleOp(name = "TeamFFFTestOpMode", group = "FFF")
public class Test extends OpMode {
    private FtcController controller;
    private MotionModule motionModule;

    @Override
    public void init() {
        controller = new FtcControllerImpl(gamepad1, hardwareMap);
        controller.startGamepadListening();
    }

    @Override
    public void loop() {
        controller.setIsListening(true);
    }
}
