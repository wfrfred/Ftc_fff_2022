package org.firstinspires.ftc.teamcode.functionModule;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.time.Instant;
import java.util.Date;

/**
 * 功能模块接口实现
 *
 * @author wfrfred
 * @date 2022/07/11
 */
public class FunctionModuleImpl implements FunctionModule {
    /**
     * 抬升装置、卷与射击挡板
     */
    private final DcMotor elevator;
    private final DcMotor rotator;
    private final DcMotor involution;
    private final Servo shooter;

    private final int[] TARGET_POSITION;

    public FunctionModuleImpl(HardwareMap hardwareMap) {
        elevator = hardwareMap.dcMotor.get("elevator");
        involution = hardwareMap.dcMotor.get("involution");
        rotator = hardwareMap.dcMotor.get("rotator");
        shooter = hardwareMap.servo.get("shooter");
        TARGET_POSITION = new int[]{0, 740, 1420};
        elevator.setTargetPosition(0);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(0.5);
    }

    @Override
    public void involving(boolean start) {
        if (start) involution.setPower(-1);
        else involution.setPower(0);
    }

    @Override
    public void elevating(int floor) {
        elevator.setTargetPosition(TARGET_POSITION[floor]);
    }

    @Override
    public void shooting(boolean start) {
        if (start) shooter.setPosition(0.63);
        else shooter.setPosition(0.21);
    }

    @Override
    public void rotating(boolean start) {
        if (start) {
            Date before = new Date();
            Date now = new Date();

            double t=0;
            while (t<=1){
                now = new Date();
                t = ((double)(now.getTime()-before.getTime()))/1000;
                rotator.setPower(t*(2-t));
            }
            while (t<=1.6){
                now = new Date();
                t = ((double)(now.getTime()-before.getTime()))/1000;
            }
            rotator.setPower(0);
        }
        else rotator.setPower(0);
    }
}
