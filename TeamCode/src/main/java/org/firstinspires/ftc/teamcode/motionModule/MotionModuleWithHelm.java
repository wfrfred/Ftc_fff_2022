package org.firstinspires.ftc.teamcode.motionModule;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotionModuleWithHelm implements MotionModule {
    Helm[] helms = new Helm[4];
    double[] rotateAngles = {45, -45, 135, -135};

    public MotionModuleWithHelm(HardwareMap hardwareMap) {
        helms[0] = new Helm(hardwareMap, "l1", Helm.Direction.Forward);
        helms[1] = new Helm(hardwareMap, "l2", Helm.Direction.Backward);
        helms[2] = new Helm(hardwareMap, "r1", Helm.Direction.Forward);
        helms[3] = new Helm(hardwareMap, "r2", Helm.Direction.Backward);
    }

    @Override
    public void move(double speed, double angle) {
        Vector vector = new Vector(speed, angle);
        for (Helm helm : helms) {
            helm.setVelocity(vector);
        }
    }

    @Override
    public void rotate(double speed) {
        for (int i = 0; i < 4; i++) {
            helms[i].setVelocity(new Vector(speed, rotateAngles[i]));
        }
    }

    @Override
    public void rotateAndMove(double rotateSpeed, double moveDirection, double speed) {

    }

    @Override
    public void stop() {
        move(0, 0);
    }

    @Override
    public void moveGamepad(double move, double turn, double fun, double k) {
        Vector shift = Vector.add(new Vector(move * k, 0), new Vector(fun * k, 90));
        Vector[] velocity = new Vector[4];
        for (int i = 0; i < 4; i++) {
            velocity[i] = Vector.add(shift, new Vector(turn * k, rotateAngles[i]));
        }
        velocity = Vector.normalize(velocity);
        for (int i = 0; i < 4; i++) {
            helms[i].setVelocity(velocity[i]);
        }
    }
}
