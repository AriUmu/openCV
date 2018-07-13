package application;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class Scharr {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        setScharr();
    }

    public static void setScharr(){
        Mat  img = new Mat(150, 300, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Imgproc.rectangle(img, new Point(20,20), new Point(120,70),
                CvUtils.COLOR_GREEN, Core.FILLED);

        CvUtils.showImage(img, "Origin");

        Mat imgGrey = new Mat();
        Imgproc.cvtColor(img, imgGrey, Imgproc.COLOR_BGR2GRAY);

        Mat dstSobelX = new Mat();
        Imgproc.Sobel(imgGrey, dstSobelX, CvType.CV_32F, 1, 0);

        Mat imgSobelX = new Mat();
        Core.convertScaleAbs(dstSobelX, imgSobelX);
        CvUtils.showImage(imgSobelX, "SobelX");

        img.release();
        imgGrey.release();
        imgSobelX.release();
    }
}
