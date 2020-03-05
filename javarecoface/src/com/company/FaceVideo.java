package com.company;


import org.apache.commons.io.IOUtils;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


import java.io.File;

/**
 * @Title: Opencv 图片人脸识别、实时摄像头人脸识别、视频文件人脸识别
 */
public class FaceVideo {

    // 初始化人脸探测器
    static CascadeClassifier faceDetector;

    static final File xmlFile = new File("src/resources/haarcascade_frontalface_alt.xml");
    private static final String xmlFilePath = xmlFile.getAbsolutePath();

    static final File dllFile = new File("src/resources/dll/opencv_java411.dll");


    static {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//环境变量中
        System.load(dllFile.getAbsolutePath());//绝对路径
        faceDetector = new CascadeClassifier(xmlFilePath);
    }


    public static void main(String[] args) {
        System.out.println(xmlFilePath);
        face();
    }

    /**
     * OpenCV-4.1.1 图片人脸识别
     */
    public static void face() {
        // 2 读取测试图片
        String imgPath = "G:\\MyFiles\\face.jpg";
        Mat image = Imgcodecs.imread(imgPath);
        if (image.empty()) {
            System.out.println("image 内容不存在！");
            return;
        }
        // 3 特征匹配
        MatOfRect face = new MatOfRect();
        faceDetector.detectMultiScale(image, face);
        // 4 匹配 Rect 矩阵 数组
        Rect[] rects = face.toArray();
        System.out.println("匹配到 " + rects.length + " 个人脸");
        // 5 为每张识别到的人脸画一个圈
        int i = 1;
        for (Rect rect : face.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
            imageCut(imgPath, "G:\\MyFiles\\Pictures\\" + i + ".jpg", rect.x, rect.y, rect.width, rect.height);// 进行图片裁剪
            i++;
        }
        // 6 展示图片
        HighGui.imshow("人脸识别", image);
        HighGui.waitKey(0);
    }

    /**
     * 裁剪人脸
     *
     * @param imagePath
     * @param outFile
     * @param posX
     * @param posY
     * @param width
     * @param height
     */
    public static void imageCut(String imagePath, String outFile, int posX, int posY, int width, int height) {
        // 原始图像
        Mat image = Imgcodecs.imread(imagePath);
        // 截取的区域：参数,坐标X,坐标Y,截图宽度,截图长度
        Rect rect = new Rect(posX, posY, width, height);
        // 两句效果一样
        Mat sub = image.submat(rect); // Mat sub = new Mat(image,rect);
        Mat mat = new Mat();
        Size size = new Size(width, height);
        Imgproc.resize(sub, mat, size);// 将人脸进行截图并保存
        Imgcodecs.imwrite(outFile, mat);
        System.out.println(String.format("图片裁切成功，裁切后图片文件为： %s", outFile));
    }


}

