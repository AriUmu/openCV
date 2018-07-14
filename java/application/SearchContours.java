package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class SearchContours {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        getCountours("/Users/arina/Desktop/2.jpg");
    }

    public static void getCountours(String path){
        Mat img = CvUtils.loadPicture(path);
        CvUtils.showImage(img, "Origin");

        Mat imgGrey = new Mat();
        Imgproc.cvtColor(img, imgGrey, Imgproc.COLOR_BGR2GRAY);
        Mat edges = new Mat();
        Imgproc.Canny(imgGrey, edges, 80,200);
        CvUtils.showImage(edges, "Canny");

        Mat edgesCopy = edges.clone();
        ArrayList<MatOfPoint> countour = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edgesCopy, countour, hierarchy,
                Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        System.out.println(countour.size());
        System.out.println(hierarchy.size());
     //   System.out.println(hierarchy.dump());

        Imgproc.drawContours(img, countour, -1, CvUtils.COLOR_WHITE);
        img.release();
        imgGrey.release();
        edges.release();
        edgesCopy.release();
        hierarchy.release();
    }

}
