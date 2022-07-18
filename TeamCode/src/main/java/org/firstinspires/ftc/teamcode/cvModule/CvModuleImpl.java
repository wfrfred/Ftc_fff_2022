package org.firstinspires.ftc.teamcode.cvModule;

import android.graphics.Bitmap;
import android.util.Base64;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CvModuleImpl implements CvModule {
    private final Camera camera;
    private VideoCapture videoCapture;

    public CvModuleImpl(HardwareMap hardwareMap) {
        OpenCVLoader.initDebug();
        camera = new Camera(hardwareMap);
        camera.startCamera();
    }


    @Override
    public int getMode() {
        Bitmap bmp = camera.getFrame();
        Mat mat = new Mat();
        //bmp = Bitmap.createBitmap(bmp,0,bmp.getHeight()/3,bmp.getWidth()-1,bmp.getHeight()/3-1);
//        int w = bmp.getWidth();
//        int h = bmp.getHeight();
//        Mat mat = new Mat(h,w, CvType.CV_32SC4,new Scalar(0));
        Utils.bitmapToMat(bmp, mat);
        Imgproc.resize(mat,mat,new Size(640,480));
//        System.out.println(byte2Base64(bitmap2Byete(bmp)));
        return cal(mat);
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
            int red = max(area);
            area.remove(red);
            red = max(area);
            area.remove(red);
            int red1 = max(area);
            return new double[]{contours.get(red).toList().get(0).x, contours.get(red1).toList().get(0).x};
        }
        int green = max(area);
        System.out.println(green);
        System.out.println(contours.get(green).toList().get(0).x);
        return new double[]{contours.get(green).toList().get(0).x, 0};
    }

    private int max(HashMap<Integer, Double> map) {
        List<Map.Entry<Integer, Double>> list = new ArrayList(map.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list.get(0).getKey();
    }

    private int cal(Mat img){
        Mat mat = new Mat();
        Imgproc.cvtColor(img, mat, Imgproc.COLOR_BGR2HSV);
        Imgproc.blur(mat, mat, new Size(3, 3));
        Mat red = new Mat();
        Mat green = new Mat();
        Core.inRange(mat, new Scalar(0,45,0), new Scalar(20,255,255), red);
        Core.inRange(mat, new Scalar(31, 32, 155), new Scalar(91, 255, 255), green);


        Imgproc.erode(green,green,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(7,7)),new Point(-1,-1),1);
        Imgproc.dilate(green,green,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(3,3)),new Point(-1,-1),3);

        Imgproc.erode(red,red,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(3,3)),new Point(-1,-1),2);
        Imgproc.dilate(red,red,Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,new Size(11,11)),new Point(-1,-1),3);


        int ret = -1;
        double[] r = new double[2];
        double g = 0;
        try {
            r = getContours(red, 0);
            g = getContours(green, 1)[0];
        } catch (IndexOutOfBoundsException e) {
            System.out.println(g);
        }
        System.out.println("r:" + r[0] + " " + r[1] + "g:" + g);
        if (g < r[0] && g < r[1]) ret = 0;
        else if (g > r[0] && g > r[1]) ret = 2;
        else ret = 1;
        return ret;
    }

    public byte[] bitmap2Byete(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,outputStream);
        return outputStream.toByteArray();
    }

    public String byte2Base64(byte[] imageByte){
        if(null == imageByte) return null;
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }
}


