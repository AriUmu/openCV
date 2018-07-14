package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class SearchByTemplates {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        toSearchByTemplate("/Users/arina/Desktop/r.jpg");
    }

    public static void toSearchByTemplate(String path) {
        Mat img = CvUtils.loadPicture(path);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        CvUtils.showImage(img, "Origin");
        Mat img2 = img.submat(new Rect(30, 120, 45, 60)).clone();
        CvUtils.showImage(img2, "Template");

        Mat result = new Mat();
        Imgproc.matchTemplate(img, img2, result, Imgproc.TM_SQDIFF);
        Core.MinMaxLocResult r = Core.minMaxLoc(result);
        System.out.println(r.minVal + " " + r.minLoc);

        Imgproc.rectangle(img, r.minLoc, new Point(r.minLoc.x + img2.width() - 1,
                r.minLoc.y + img2.height() - 1), CvUtils.COLOR_WHITE);
        CvUtils.showImage(img, "Result of Search");

        Mat result2 = new Mat();
        Imgproc.matchTemplate(img, img2, result2, Imgproc.TM_SQDIFF);
        Core.MinMaxLocResult r2 = Core.minMaxLoc(result2);
        System.out.println(r2.maxVal + "  " + r2.maxLoc);

        img.release();
        img2.release();
        result.release();
        result2.release();
    }
}
