package application;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Laplacian {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
         getLaplacian("/Users/arina/Desktop/orig.png");
        //getLaplacian("/Users/arina/Desktop/я.jpg");
    }

    public static void getLaplacian(String path){
        Mat img = CvUtils.loadPicture(path);
        CvUtils.showImage(img, "Origin");

        Mat imgGrey = new Mat();
        Imgproc.cvtColor(img, imgGrey, Imgproc.COLOR_BGR2GRAY);

        Mat dstLaplacian = new Mat();
        Imgproc.Laplacian(imgGrey, dstLaplacian, CvType.CV_32F);

        Mat imgLaplacian = new Mat();
        Core.convertScaleAbs(dstLaplacian, imgLaplacian);
        CvUtils.showImage(imgLaplacian, "Laplacian ksize = 1");

        Mat dstLaplacian2 = new Mat();
        Imgproc.Laplacian(imgGrey, dstLaplacian2, CvType.CV_32F, 3, 1, 0);

        Mat imgLaplacian2 = new Mat();
        Core.convertScaleAbs(dstLaplacian2, imgLaplacian2);
        CvUtils.showImage(imgLaplacian2, "Laplacian ksize=3");

        img.release();
        imgGrey.release();
        dstLaplacian.release();
        dstLaplacian2.release();
        imgLaplacian.release();
        imgLaplacian2.release();
    }
}
