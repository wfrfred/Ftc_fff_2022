package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.functionModule.FunctionModule;
import org.firstinspires.ftc.teamcode.functionModule.FunctionModuleImpl;
import org.firstinspires.ftc.teamcode.motionModule.MotionModule;
import org.firstinspires.ftc.teamcode.motionModule.MotionModuleWith_LvDai;

@Autonomous(name = "Auto", group = "FFF")
public class AutoOpMode extends LinearOpMode {
//    private CvModule cvModule;
    private FunctionModule functionModule;
    private MotionModule motionModule;
    private DcMotor[] motor = new DcMotor[2];

    @Override
    public void runOpMode() throws InterruptedException {
//        cvModule = new CvModuleImpl(hardwareMap);
        functionModule = new FunctionModuleImpl(hardwareMap);
        motionModule = new MotionModuleWith_LvDai(hardwareMap);

        motor[0] = hardwareMap.dcMotor.get("r");
        motor[1] = hardwareMap.dcMotor.get("l");
        int[] mode = new int[]{2,0,0};
//        for(int i = 0;i < 60;i++){
//            mode[cvModule.getMode()]++;
//        }
        int max = mode[0];
//        for(int i:mode){
//            if (i >= max) max = i;
//        }
        waitForStart();

        motionModule.movePosition(0.5,500);
        sleep(2000);
        motionModule.rotatePosition(0.5,-1150);
        sleep(3000);
        motionModule.movePosition(0.5,2000);
        sleep(3000);
        motionModule.rotatePosition(0.5,-1200);
        sleep(3000);
        if (max == 0) {
            motionModule.movePosition(0.5,-750);
            sleep(2000);
            functionModule.shooting(true);
            sleep(2000);
            functionModule.shooting(false);
            sleep(1000);
        } else if(max == 1) {
            motionModule.movePosition(0.5, -800);
            sleep(2500);
            functionModule.elevating(1);
            sleep(500);
            functionModule.shooting(true);
            sleep(2000);
            functionModule.shooting(false);
            sleep(1000);
            functionModule.elevating(0);
            sleep(500);
            motionModule.movePosition(0.5, 50);
            sleep(300);
        } else if(max == 2){
            motionModule.movePosition(0.5,-900);
            sleep(2500);
            functionModule.elevating(2);
            sleep(1000);
            functionModule.shooting(true);
            sleep(2000);
            functionModule.shooting(false);
            sleep(1000);
            functionModule.elevating(0);
            sleep(1000);
            motionModule.movePosition(0.5,70);
            sleep(300);
        }

        motionModule.rotatePosition(0.5,1200);
        sleep(3000);
        motionModule.movePosition(0.5,5000);
        sleep(4000);
        motionModule.stop();
        while (opModeIsActive()){
        }
        motionModule.stop();
        sleep(1000);

    }


}
