package application;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class AutoCorrectHistogramm {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        getAutoHistogramm("/Users/arina/Desktop/r.jpg");
    }

    public static void getAutoHistogramm(String path) {
        Mat img = CvUtils.loadPicture(path);

        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);

        Mat img3 = new Mat();
        Imgproc.equalizeHist(img2, img3);
        //Вычисляем и отрисовываем диаграммы
        ArrayList<Mat> images = new ArrayList<>();
        images.add(img2);
        images.add(img3);
        Mat hist = new Mat();
        Mat hist2 = new Mat();
        Imgproc.calcHist(images, new MatOfInt(0), new Mat(),
                hist, new MatOfInt(256), new MatOfFloat(0, 256));

        Imgproc.calcHist(images, new MatOfInt(1), new Mat(),
                hist2, new MatOfInt(256), new MatOfFloat(0, 256));

        Core.normalize(hist, hist, 0, 128, Core.NORM_MINMAX);
        Core.normalize(hist2, hist2, 0, 128, Core.NORM_MINMAX);
        double v = 0;
        int h = 150;
        Mat imgHist = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Mat imgHist2 = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);

        for (int i = 0, j = hist.rows(); i < j; i++) {
            v = Math.round(hist.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist, new Point(i, h - 1),
                        new Point(i, h - 1 - v), CvUtils.COLOR_BLACK);
            }
            v = Math.round(hist2.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist2, new Point(i, h - 1),
                        new Point(i, h - 1 - v), CvUtils.COLOR_BLACK);
            }
        }
        CvUtils.showImage(img2, "Ogininal");
        CvUtils.showImage(imgHist, "Histogram before");
        CvUtils.showImage(img3, "EqualizeHist");
        CvUtils.showImage(imgHist2, "Histogram after");

        img.release();
        img2.release();
        img3.release();
        imgHist.release();
        imgHist2.release();
        hist.release();
        hist2.release();
    }
}
