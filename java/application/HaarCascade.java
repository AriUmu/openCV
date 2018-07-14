package application;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class HaarCascade {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        //getFace("/Users/arina/Desktop/55.jpg", "/Users/arina/Desktop/Repository/opencv1/java/application/haarcascade/haarcascade_frontalface_alt.xml");
        //second is better
        getFace("/Users/arina/Desktop/55.jpg", "/Users/arina/Desktop/Repository/opencv1/java/application/haarcascade/haarcascade_frontalface_alt2.xml");
    }

    public static void getFace(String path, String path_cascade) {
        Mat img = CvUtils.loadPicture(path);
        CascadeClassifier face_detector = new CascadeClassifier();

        if (!face_detector.load(path_cascade)) {
            System.out.println("Не удалось загрузить классификатор!");
            return;
        }

        MatOfRect faces = new MatOfRect();
        face_detector.detectMultiScale(img, faces);

        for (Rect r : faces.toList()){
            Imgproc.rectangle(img, new Point(r.x, r.y),
                    new Point(r.x + r.width, r.y + r.height),
                    CvUtils.COLOR_WHITE, 2);
        }

        CvUtils.showImage(img, "Result");
        img.release();
    }
}
