package application;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class Gistogramm {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        showGistogramm("/Users/arina/Desktop/1.jpg");
    }

    public static void showGistogramm(String path) {
        Mat img = CvUtils.loadPicture(path);

        CvUtils.showImage(img, "Origin");
        Mat imgGrey = new Mat();
        Imgproc.cvtColor(img, imgGrey, Imgproc.COLOR_BGR2GRAY);
        //Вычисляем гистограммы по каналам
        ArrayList<Mat> images = new ArrayList<>();
        images.add(img);
        images.add(imgGrey);
        Mat histGrey = new Mat();
        Mat histRed = new Mat();
        Mat histGreen = new Mat();
        Mat histBlue = new Mat();

        Imgproc.calcHist(images, new MatOfInt(3), new Mat(),
                histGrey, new MatOfInt(256), new MatOfFloat(0, 256));

        Imgproc.calcHist(images, new MatOfInt(2), new Mat(),
                histRed, new MatOfInt(256), new MatOfFloat(0, 256));

        Imgproc.calcHist(images, new MatOfInt(1), new Mat(),
                histGreen, new MatOfInt(256), new MatOfFloat(0, 256));

        Imgproc.calcHist(images, new MatOfInt(0), new Mat(),
                histBlue, new MatOfInt(256), new MatOfFloat(0, 256));
        //Нормализайия диапазона

        Core.normalize(histGrey, histGrey, 0, 128, Core.NORM_MINMAX);
        Core.normalize(histRed, histRed, 0, 128, Core.NORM_MINMAX);
        Core.normalize(histGreen, histGreen, 0, 128, Core.NORM_MINMAX);
        Core.normalize(histBlue, histBlue, 0, 128, Core.NORM_MINMAX);

        //Отрисовка гистограммы
        double v = 0;
        int h = 150;
        Mat imgHistRed = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Mat imgHistGreen = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Mat imgHistBlue = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Mat imgHistGrey = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);

        for (int i = 0, j = histGrey.rows(); i < j; i++) {
            v = Math.round(histRed.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHistRed, new Point(i, h - 1),
                        new Point(i, h - 1 - v), CvUtils.COLOR_RED);
            }

            v = Math.round(histGreen.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHistGreen, new Point(i, h - 1),
                        new Point(i, h - 1 - v), CvUtils.COLOR_GREEN);
            }

            v = Math.round(histBlue.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHistBlue, new Point(i, h - 1),
                        new Point(i, h - 1 - v), CvUtils.COLOR_BLUE);
            }

            v = Math.round(histGrey.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHistGrey, new Point(i, h - 1),
                        new Point(i, h - 1 - v), CvUtils.COLOR_GREY);
            }
        }

        CvUtils.showImage(imgHistRed, "Red");
        CvUtils.showImage(imgHistGreen, "Green");
        CvUtils.showImage(imgHistBlue, "Blue");
        CvUtils.showImage(imgHistGrey, "Grey");

        img.release();
        imgGrey.release();
        imgHistBlue.release();
        imgHistGreen.release();
        imgHistRed.release();
        imgHistGrey.release();
        histBlue.release();
        histGreen.release();
        histRed.release();
        histGrey.release();
    }
}
