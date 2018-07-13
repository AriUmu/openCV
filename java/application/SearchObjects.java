package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class SearchObjects {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        borderline("/Users/arina/Desktop/3.jpg");
    }

    public static void borderline(String path) {
        Mat img = CvUtils.loadPicture(path);
        Mat imgGrey = new Mat();
        Imgproc.cvtColor(img, imgGrey, Imgproc.COLOR_BGR2GRAY);
        CvUtils.showImage(imgGrey, "GREY");

        Mat edges = new Mat();
        Imgproc.Canny(imgGrey, edges, 80, 200);
        CvUtils.showImage(edges, "Canny");

        Mat img3 = new Mat();
        Imgproc.threshold(imgGrey, img3, 100, 255,
                Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        Mat edges2 = new Mat();
        Imgproc.Canny(img3, edges2, 80, 200);
        CvUtils.showImage(edges2, "Canny + THRESH_OTSU");


        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(imgGrey, img4, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, 5);

        Mat edges3 = new Mat();
        Imgproc.Canny(img4, edges3, 80, 200);
        CvUtils.showImage(edges3, "Canny + adaptiveThreshold");


        img.release();
        img3.release();
        img4.release();

        edges.release();
        edges2.release();
        edges3.release();
    }
}
