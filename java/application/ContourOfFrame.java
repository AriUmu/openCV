package application;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class ContourOfFrame {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        setContour("/Users/arina/Desktop/12.jpg");
    }

    public static void setContour(String path) {
        Mat img = CvUtils.loadPicture(path);

        CvUtils.showImage(img, "Origin");
        Mat imgGrey = new Mat();

        Imgproc.cvtColor(img, imgGrey, Imgproc.COLOR_BGR2GRAY);
        Mat edges = new Mat();

        Imgproc.Canny(imgGrey, edges, 80, 200);
        Mat edgesCopy = edges.clone();
        ArrayList<MatOfPoint> contour = new ArrayList<>();
        Imgproc.findContours(edgesCopy, contour, new Mat(),
                Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0, j = contour.size(); i < j; i++) {
            System.out.println(Imgproc.contourArea(contour.get(i)));
            Rect r = Imgproc.boundingRect(contour.get(i));
            System.out.println("boundingRct = " + r);
            double len = Imgproc.arcLength(new MatOfPoint2f(contour.get(i).toArray()),
                    true);

            System.out.println("arcLength = " + len);
            Imgproc.rectangle(img, new Point(r.x, r.y), new Point(r.x + r.width - 1, r.y + r.height - 1),
                    CvUtils.COLOR_WHITE);
        }
        CvUtils.showImage(img, "boundingRect");
        img.release();
        imgGrey.release();
        edges.release();
        edgesCopy.release();

    }


}
