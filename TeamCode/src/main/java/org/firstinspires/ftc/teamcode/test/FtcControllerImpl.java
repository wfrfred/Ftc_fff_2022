package org.firstinspires.ftc.teamcode.test;

import com.fff.controller.AbstractFtcController;
import com.fff.controller.GamepadListener;
import com.fff.controller.MyGamepad;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.motionModule.MotionModule;
import org.firstinspires.ftc.teamcode.motionModule.MotionModuleWithHelm;

public class FtcControllerImpl extends AbstractFtcController {
    private final MotionModule motionModule;
    private volatile boolean isMotionModuleManual = false;

    private GamepadListener gamepadListener = new GamepadListener() {
        @Override
        public void pressA() {

        }

        @Override
        public void pressB() {

        }

        @Override
        public void pressX() {
            isMotionModuleManual = false;
        }

        @Override
        public void pressY() {
            isMotionModuleManual = true;
        }

        @Override
        public void pressR1() {
            motionModule.stop();
        }

        @Override
        public void pressL1() {

        }

        @Override
        public void pressThumbR() {

        }

        @Override
        public void pressThumbL() {

        }
    };

    public FtcControllerImpl(Gamepad gamepad1, HardwareMap hardwareMap) {
        super(gamepad1);
        motionModule = new MotionModuleWithHelm(hardwareMap);
        super.setGamepadListener(gamepadListener);
        motionModuleManualThread.start();
    }

    Thread motionModuleManualThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (isMotionModuleManual) {
                    //targetTotalAngle += gamepad1.getMotion(MyGamepad.Code.RIGHT_STICK_X);
                    synchronized (motionModule) {
                        motionModule.moveGamepad(
                                gamepad1.getMotion(MyGamepad.Code.LEFT_STICK_Y),
                                //motionModule.turn(targetTotalAngle),
                                -gamepad1.getMotion(MyGamepad.Code.RIGHT_STICK_X),
                                -gamepad1.getMotion(MyGamepad.Code.LEFT_STICK_X),
                                1 - gamepad1.getMotion(MyGamepad.Code.RIGHT_TRIGGER)
                        );
                    }
                }
            }
        }
    }, "MotionModule");

}
