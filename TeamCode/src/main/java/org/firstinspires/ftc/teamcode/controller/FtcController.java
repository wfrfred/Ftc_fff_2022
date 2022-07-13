package org.firstinspires.ftc.teamcode.controller;

public interface FtcController {
    void startGamepadListening() throws NullPointerException;

    void setIsListening(boolean var1);

    boolean getIsListening();

    void stop();
}
