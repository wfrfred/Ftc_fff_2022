package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.functionModule.FunctionModule;
import org.firstinspires.ftc.teamcode.functionModule.FunctionModuleImpl;
import org.firstinspires.ftc.teamcode.motionModule.MotionModule;
import org.firstinspires.ftc.teamcode.motionModule.MotionModuleWith_LvDai;

import java.util.List;

@Autonomous(name = "Auto", group = "FFF")
public class AutoOpMode extends LinearOpMode {
//    private CvModule cvModule;
    private FunctionModule functionModule;
    private MotionModule motionModule;
    private DcMotor[] motor = new DcMotor[2];
    private TouchSensor touchSensor;
//    private static final String TFOD_MODEL_ASSET = "FTC2022.tflite";
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
//    private static final String[] LABELS = {
//            "cube",
//            "pan",
//    };
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };
    private static final String VUFORIA_KEY = "AVneEoT/////AAABmTIGD9xAZ0AukIyy3GdHxM0yD7etszOcxfKiTKLZEVHPLXu3gpq/AYKXqIPFvb0pgBJzqZFiNGmCsKZ4ApGO2OJ1PamCs0loI2/qKsf8DbME27gTBmZtOrR82sJs4+WU5D9OjyufsIYYOcwcYuARxUZJkCTkcBJPfby+ifGXGoQbX39LMHtH+aisU2ASOXLawa2kRZFgzdU3ToWfNBUzoN3V045Zyjyuk+JqqthkV2JfuwYQlb8INvWXQkIR8696dwgZeTzD51UmAY+paeO7A2gHvPU+rqAMGjpmr7DKlRNdRBBjf44/rEcVUPslpHvsZ5k0BbxZbLyhgDEVynVwbz/m9E6atNNrad/xNKZvTNuI";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
    @Override

    public void runOpMode() throws InterruptedException {
        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(2, 2.5/1.0);
        }
        functionModule = new FunctionModuleImpl(hardwareMap);
        motionModule = new MotionModuleWith_LvDai(hardwareMap);

        motor[0] = hardwareMap.dcMotor.get("r");
        motor[1] = hardwareMap.dcMotor.get("l");
//        touchSensor = hardwareMap.touchSensor.get("touch");
        int findTimes = 0;
        int max = 2;
        if (!isStarted()) {
            while (!isStarted()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> Recognitions = tfod.getRecognitions();
                    if (Recognitions != null) {
                        telemetry.addData("# Object Detected", Recognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : Recognitions) {
                            if(recognition.getLabel() == "Ball"){
                                if (recognition.getLeft()<300){
                                    max = 0;
                                    telemetry.addData("left",0);
                                }
                                else{
                                    max = 1;
                                    telemetry.addData("middle", 1);
                                }
                            }
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            telemetry.addData(String.format("  Width,Height (%d)", i), "%.03f , %.03f",
                                    (float)recognition.getImageWidth(), (float)recognition.getImageHeight());
                            i++;
                        }
                        telemetry.update();
                    }
                    sleep(100);
                    findTimes ++;
                }
            }
        }
        telemetry.addData("position",max);
        waitForStart();
        motionModule.movePosition(0.5,-600);
        motionModule.rotatePosition(0.5,-400);
        sleep(200);
        if (max == 0) {
            motionModule.movePosition(0.45,-1375);
//            sleep(2000);
            functionModule.shooting(true);
            sleep(2000);
            functionModule.shooting(false);
            sleep(1000);
            motionModule.movePosition(0.45,175);
        } else if(max == 1) {
            motionModule.movePosition(0.5, -1450);
//            sleep(2500);
            functionModule.elevating(1);
            sleep(500);
            functionModule.shooting(true);
            sleep(2000);
            functionModule.shooting(false);
            sleep(1000);
            functionModule.elevating(0);
            sleep(500);
            motionModule.movePosition(0.5, 200);
//            sleep(300);
        } else if(max == 2){
            motionModule.movePosition(0.5,-1550);
//            sleep(2500);
            functionModule.elevating(2);
            sleep(1000);
            functionModule.shooting(true);
            sleep(2000);
            functionModule.shooting(false);
            sleep(1000);
            functionModule.elevating(0);
            sleep(1000);
            motionModule.movePosition(0.5,300);
//            sleep(300);
        }
        sleep(100);
        motionModule.rotatePosition(0.3,-675);
        sleep(200);
        motionModule.movePosition(0.5,-5500);
//        sleep(4000);
        motionModule.stop();
        while (opModeIsActive()){
        }
        motionModule.stop();
        sleep(1000);

    }


}
