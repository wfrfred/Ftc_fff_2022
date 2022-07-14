package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.cvModule.CvModule;
import org.firstinspires.ftc.teamcode.cvModule.CvModuleImpl;
import org.firstinspires.ftc.teamcode.functionModule.FunctionModule;
import org.firstinspires.ftc.teamcode.functionModule.FunctionModuleImpl;
import org.firstinspires.ftc.teamcode.motionModule.MotionModule;
import org.firstinspires.ftc.teamcode.motionModule.MotionModuleWith_LvDai;

@Autonomous(name = "Auto", group = "FFF")
public class AutoOpMode extends LinearOpMode {
    private CvModule cvModule;
    private FunctionModule functionModule;
    private MotionModule motionModule;

    @Override
    public void runOpMode() throws InterruptedException {
        cvModule = new CvModuleImpl(hardwareMap);
        functionModule = new FunctionModuleImpl(hardwareMap);
        motionModule = new MotionModuleWith_LvDai(hardwareMap);
        int[] mode = new int[]{0,0,0};
        for(int i = 0;i < 60;i++){
            mode[cvModule.getMode()]++;
        }
        int max = mode[0];
        for(int i:mode){
            if (i >= max) max = i;
        }
        waitForStart();

        motionModule.rotate(1);
        sleep(1000);
        motionModule.stop();
        motionModule.move(1);
        sleep(2000);
        functionModule.elevating(max);
        sleep(1000);
        functionModule.shooting(true);
        sleep(1000);
        functionModule.shooting(false);
        functionModule.elevating(0);

        motionModule.rotate(1);
        sleep(2000);
        motionModule.stop();
        motionModule.move(1);
        sleep(5000);
        motionModule.stop();
    }
}
