package application;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class VideoOpenCV {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
     VideoCapture camera = new VideoCapture();
     camera.open(0);
        try {
            Thread.sleep(10000);
            camera.grab();
            Mat m = new Mat();
            camera.retrieve(m);
            CvUtils.showImage(m, "Img");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!camera.isOpened()){
         System.out.println("Cam is closed!");
         return;
     }
     System.exit(0);
    }

    public static void openCam(){
        JFrame window = new JFrame("Просмотр видео");
        window.setSize(1000,600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        JLabel label = new JLabel();
        window.setContentPane(label);
        window.setVisible(true);

        VideoCapture capture = new VideoCapture("/Users/arina/Desktop/11.avi");
        if(!capture.isOpened()){
            System.out.println("Video is not opened");
            return;
        }
        Mat frame = new Mat();
        BufferedImage img = null;
        while (capture.read(frame)){
            Imgproc.resize(frame, frame, new Size(960, 540));
            img =  CvUtils.MapToBufferedImage(frame);
            if(img != null ){
                ImageIcon imageIcon = new ImageIcon(img);
                label.setIcon(imageIcon);
                label.repaint();
                window.pack();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Выход");
        capture.release();
    }

}
