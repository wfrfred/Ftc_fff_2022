package org.firstinspires.ftc.teamcode.controller;

import android.util.Pair;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyGamepad {
    private GamepadListener gamepadListener;
    private Gamepad gamepad;
    private Hashtable<org.firstinspires.ftc.teamcode.controller.MyGamepad.Code, Pair<Boolean, Long>> pressTime;
    private AtomicBoolean isListening = new AtomicBoolean(false);
    private Thread listener = new Thread(() -> {
        while(true) {
            if (MyGamepad.this.isListening.get()) {
                if ((Boolean)((Pair) MyGamepad.this.pressTime.get(Code.A)).first != MyGamepad.this.gamepad.a) {
                    MyGamepad.this.debounce(Code.A);
                }

                if ((Boolean)((Pair) MyGamepad.this.pressTime.get(Code.B)).first != MyGamepad.this.gamepad.b) {
                    MyGamepad.this.debounce(Code.B);
                }

                if ((Boolean)((Pair) MyGamepad.this.pressTime.get(Code.X)).first != MyGamepad.this.gamepad.x) {
                    MyGamepad.this.debounce(Code.X);
                }

                if ((Boolean)((Pair) MyGamepad.this.pressTime.get(Code.Y)).first != MyGamepad.this.gamepad.y) {
                    MyGamepad.this.debounce(Code.Y);
                }

                if ((Boolean)((Pair) MyGamepad.this.pressTime.get(Code.RIGHT_BUMPER)).first != MyGamepad.this.gamepad.right_bumper) {
                    MyGamepad.this.debounce(Code.RIGHT_BUMPER);
                }

                if ((Boolean)((Pair) MyGamepad.this.pressTime.get(Code.LEFT_BUMPER)).first != MyGamepad.this.gamepad.left_bumper) {
                    MyGamepad.this.debounce(Code.LEFT_BUMPER);
                }

                if ((Boolean)((Pair) MyGamepad.this.pressTime.get(Code.RIGHT_STICK_BUTTON)).first != MyGamepad.this.gamepad.right_stick_button) {
                    MyGamepad.this.debounce(Code.RIGHT_STICK_BUTTON);
                }

                if ((Boolean)((Pair) MyGamepad.this.pressTime.get(Code.LEFT_STICK_BUTTON)).first != MyGamepad.this.gamepad.left_stick_button) {
                    MyGamepad.this.debounce(Code.LEFT_STICK_BUTTON);
                }
            }
        }
    });

    MyGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
        final long time = System.currentTimeMillis();
        this.pressTime = new Hashtable<org.firstinspires.ftc.teamcode.controller.MyGamepad.Code, Pair<Boolean, Long>>() {
            {
                this.put(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code.A, new Pair(false, time));
                this.put(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code.B, new Pair(false, time));
                this.put(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code.X, new Pair(false, time));
                this.put(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code.Y, new Pair(false, time));
                this.put(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code.RIGHT_BUMPER, new Pair(false, time));
                this.put(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code.LEFT_BUMPER, new Pair(false, time));
                this.put(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code.RIGHT_STICK_BUTTON, new Pair(false, time));
                this.put(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code.LEFT_STICK_BUTTON, new Pair(false, time));
            }
        };
    }

    public void registerListener(GamepadListener gamepadListener) {
        this.gamepadListener = gamepadListener;
    }

    public Thread getThread() {
        return this.listener;
    }

    public void setIsListening(boolean isListening) {
        this.isListening.set(isListening);
    }

    public boolean getIsListening() {
        return this.isListening.get();
    }

    public float getMotion(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code code) {
        switch(code) {
            case RIGHT_STICK_X:
                return this.gamepad.right_stick_x;
            case RIGHT_STICK_Y:
                return this.gamepad.right_stick_y;
            case LEFT_STICK_X:
                return this.gamepad.left_stick_x;
            case LEFT_STICK_Y:
                return this.gamepad.left_stick_y;
            case LEFT_TRIGGER:
                return this.gamepad.left_trigger;
            case RIGHT_TRIGGER:
                return this.gamepad.right_trigger;
            default:
                return 0.0F;
        }
    }

    private long getTime(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code keyCode) {
        return (Long)((Pair)this.pressTime.get(keyCode)).second;
    }

    private boolean getValue(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code keyCode) {
        return (Boolean)((Pair)this.pressTime.get(keyCode)).first;
    }

    private void setKey(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code keyCode, Pair<Boolean, Long> newKey) {
        this.pressTime.put(keyCode, newKey);
    }

    private void debounce(org.firstinspires.ftc.teamcode.controller.MyGamepad.Code keyCode) {
        long time = System.currentTimeMillis();
        long DEBOUNCE_TIME = 50L;
        if (time - Math.abs(this.getTime(keyCode)) >= 50L) {
            if (!this.getValue(keyCode)) {
                this.setKey(keyCode, new Pair(true, time));
            } else {
                this.setKey(keyCode, new Pair(false, time));
                switch(keyCode) {
                    case A:
                        this.gamepadListener.pressA();
                        break;
                    case B:
                        this.gamepadListener.pressB();
                        break;
                    case X:
                        this.gamepadListener.pressX();
                        break;
                    case Y:
                        this.gamepadListener.pressY();
                        break;
                    case RIGHT_BUMPER:
                        this.gamepadListener.pressR1();
                        break;
                    case LEFT_BUMPER:
                        this.gamepadListener.pressL1();
                        break;
                    case RIGHT_STICK_BUTTON:
                        this.gamepadListener.pressThumbR();
                        break;
                    case LEFT_STICK_BUTTON:
                        this.gamepadListener.pressThumbL();
                }

            }
        }
    }

    public static enum Code {
        A,
        B,
        X,
        Y,
        RIGHT_BUMPER,
        LEFT_BUMPER,
        RIGHT_STICK_BUTTON,
        LEFT_STICK_BUTTON,
        RIGHT_STICK_X,
        RIGHT_STICK_Y,
        LEFT_STICK_X,
        LEFT_STICK_Y,
        RIGHT_TRIGGER,
        LEFT_TRIGGER;

        Code() {
        }
    }
}