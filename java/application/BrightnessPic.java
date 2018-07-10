package application;

import org.opencv.core.Core;
import org.opencv.core.CvType;
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
        //setWhiteBalance("/Users/arina/Desktop/r.jpg");
        setContrast("/Users/arina/Desktop/r.jpg");
    }


    public static void setBrightness(String path) {
        Mat img = CvUtils.loadPicture(path);

        Mat imgHSV = new Mat();
        Imgproc.cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HLS);
        //Увеличение яркости
        //Core.add(imgHSV, new Scalar(0,0,40), imgHSV);
        //Уменьшение яркости
        Core.add(imgHSV, new Scalar(0, 0, -40), imgHSV);
        Mat imgBRG = new Mat();
        Imgproc.cvtColor(imgHSV, imgBRG, Imgproc.COLOR_HSV2BGR);
        CvUtils.showImage(img, "Яркость +0");
        CvUtils.showImage(imgBRG, "Яркость +40");
        img.release();
        imgBRG.release();
        imgHSV.release();
    }

    public static void setSaturation(String path) {
        Mat img = CvUtils.loadPicture(path);

        Mat imgHSV = new Mat();
        Imgproc.cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HSV);
        //Увеличение насыщеность
        //Core.add(imgHSV, new Scalar(0,40,0), imgHSV);
        //Уменьшение насыщености
        Core.add(imgHSV, new Scalar(0, -40, 0), imgHSV);
        Mat imgBRG = new Mat();
        Imgproc.cvtColor(imgHSV, imgBRG, Imgproc.COLOR_HSV2BGR);
        CvUtils.showImage(img, "Насыщеность +0");
        CvUtils.showImage(imgBRG, "Насыщеность +40");
        img.release();
        imgBRG.release();
        imgHSV.release();
    }

    public static void setWhiteBalance(String path) {
        Mat img = CvUtils.loadPicture(path);
        Mat imgLab = new Mat();
        Imgproc.cvtColor(img, imgLab, Imgproc.COLOR_BGR2Lab);
        //Положение цвета от зеленого до красного
        Core.add(imgLab, new Scalar(0, 10, 0), imgLab);
        //Положение цвета от синего до желтого
        //Core.add(imgLab, new Scalar(0, 0, 20), imgLab);
        Mat imgBRG = new Mat();
        Imgproc.cvtColor(imgLab, imgBRG, Imgproc.COLOR_Lab2BGR);
        CvUtils.showImage(img, " +0");
        CvUtils.showImage(imgBRG, "+N");
        img.release();
        imgBRG.release();
        imgLab.release();
    }

    public static void setContrast(String path) {
        Mat img = CvUtils.loadPicture(path);
        Scalar meanBGR = Core.mean(img);
        //Вычисление средней яркости
        double mean = meanBGR.val[0] * 0.114 + meanBGR.val[1] * 0.587 + meanBGR.val[2] * 0.299;
        //Коэфициент контраста
        double contrast = 1.5;
        //Построение таблиц соответствия
        Mat lut = new Mat(1, 256, CvType.CV_8UC1);
        byte[] arr = new byte[256];
        int color = 0;
        for (int i = 0; i < 256; i++) {
            color = (int) (contrast * (i - mean) + mean);
            //color = (int) (contrast* (i - 128) + 128);
            //color = (int) ((contrast * (i/255.0-0.5) + 0.5)*255);
            color = color > 255 ? 255 : (color < 0 ? 0 : color);
            arr[i] = (byte) color;
        }

        lut.put(0, 0, arr);
        //Применение таблицы соответсвия к изображению
        Mat img2 = new Mat();
        Core.LUT(img, lut, img2);
        CvUtils.showImage(img2, "Контраст " + contrast);
        img.release();
        img2.release();
        lut.release();
    }
}
