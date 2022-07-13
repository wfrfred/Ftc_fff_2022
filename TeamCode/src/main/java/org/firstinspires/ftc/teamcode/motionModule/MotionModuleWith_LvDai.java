package org.firstinspires.ftc.teamcode.motionModule;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.motionModule.MotionModule;

public class MotionModuleWith_LvDai implements MotionModule {
    private final DcMotor[] motor = new DcMotor[2];

    public MotionModuleWith_LvDai(HardwareMap hardwareMap){
        motor[0] = hardwareMap.dcMotor.get("r");
        motor[1] = hardwareMap.dcMotor.get("l");
    }

    @Override
    public void move(double speed) {
        motor[0].setPower(speed);
        motor[1].setPower(speed);
    }

    @Override
    public void rotate(double speed) {
        motor[0].setPower(speed);
        motor[1].setPower(-speed);
    }

    @Override
    public void stop() {
        motor[0].setPower(0);
        motor[1].setPower(0);
    }

    @Override
    public void moveGamepad(double move, double turn, double k) {
        double right = move+0.6*turn;
        double left = move-0.6*turn;
        if (Math.abs(right) > 1 || Math.abs(left) > 1){
            double maxnum = Math.max(Math.abs(right),Math.abs(left));
            left /= maxnum;
            right /= maxnum;
        }
        motor[0].setPower(-right*k);
        motor[1].setPower(left*k);
    }
}
