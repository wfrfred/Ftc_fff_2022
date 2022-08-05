package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
    private boolean last_function_is_move = true;
    private  int last_move=0;
    private  int last_rotate=0;
    private final FunctionModule functionModule;
    private final MotionModule motionModule;
    private final Telemetry telemetry;

    private final GamepadListener gamepadListener = new GamepadListener() {
        @Override
        public void pressA() {
            isRotating = !isRotating;
            functionModule.rotating(true);
//            motionModule.movePosition(0.5,-100);
//            if(last_function_is_move){
//                last_move = last_move-100;
//            }
//            else{
//                last_function_is_move = true;
//                last_move = -100;
//                telemetry.addData("rotate:",last_rotate);
//                telemetry.addData("Turn to move!",0);
//            }
        }

        @Override
        public void pressB() {
            if (k==1)k=0.5;
            else k=1;
//            motionModule.rotatePosition(0.5,-100);
//            if(!last_function_is_move){
//                last_rotate = last_rotate-100;
//            }
//            else{
//                last_function_is_move = false;
//                last_rotate = -100;
//                telemetry.addData("move:",last_move);
//                telemetry.addData("Turn to rotate!",0);
//            }

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
//            motionModule.rotatePosition(0.5,100);
//            if(!last_function_is_move){
//                last_rotate = last_rotate+100;
//            }
//            else{
//                last_function_is_move = false;
//                last_rotate = 100;
//                telemetry.addData("move:",last_move);
//                telemetry.addData("Turn to rotate!",0);
//            }
        }
        @Override
        public void pressY() {
            isShooting = !isShooting;
            functionModule.shooting(isShooting);
//            motionModule.movePosition(0.5,100);
//            if(last_function_is_move){
//                last_move = last_move+100;
//            }
//            else{
//                last_function_is_move = true;
//                last_move = 100;
//                telemetry.addData("rotate:",last_rotate);
//                telemetry.addData("Turn to move!",0);
//            }
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
//            functionModule.elevate(10);
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
//            functionModule.elevate(-10);
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

    public FtcControllerImpl(Gamepad gamepad1, HardwareMap hardwareMap, Telemetry telemetry) {
        super(gamepad1);
        motionModule = new MotionModuleWith_LvDai(hardwareMap);
        super.setGamepadListener(gamepadListener);
        motionModuleManualThread.start();
        functionModule = new FunctionModuleImpl(hardwareMap);
        isMotionModuleManual = true;
        this.telemetry=telemetry;
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
//                        telemetry.addData(gamepad1.getMotion(MyGamepad.Code.RIGHT_TRIGGER)+"qwerty");
                }
            }
        }
    }, "MotionModule");





}
