package application;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

public class CorrectPic {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        //setNegative("/Users/arina/Desktop/r.jpg");
        setSepia("/Users/arina/Desktop/r.jpg");
    }

    public static void setNegative(String path) {
        Mat img = CvUtils.loadPicture(path);

        Mat m = new Mat(img.rows(), img.cols(), img.type(), CvUtils.COLOR_WHITE);
        Mat negative = new Mat();
        Core.subtract(m, img, negative);
        CvUtils.showImage(negative, "Негатив");
        img.release();
        negative.release();
        m.release();
    }

    public static void setSepia(String path){
        Mat img = CvUtils.loadPicture(path);
        Mat kernel = new Mat(3,3, CvType.CV_32FC1);
        kernel.put(0,0,0.131, 0.534, 0.373,//blue
                0.168, 0.686, 0.349, //green
                0,189, 0.769, 0.393  //red
                );
        Mat sepia = new Mat();
        Core.transform(img, sepia, kernel);
        CvUtils.showImage(sepia, "Сепия");
        img.release();
        kernel.release();
        sepia.release();
    }
}
