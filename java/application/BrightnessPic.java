package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class BrightnessPic {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        //setBrightness("/Users/arina/Desktop/r.jpg");
        //setSaturation("/Users/arina/Desktop/r.jpg");
        setWhiteBalance("/Users/arina/Desktop/r.jpg");
    }


    public static void setBrightness(String path) {
        Mat img = Imgcodecs.imread(path);
        if (img.empty()) {
            System.out.println("Img is empty");
            return;
        }

        Mat imgHSV = new Mat();
        Imgproc.cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HLS);
        //Увеличение яркости
        //Core.add(imgHSV, new Scalar(0,0,40), imgHSV);
        //Уменьшение яркости
        Core.add(imgHSV, new Scalar(0, 0, -40), imgHSV);
        Mat imgBRG = new Mat();
        Imgproc.cvtColor(imgHSV, imgBRG, Imgproc.COLOR_HSV2BGR);
        GreyPic.showImage(img, "Яркость +0");
        GreyPic.showImage(imgBRG, "Яркость +40");
        img.release();
        imgBRG.release();
        imgHSV.release();
    }

    public static void setSaturation(String path) {
        Mat img = Imgcodecs.imread(path);
        if (img.empty()) {
            System.out.println("Img is empty");
            return;
        }
        Mat imgHSV = new Mat();
        Imgproc.cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HSV);
        //Увеличение насыщеность
        //Core.add(imgHSV, new Scalar(0,40,0), imgHSV);
        //Уменьшение насыщености
        Core.add(imgHSV, new Scalar(0, -40, 0), imgHSV);
        Mat imgBRG = new Mat();
        Imgproc.cvtColor(imgHSV, imgBRG, Imgproc.COLOR_HSV2BGR);
        GreyPic.showImage(img, "Насыщеность +0");
        GreyPic.showImage(imgBRG, "Насыщеность +40");
        img.release();
        imgBRG.release();
        imgHSV.release();
    }

    public static void setWhiteBalance(String path) {
        Mat img = Imgcodecs.imread(path);
        if (img.empty()) {
            System.out.println("Img is empty");
            return;
        }
        Mat imgLab = new Mat();
        Imgproc.cvtColor(img, imgLab, Imgproc.COLOR_BGR2Lab);
        //Положение цвета от зеленого до красного
        Core.add(imgLab, new Scalar(0, 10, 0), imgLab);
        //Положение цвета от синего до желтого
        //Core.add(imgLab, new Scalar(0, 0, 20), imgLab);
        Mat imgBRG = new Mat();
        Imgproc.cvtColor(imgLab, imgBRG, Imgproc.COLOR_Lab2BGR);
        GreyPic.showImage(img, " +0");
        GreyPic.showImage(imgBRG, "+N");
        img.release();
        imgBRG.release();
        imgLab.release();
    }
}
