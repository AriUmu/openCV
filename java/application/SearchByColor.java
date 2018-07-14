package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.awt.*;

public class SearchByColor {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static void main(String[] args) {
        searchObjByColor("/Users/arina/Desktop/13.jpg");
    }

    public static void searchObjByColor(String path){
        Mat img = CvUtils.loadPicture(path);
        CvUtils.showImage(img, "Origin");

        Mat hsv = new Mat();
        Imgproc.cvtColor(img, hsv, Imgproc.COLOR_BGR2HSV);
        Mat h = new Mat();
        Core.extractChannel(hsv, h, 0);

        Mat img2 = new Mat();
        Core.inRange(h, new Scalar(40), new Scalar(80), img2);
        CvUtils.showImage(img2, "Green");

        Core.inRange(h, new Scalar(100), new Scalar(140), img2);
        CvUtils.showImage(img2, "Blue");

        Core.inRange(hsv, new Scalar(0, 200, 200), new Scalar(20, 256, 256), img2);
        CvUtils.showImage(img2, "Red");

        Core.inRange(hsv, new Scalar(0,0,0), new Scalar(0,0,50), img2);
        CvUtils.showImage(img2, "Black");

        img.release();
        img2.release();
        h.release();
        hsv.release();
    }





}
