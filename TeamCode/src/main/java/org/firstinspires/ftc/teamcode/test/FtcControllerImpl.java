package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.controller.AbstractFtcController;
import org.firstinspires.ftc.teamcode.controller.GamepadListener;
import org.firstinspires.ftc.teamcode.controller.MyGamepad;
import org.firstinspires.ftc.teamcode.functionModule.FunctionModule;
import org.firstinspires.ftc.teamcode.functionModule.FunctionModuleImpl;
import org.firstinspires.ftc.teamcode.motionModule.MotionModule;
import org.firstinspires.ftc.teamcode.motionModule.MotionModuleWith_LvDai;


public class FtcControllerImpl extends AbstractFtcController {
    private boolean isMotionModuleManual;
    private int floor = 0;

    private boolean isRotating = false;
    private boolean isShooting = false;
    private boolean isInvolving = false;
    private double k = 1;

    private final FunctionModule functionModule;
    private final MotionModule motionModule;

    private final GamepadListener gamepadListener = new GamepadListener() {
        @Override
        public void pressA() {
            isRotating = !isRotating;
            functionModule.rotating(isRotating);
        }

        @Override
        public void pressB() {
            if (k==1)k=0.5;
            else k=1;

        }

        @Override
        public void pressX() {
            if (floor == 0) {
                if(isShooting && !(isInvolving)){
                    isShooting = false;
                    functionModule.shooting(false);
                }
                isInvolving = !isInvolving;
                functionModule.involving(isInvolving);
            }
        }
        @Override
        public void pressY() {
            isShooting = !isShooting;
            functionModule.shooting(isShooting);
        }

        @Override
        public void pressR1() {
            isShooting = false;
            functionModule.shooting(false);
            isInvolving = false;
            functionModule.involving(false);
            if (floor == 2) return;
            floor += 1;
            functionModule.elevating(floor);
        }

        @Override
        public void pressL1() {
            isShooting = false;
            functionModule.shooting(false);
            isInvolving = false;
            functionModule.involving(false);
            if (floor == 0) return;
            floor -= 1;
            functionModule.elevating(floor);
        }

        @Override
        public void pressThumbR() {
            motionModule.stop();
            functionModule.involving(false);
            functionModule.elevating(0);
            functionModule.rotating(false);
            functionModule.shooting(false);
        }

        @Override
        public void pressThumbL() {
            motionModule.movePosition(0.5,-100);
        }
    };

    public FtcControllerImpl(Gamepad gamepad1, HardwareMap hardwareMap) {
        super(gamepad1);
        motionModule = new MotionModuleWith_LvDai(hardwareMap);
        super.setGamepadListener(gamepadListener);
        motionModuleManualThread.start();
        functionModule = new FunctionModuleImpl(hardwareMap);
        isMotionModuleManual = true;
        super.startGamepadListening();
    }

    @Override
    public void stop() {
        functionModule.involving(false);
        functionModule.elevating(0);
        functionModule.rotating(false);
        functionModule.shooting(false);
        motionModuleManualThread.interrupt();
        setIsListening(false);
    }

    Thread motionModuleManualThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (isMotionModuleManual) {
                    //targetTotalAngle += gamepad1.getMotion(MyGamepad.Code.RIGHT_STICK_X);
                        motionModule.moveGamepad(
                                gamepad1.getMotion(MyGamepad.Code.LEFT_STICK_Y),
                                //motionModule.turn(targetTotalAngle),
                                gamepad1.getMotion(MyGamepad.Code.LEFT_STICK_X),
                                k
                        );
//                        System.out.println(gamepad1.getMotion(MyGamepad.Code.RIGHT_TRIGGER)+"qwerty");
                }
            }
        }
    }, "MotionModule");





}
