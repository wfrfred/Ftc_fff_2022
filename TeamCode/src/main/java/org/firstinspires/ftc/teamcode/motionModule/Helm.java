package org.firstinspires.ftc.teamcode.motionModule;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * @author wfrfred
 * @time 2021-10-23 20:40
 */
public class Helm {
    private final DcMotor motor;
    private final Servo servo;
    private Vector velocity;
    private Direction direction;

    public enum Direction {
        Forward, Backward
    }

    public Helm(HardwareMap hardwareMap, String name, Direction direction) {
        motor = hardwareMap.dcMotor.get(name + "Motor");
        servo = hardwareMap.servo.get((name + "Servo"));
        servo.setPosition(0.5);
        velocity = new Vector(0, 0);
        this.direction = direction;
        if (direction == Direction.Forward) servo.setDirection(Servo.Direction.REVERSE);
    }

    public void setVelocity(Vector velocity) {
        double theta1 = this.velocity.getTheta() - velocity.getTheta();
        double theta2 = this.velocity.getTheta() - (velocity.getTheta()
                + velocity.getTheta() >= 0 ? -180 : 180);
        this.velocity = velocity;
        if (Math.abs(theta1) > Math.abs(theta2)) {
            this.velocity.setPositivity(-1);
        }
        motor.setPower(velocity.getR());
        servo.setPosition((velocity.getTheta() + 180) / 360);
    }

    public Direction getDirection() {
        return direction;
    }

    public Vector getVelocity() {
        return velocity;
    }

    /*
    public void turn(double angle){
        velocity.turn(angle);
    }
     */

}
