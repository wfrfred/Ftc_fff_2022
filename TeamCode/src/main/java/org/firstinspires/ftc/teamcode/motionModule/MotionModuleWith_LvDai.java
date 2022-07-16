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
        motor[0].setPower(-speed);
        motor[1].setPower(speed);
    }

    @Override
    public void movePosition(double speed,int length){
        int p1=motor[0].getCurrentPosition()+length;
        int p2=motor[1].getCurrentPosition()-length;
        motor[0].setTargetPosition(p1);
        motor[1].setTargetPosition(p2);
        motor[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor[0].setPower(-speed);
        motor[1].setPower(speed);
    }

    @Override
    public void rotate(double speed) {
        motor[0].setPower(-speed);
        motor[1].setPower(-speed);
    }

    @Override
    public  void rotatePosition(double speed,int angle){
        int p1=motor[0].getCurrentPosition()+angle;
        int p2=motor[1].getCurrentPosition()+angle;
        motor[0].setTargetPosition(p1);
        motor[1].setTargetPosition(p2);
        motor[0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor[1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor[0].setPower(-speed);
        motor[1].setPower(speed);}

    @Override
    public void stop() {
        motor[0].setPower(0);
        motor[1].setPower(0);
    }

    @Override
    public void moveGamepad(double move, double turn, double k) {
        double right = move+0.9*turn;
        double left = move-0.9*turn;
        if (Math.abs(right) > 1 || Math.abs(left) > 1){
            double maxnum = Math.max(Math.abs(right),Math.abs(left));
            left /= maxnum;
            right /= maxnum;
        }
        motor[0].setPower(-right*k);
        motor[1].setPower(left*k);
    }
}
