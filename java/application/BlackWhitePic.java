package application;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class BlackWhitePic {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        //  getBlackWhitePic("/Users/arina/Desktop/r.jpg");
        adaptiveThreshold("/Users/arina/Desktop/r.jpg");
    }

    public static void getBlackWhitePic(String path) {
        Mat img = Imgcodecs.imread(path);
        if (img.empty()) {
            System.out.println("Img is empty");
            return;
        }

        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);
        Mat img3 = new Mat();
        double thresh = Imgproc.threshold(img2, img3, 100, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        System.out.println(thresh);

        GreyPic.showImage(img3, "THRESH_OTSU");
        img.release();
        img2.release();
        img3.release();
    }

    public static void adaptiveThreshold(String path) {
        Mat img = Imgcodecs.imread(path);
        if (img.empty()) {
            System.out.println("Img is empty");
            return;
        }

        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);
        Mat img3 = new Mat();
        Imgproc.adaptiveThreshold(img2, img3, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, 5);
        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(img2, img4, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, 5);
        GreyPic.showImage(img3, "Adaptive_thresh_mean_c");
        GreyPic.showImage(img4, "Adaptive_thresh_gaussian_c + thresh_binary_inv");
        //инверсия с помощью теблицы соответствия

        Mat lut = new Mat(1, 256, CvType.CV_8UC1);
        byte[] arr = new byte[256];
        for (int i = 0; i < 256; i++) {
            arr[i] = (byte) (255 - i);
        }

        lut.put(0, 0, arr);
        //Преобразование в соотвествии с таблицей
        Mat imgInv = new Mat();
        Core.LUT(img3, lut, imgInv);
        GreyPic.showImage(imgInv, "Adaptive_thresh_mean_c + inv");
        img.release();
        img2.release();
        img3.release();
        img4.release();
        imgInv.release();
        lut.release();
    }
}
