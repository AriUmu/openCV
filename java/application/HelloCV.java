package application;

import org.opencv.core.Core;

public class HelloCV {

    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        System.out.println(Core.VERSION);
        System.out.println(Core.VERSION_MAJOR);
        System.out.println(Core.VERSION_MINOR);
        System.out.println(Core.VERSION_REVISION);
        System.out.println(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.getBuildInformation());
    }
}
