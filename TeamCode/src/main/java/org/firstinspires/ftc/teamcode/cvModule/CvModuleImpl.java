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
        Mat blue = new Mat();
        Mat red = new Mat();
        Core.inRange(mat, new Scalar(30, 81, 228), new Scalar(155, 199, 255), blue);
        Core.inRange(mat, new Scalar(0, 80, 50), new Scalar(8, 245, 220), red);


        Imgproc.erode(red,red,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(7,7)),new Point(-1,-1),7);
        Imgproc.dilate(red,red,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(3,3)),new Point(-1,-1),2);

        Imgproc.erode(blue,blue,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(7,7)),new Point(-1,-1),7);
        Imgproc.dilate(blue,blue,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(3,3)),new Point(-1,-1),2);

        double[] b = new double[2];
        double r = 0;
        try {
            b = getContours(blue, 0);
            r = getContours(red, 0)[0];
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
        System.out.println("b:" + b[0] + " " + b[1] + "r:" + r);
        if (r < b[0] && r < b[1]) ret = 0;
        else if (r > b[0] && r > b[1]) ret = 2;
        else ret = 1;
        return ret;
    }

    private double[] getContours(Mat mat, int color) {
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(mat, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        HashMap<Integer, Double> area = new HashMap<>();
        for (int i = 0; i < contours.size(); i++) {
            area.put(i, Imgproc.contourArea(contours.get(i)));
        }
        if (color == 0) {
            int blue0 = max(area);
            area.remove(blue0);
            int blue1 = max(area);
            return new double[]{contours.get(blue0).toList().get(0).x, contours.get(blue1).toList().get(0).x};
        }
        int red0 = max(area);
        return new double[]{contours.get(red0).toList().get(0).x, 0};
    }

    private int max(HashMap<Integer, Double> map) {
        Set<Integer> set = map.keySet();
        map.entrySet();
        List<Map.Entry<Integer, Double>> list = new ArrayList(map.entrySet());
        return list.get(0).getKey();
    }

}
