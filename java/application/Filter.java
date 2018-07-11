package application;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class Filter {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        // setBlur("/Users/arina/Desktop/r.jpg");
        //setGaussianBlur("/Users/arina/Desktop/r.jpg");
        //setBilateralFilter("/Users/arina/Desktop/r.jpg");
        setFilter2D("/Users/arina/Desktop/r.jpg");
    }

    //данный фильт добавляет размытость
    public static void setBlur(String path) {
        Mat img = CvUtils.loadPicture(path);
        Mat img2 = new Mat();
        Imgproc.blur(img, img2, new Size(3, 3));
        CvUtils.showImage(img2, "Size 3x3");

        Mat img3 = new Mat();
        Imgproc.blur(img, img3, new Size(45, 45), new Point(-1, 1));
        CvUtils.showImage(img3, "Size 45x45");
        img.release();
        img2.release();
        img3.release();
    }

    public static void setGaussianBlur(String path) {
        Mat img = CvUtils.loadPicture(path);

        Mat img2 = new Mat();
        Imgproc.GaussianBlur(img, img2, new Size(3, 3), 0);
        CvUtils.showImage(img2, "Size 3x3");


        Mat img3 = new Mat();
        Imgproc.GaussianBlur(img, img3, new Size(45, 45), 0);
        CvUtils.showImage(img3, "Size 45x45");


        Mat img4 = new Mat();
        Imgproc.GaussianBlur(img, img4, new Size(0, 0), 1.5);
        CvUtils.showImage(img3, "Size 0x0, 1.5");

        img.release();
        img2.release();
        img3.release();
        img4.release();
    }

    public static void setBilateralFilter(String path) {
        Mat img = CvUtils.loadPicture(path);

        //d = диаметр окрустности пиксела
        Mat img2 = new Mat();
        Imgproc.bilateralFilter(img, img2, 5, 5 * 2, 5 * 2);
        CvUtils.showImage(img2, "d = 5");

        Mat img3 = new Mat();
        Imgproc.bilateralFilter(img, img3, 9, 9 * 2, 9 * 2);
        CvUtils.showImage(img3, "d = 9");

        img.release();
        img2.release();
        img3.release();
    }

    public static void setFilter2D(String path) {
        Mat img = CvUtils.loadPicture(path);

        Mat kernel = Mat.ones(new Size(3, 3), CvType.CV_32FC1);
        Core.divide(kernel, new Scalar(kernel.rows() * kernel.cols()), kernel);
        System.out.println(kernel.dump());

        Mat img2 = new Mat();
        Imgproc.filter2D(img, img2, -1, kernel);
        CvUtils.showImage(img2, "Filter2D");

        Mat img3 = new Mat();
        Imgproc.blur(img, img3, new Size(3, 3), new Point(-1, -1));
        CvUtils.showImage(img3, "Blur");


        Mat kernelGuassian = Imgproc.getGaussianKernel(3, 0, CvType.CV_64F);
        Mat img4 = new Mat();
        Imgproc.sepFilter2D(img, img4, -1, kernelGuassian, kernelGuassian);
        CvUtils.showImage(img4, "SepFilter2D");

        Mat img5 = new Mat();
        Imgproc.GaussianBlur(img, img5, new Size(3, 3), 0);
        CvUtils.showImage(img5, "GuassianBlur");
        img.release();
        img2.release();
        img3.release();
        img4.release();
        img5.release();
        kernel.release();
        kernelGuassian.release();
    }
}
