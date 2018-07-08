package application;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.Arrays;


public class GreyPic {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        Mat img = Imgcodecs.imread("/Users/arina/Desktop/1.jpg");

//        BufferedImage image = MapToBufferedImage(img);
//        Mat mat = BufferedImgeToMat(image);
        if(img.empty()){
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        else {
            showImage(img, "Изображение");
        }
    }


    public static BufferedImage MapToBufferedImage(Mat m) {
        if (m == null || m.empty()) return null;
        if (m.depth() == CvType.CV_8U) {
        } else if (m.depth() == CvType.CV_16U) {
            Mat m_16 = new Mat();
            m.convertTo(m_16, CvType.CV_8U, 255.0 / 65535);
            m = m_16;
        } else if (m.depth() == CvType.CV_32F) {
            Mat m_32 = new Mat();
            m.convertTo(m_32, CvType.CV_8U, 255);
            m = m_32;
        } else return null;

        int type = 0;
        if (m.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (m.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else if (m.channels() == 4) {
            type = BufferedImage.TYPE_4BYTE_ABGR;
        } else return null;

        byte[] buf = new byte[m.channels() * m.cols() * m.rows()];
        m.get(0, 0, buf);
        byte tmp = 0;
        if (m.channels() == 4) {
            for (int i = 0; i < buf.length; i += 4) {
                tmp = buf[i + 3];
                buf[i + 3] = buf[i + 2];
                buf[i + 2] = buf[i + 1];
                buf[i + 1] = buf[i];
                buf[i] = tmp;
            }
        }

        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buf, 0, data, 0, buf.length);
        return image;
    }

    public static Mat BufferedImgeToMat(BufferedImage image) {
        if (image == null) return new Mat();
        int type = 0;
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            type = CvType.CV_8UC1;
        } else if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            type = CvType.CV_8UC3;
        } else if (image.getType() == BufferedImage.TYPE_4BYTE_ABGR) {
            type = CvType.CV_8UC4;
        } else return new Mat();

        Mat m = new Mat(image.getHeight(), image.getWidth(), type);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        if (type == CvType.CV_8UC1 || type == CvType.CV_8UC3) {
            m.put(0, 0, data);
            return m;
        }
        byte[] buf = Arrays.copyOf(data, data.length);
        byte tmp = 0;
        for (int i = 0; i < buf.length; i += 4) {
            tmp = buf[i];
            buf[i] = buf[i + 1];
            buf[i + 1] = buf[i + 2];
            buf[i + 2] = buf[i + 3];
            buf[i + 3] = tmp;
        }
        m.put(0, 0, buf);
        return m;
    }

    public static boolean saveMat(Mat m, String path) {
        if (m == null || m.empty()) return false;
        if (path == null || path.length() < 5 || !path.endsWith(".mat")) return false;

        if (m.depth() == CvType.CV_8U) {
        } else if (m.depth() == CvType.CV_16U) {
            Mat m_16 = new Mat();
            m.convertTo(m_16, CvType.CV_8U, 255.0 / 65535);
        } else if (m.depth() == CvType.CV_32F) {
            Mat m_32 = new Mat();
            m.convertTo(m_32, CvType.CV_8U, 255);
            m = m_32;
        } else return false;

        if (m.channels() == 2 || m.channels() > 4) return false;
        byte[] buf = new byte[m.channels() * m.cols() * m.rows()];
        m.get(0, 0, buf);

        try (
                OutputStream out = new FileOutputStream(path);
                BufferedOutputStream bous = new BufferedOutputStream(out);
                DataOutputStream dout = new DataOutputStream(bous);
        ) {
            dout.writeInt(m.rows());
            dout.writeInt(m.cols());
            dout.writeInt(m.channels());
            dout.write(buf);
            dout.flush();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void showImage(Mat img, String title){
        BufferedImage im = MapToBufferedImage(img);
        if (im == null){
            return;
        }
        int w = 1000;
        int h = 600;
        JFrame window = new JFrame(title);
        window.setSize(w,h);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(im);
        JLabel label = new JLabel(imageIcon);

        JScrollPane pane = new JScrollPane(label);
        window.setContentPane(pane);

        if(im.getWidth() < w && im.getHeight() < h){
            window.pack();
        }
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }


}
