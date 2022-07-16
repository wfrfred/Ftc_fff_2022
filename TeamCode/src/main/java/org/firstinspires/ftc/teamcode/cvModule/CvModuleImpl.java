package org.firstinspires.ftc.teamcode.cvModule;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CvModuleImpl implements CvModule {
    private final Camera camera;

    public CvModuleImpl(HardwareMap hardwareMap) {
        OpenCVLoader.initDebug();
        camera = new Camera(hardwareMap);
        camera.startCamera();
    }


    @Override
    public int getMode() {
        Mat mat = new Mat();
        int ret = 0;
        Bitmap bmp = camera.getFrame();
        bmp = Bitmap.createBitmap(bmp,0,bmp.getHeight()/2,bmp.getWidth()-1,bmp.getHeight()/2-1);
        Utils.bitmapToMat(bmp, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2HSV);
        Imgproc.blur(mat, mat, new Size(3, 3));
        Mat red = new Mat();
        Mat green = new Mat();
        Core.inRange(mat, new Scalar(153,20,155), new Scalar(215,255,255), red);
        Core.inRange(mat, new Scalar(31, 32, 155), new Scalar(91, 255, 255), green);

//
//        Imgproc.erode(green,green,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(7,7)),new Point(-1,-1),7);
//        Imgproc.dilate(green,green,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(3,3)),new Point(-1,-1),2);

//        Imgproc.erode(red,red,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(3,3)),new Point(-1,-1),2);
        Imgproc.dilate(red,red,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(13,13)),new Point(-1,-1),3);

        double[] b = new double[2];
        double r = 0;
        try {
            b = getContours(red, 0);
            System.out.print("red get");
            r = getContours(green, 1)[0];
            System.out.println("green get");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(r);
            return -1;
        }
        System.out.println("r:" + b[0] + " " + b[1] + "g:" + r);
        if (r < b[0] && r < b[1]) ret = 0;
        else if (r > b[0] && r > b[1]) ret = 2;
        else ret = 1;
        return ret;
    }

    private double[] getContours(Mat mat, int color) {
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mat, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        if(color==1){
            System.out.println("geen countours:"+contours.size());
        }
        if(color==0){
            System.out.println("red countours:"+contours.size());
        }
        HashMap<Integer, Double> area = new HashMap<>();
        for (int i = 0; i < contours.size(); i++) {
            area.put(i, Imgproc.contourArea(contours.get(i)));
        }
        if (color == 0) {
            int blue0 = max(area);
            area.remove(blue0);
            blue0 = max(area);
            area.remove(blue0);
            int blue1 = max(area);
            return new double[]{contours.get(blue0).toList().get(0).x, contours.get(blue1).toList().get(0).x};
        }
        int red0 = max(area);
        System.out.println(red0);
        return new double[]{contours.get(red0).toList().get(0).x, 0};
    }

    private int max(HashMap<Integer, Double> map) {
        List<Map.Entry<Integer, Double>> list = new ArrayList(map.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list.get(0).getKey();
    }
}
