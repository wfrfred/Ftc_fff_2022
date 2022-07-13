package org.firstinspires.ftc.teamcode.controller;


import com.qualcomm.robotcore.hardware.Gamepad;

public abstract class AbstractFtcController implements FtcController {
    protected final MyGamepad gamepad1;
    protected GamepadListener gamepadListener;

    public AbstractFtcController(Gamepad gamepad1) {
        this.gamepad1 = new MyGamepad(gamepad1);
    }

    public void setGamepadListener(GamepadListener gamepadListener)  throws NullPointerException {
        this.gamepadListener = gamepadListener;
        if (this.gamepadListener != null) {
            this.gamepad1.registerListener(this.gamepadListener);
            this.gamepad1.getThread().start();
        } else {
            throw new NullPointerException();
        }
    }

    public void startGamepadListening(){
        gamepad1.setIsListening(true);
    }

    public void setIsListening(boolean isListening) {
        this.gamepad1.setIsListening(isListening);
    }

    public boolean getIsListening() {
        return this.gamepad1.getIsListening();
    }
}

