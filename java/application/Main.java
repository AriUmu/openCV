package application;

import org.opencv.core.*;

public class Main {
  //first of all load the lib
    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Scalar s = new Scalar(1.0);
        System.out.println(s);
        System.out.println(s.val[0]);


        Mat m = Mat.zeros(3,2 , CvType.CV_8UC1);
        Mat m1 = new Mat(new Size(6,6), CvType.CV_8UC1);
        System.out.println(m.dump());
        System.out.println(m1.dump());

        System.out.println(m1.toString());
        System.out.println("_______________________________");

        Mat mt = m1.t();
        System.out.println(mt.dump());
    }
}
