package com.yuran.fittingroomapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuran on 10.12.2015.
 */
public class Detection {

    public static final int VIEW_NONE = -1;
    public static final int VIEW_SIDE = 0;
    public static final int VIEW_TOP = 1;

    public static  double length = 0.0;
    public static  double width = 0.0;
    public static  double instep = 0.0;
    public static  double debug_value = 0.0;

    private static  double length_relative = 0.0;
    private static  double width_relative = 0.0;
    private static  double instep_relative = 0.0;

    private Point[] base = new Point[2];
    private double side_scale = 1.0;
    private double top_scale = 1.0;

    public int method_type = 1;

    long H_MIN = 0;
    long H_MAX = 180;
    long S_MIN = 0;
    long S_MAX = 255;
    long V_MIN = 0;
    long V_MAX = 255;
    // white color range in HSV
    private long color_sens_white = 80;
    private Scalar lower_white = new Scalar(0,0,255-color_sens_white);
    private Scalar upper_white = new Scalar(255,color_sens_white,255);
    // black color range in HSV
    private long color_sens_black = 50;
    private Scalar lower_black = new Scalar(0,0,0);
    private Scalar upper_black = new Scalar(255,255,color_sens_black);
/*
    public Bitmap footDetect ( Bitmap bitmap_input, int type ) {
        Mat input = new Mat();
        Mat output = new Mat();
        Point[] rect_points = new Point[4];
        for(int i = 0;i<4;i++)
            rect_points[i] = new Point();

        Utils.bitmapToMat(bitmap_input, output);
        Utils.bitmapToMat(bitmap_input, input);
        // Переконвертируем матрицу с RGB на градацию серого
        Mat src_white = new Mat();
        Mat src_grey = new Mat();
        Mat detected_edges = new Mat();
        src_white.create(input.size(), CvType.CV_8UC1);
        src_grey.create(input.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(input, src_white, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(input, src_grey, Imgproc.COLOR_RGB2HSV);

        Core.inRange(src_white, lower_white, upper_white, src_white);
        Imgproc.blur(src_white, detected_edges, new Size(3, 3));
        Imgproc.Canny(detected_edges, detected_edges, 0, 200);

        Mat hierarchy = new Mat();
        List<MatOfPoint> contours_hull = new ArrayList<MatOfPoint>();
        Imgproc.findContours(detected_edges, contours_hull, hierarchy, Imgproc.RETR_TREE, Imgproc.CV_CLOCKWISE, new Point(0, 0));  // CV_CHAIN_APPROX_SIMPLE or CV_CLOCKWISE
        Rect boundRect;
        int idxMainCont = findLongestContour(contours_hull);
        boundRect = Imgproc.boundingRect(contours_hull.get(idxMainCont));
        Imgproc.rectangle(output, boundRect.tl(), boundRect.br(), new Scalar(0, 255, 0), 2, 8, 0);

        Mat detected_edges_b = new Mat();
        Mat hierarchy_b = new Mat();
        List<MatOfPoint> contours_hull_b = new ArrayList<MatOfPoint>();
        Rect boundRect_b;

        Core.inRange(src_grey, lower_black, upper_black, src_grey);
        Imgproc.blur(src_grey, detected_edges_b, new Size(3, 3));
        Imgproc.Canny(detected_edges_b, detected_edges_b, 0, 200);

        Imgproc.findContours(detected_edges_b, contours_hull_b, hierarchy_b, Imgproc.RETR_TREE, Imgproc.CV_CLOCKWISE, new Point(0, 0));  // CV_CHAIN_APPROX_SIMPLE or CV_CLOCKWISE
        int idxMainCont_b = findLongestContour(contours_hull_b);
        boundRect_b = Imgproc.boundingRect(contours_hull_b.get(idxMainCont_b));
        Imgproc.rectangle(output, boundRect_b.tl(), boundRect_b.br(), new Scalar(255, 0, 0), 2, 8, 0);

        Scalar fontColor = new Scalar(0, 0, 0);
        Point fontPoint = new Point();
        fontPoint.x = 15;
        fontPoint.y = bitmap_input.getHeight() - 20;
        String text = "";
        double value;
        if(type == 0) {
            value = 297.0 / boundRect.width * boundRect_b.width;
            text = "Size:" + String.valueOf(value);
        }
        else {
            value = 297.0 / boundRect.width * boundRect_b.width;
            //text = "Width:" + String.valueOf(value);
        }

        Imgproc.putText(output,text,
                fontPoint, Core.FONT_HERSHEY_PLAIN, 2, fontColor,
                2, Core.LINE_AA, false);
        Utils.matToBitmap(output, bitmap_input);

        input.release();
        output.release();
        src_grey.release();
        src_white.release();
        detected_edges.release();
        detected_edges_b.release();
        hierarchy.release();
        hierarchy_b.release();

        return bitmap_input;
    }
 */

    public Mat primaryProcessing( Mat rgba_frame ){
        // Переконвертируем матрицу с RGB на градацию серого
        Mat src_grey = new Mat();
        Mat detected_edges = new Mat();
        src_grey.create(rgba_frame.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(rgba_frame, src_grey, Imgproc.COLOR_RGB2HSV);

        Core.inRange(src_grey, lower_black, upper_black, src_grey);
        Imgproc.blur(src_grey, detected_edges, new Size(3, 3));
        Imgproc.Canny(detected_edges, detected_edges, 0, 200);

        src_grey.release();
        detected_edges.release();

        return detected_edges;
    }

    // returns rectangle which bounds sheet side, and it also set static var scale
    public Rect sheetDetect ( Mat rgba_frame, int view_type ){

        Point[] rect_points = new Point[4];
        for(int i = 0;i<4;i++)
            rect_points[i] = new Point();

        // Переконвертируем матрицу с RGB на градацию серого
        Rect roi = drawSheet(rgba_frame,view_type);
        Mat rgba_frame_roi = new Mat(rgba_frame,roi);
        Mat src = new Mat();
        Mat detected_edges = new Mat();
        src.create(rgba_frame_roi.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(rgba_frame_roi, src, Imgproc.COLOR_RGB2HSV);

        Core.inRange(src, lower_white, upper_white, src);
        Imgproc.blur(src, detected_edges, new Size(3, 3));
        Imgproc.Canny(detected_edges, detected_edges, 0, 200);

        Mat hierarchy = new Mat();
        List<MatOfPoint> contours_hull = new ArrayList<MatOfPoint>();

        Imgproc.findContours(src, contours_hull, hierarchy, Imgproc.RETR_TREE, Imgproc.CV_CLOCKWISE, new Point(0, 0));  // CV_CHAIN_APPROX_SIMPLE or CV_CLOCKWISE

        src.release();
        detected_edges.release();

        if(contours_hull.size()==0)
            return null;

        int idxMainCont = findLongestContour(contours_hull);
        Rect boundRect;
        boundRect = Imgproc.boundingRect(contours_hull.get(idxMainCont));
        Rect result = new Rect(boundRect.x+roi.x,boundRect.y+roi.y,boundRect.width,boundRect.height);

        if(view_type == Detection.VIEW_SIDE)
            side_scale = 297.0 / boundRect.width;
        else if(view_type == Detection.VIEW_TOP)
            top_scale = 297.0 / boundRect.width;
        else
            debug_value = 297.0 / roi.width * boundRect.width;

        hierarchy.release();

        return result;

    }

    public Rect footDetect ( Mat rgba_frame, int type_view){

        Point[] rect_points = new Point[4];
        for(int i = 0;i<4;i++)
            rect_points[i] = new Point();

        // Переконвертируем матрицу с RGB на градацию серого
        Rect roi = drawSheet(rgba_frame,type_view);
        Mat rgba_frame_roi = new Mat(rgba_frame,roi);
        Mat src = new Mat();
        Mat detected_edges = new Mat();
        src.create(rgba_frame_roi.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(rgba_frame_roi, src, Imgproc.COLOR_RGB2HSV);

        Core.inRange(src, lower_black, upper_black, src);
        Imgproc.blur(src, detected_edges, new Size(3, 3));
        Imgproc.Canny(detected_edges, detected_edges, 0, 200);


        Mat hierarchy = new Mat();
        List<MatOfPoint> contours_hull = new ArrayList<MatOfPoint>();

        Imgproc.findContours(src, contours_hull, hierarchy, Imgproc.RETR_TREE, Imgproc.CV_CLOCKWISE, new Point(0, 0));  // CV_CHAIN_APPROX_SIMPLE or CV_CLOCKWISE

        src.release();
        detected_edges.release();

        if(contours_hull.size()==0)
            return null;

        int idxMainCont = findLongestContour(contours_hull);
        Rect boundRect;
        boundRect = Imgproc.boundingRect(contours_hull.get(idxMainCont));
        Rect result = new Rect(boundRect.x+roi.x,boundRect.y+roi.y,boundRect.width,boundRect.height);

        if(type_view == Detection.VIEW_SIDE)
            length_relative = boundRect.width;
        else if(type_view == Detection.VIEW_TOP)
            width_relative = boundRect.height;
        else
            Detection.debug_value = 297.0 / roi.width * boundRect.width;

        hierarchy.release();

        return result;
    }

    public double updateLength(Mat frame, int view_type){
        footDetect(frame, view_type);
        if(method_type == 0){
            length = 297.0 / drawSheet(frame,view_type).width * length_relative;
        }else
        {
            sheetDetect(frame,view_type);
            length = side_scale * length_relative;
        }
        return length;
    }

    public double updateWidth(Mat frame, int view_type){
        footDetect(frame, view_type);
        if(method_type == 0){
            width = 297.0 / drawSheet(frame,view_type).width * width_relative;
        }else
        {
            sheetDetect(frame,view_type);
            width = top_scale * width_relative;
        }
        return width;
    }

/*
    public Bitmap footDetectMain ( Bitmap bitmap_input ) {
        Mat input = new Mat();
        Mat output = new Mat();
        Point[] rect_points = new Point[4];
        for(int i = 0;i<4;i++)
            rect_points[i] = new Point();

        Utils.bitmapToMat(bitmap_input, output);
        Utils.bitmapToMat(bitmap_input, input);
        // Переконвертируем матрицу с RGB на градацию серого
        Mat src_gray = new Mat();
        Mat detected_edges = new Mat();
        src_gray.create(input.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(input, src_gray, Imgproc.COLOR_RGB2HSV);
        Core.inRange(src_gray, lower_white, upper_white, src_gray);
        Imgproc.blur(src_gray, detected_edges, new Size(3, 3));
        Imgproc.Canny(detected_edges, detected_edges, 0, 200);


        //Imgproc.cvtColor(input, src_gray, Imgproc.COLOR_RGB2GRAY);
        //Imgproc.blur(src_gray, detected_edges, new Size(3, 3));
        //double otsu_thresh_val = Imgproc.threshold(detected_edges, src_gray, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        //double high_thresh_val  = otsu_thresh_val;
        //double lower_thresh_val = otsu_thresh_val * 0.5;
        //Imgproc.Canny(detected_edges, detected_edges, lower_thresh_val, high_thresh_val);
        Imgproc.Canny(detected_edges, detected_edges, 0, 200);

        Mat hierarchy = new Mat();
        List<MatOfPoint> contours_hull = new ArrayList<MatOfPoint>();
        Imgproc.findContours(detected_edges, contours_hull, hierarchy, Imgproc.RETR_TREE, Imgproc.CV_CLOCKWISE, new Point(0, 0));  // CV_CHAIN_APPROX_SIMPLE or CV_CLOCKWISE
        MatOfInt hullsI = new MatOfInt();
        RotatedRect minRect;
        MatOfPoint2f contours_poly = new MatOfPoint2f();
        Rect boundRect;
        MatOfPoint appCurve;
        MatOfPoint2f curve;
        int idxMainCont = findLongestContour(contours_hull);
        boundRect = Imgproc.boundingRect(contours_hull.get(idxMainCont));
        //Imgproc.rectangle(output, boundRect.tl(), boundRect.br(), new Scalar(0, 255, 0), 2, 8, 0);

        for (int i = 0; i < contours_hull.size(); i++) {
                 //curve = new MatOfPoint2f(contours_hull.get(i).toArray());
            //Imgproc.approxPolyDP(curve, contours_poly, 3, true);
            //appCurve = new MatOfPoint(contours_poly.toArray());
            //boundRect = Imgproc.boundingRect(appCurve);
            boundRect = Imgproc.boundingRect(contours_hull.get(i));
            Imgproc.rectangle(output, boundRect.tl(), boundRect.br(), new Scalar(0, 255, 0), 2, 8, 0);
                 //int idxMainCont = findLongestContour(contours_hull);
            //Imgproc.convexHull(contours_hull.get(i), hullsI, false);
            //minRect = Imgproc.minAreaRect(curve);
            //minRect.points(rect_points);
            //updateBase(rect_points);
            //Imgproc.drawContours(output, contours_hull, i, new Scalar(255, 0, 0), 2, 8, hierarchy, 0, new Point());
            //for (int j = 0; j < 4; j++)
            //    Imgproc.line(output,rect_points[j],rect_points[(j + 1) % 4], new Scalar(255,255,0),2);
        }

        Scalar fontColor = new Scalar(0, 0, 0);
        Point fontPoint = new Point();
        fontPoint.x = 15;
        fontPoint.y = bitmap_input.getHeight() - 20;
        Imgproc.putText(output,
                "Width:" + boundRect.width+" Height:" + boundRect.height,
                fontPoint, Core.FONT_HERSHEY_PLAIN, 4, fontColor,
                2, Core.LINE_AA, false);
        Utils.matToBitmap(output, bitmap_input);

        return bitmap_input;

    }
*/
    private int findBiggestContour(List<MatOfPoint> contours)
    {
        int indexOfBiggestContour = -1;
        long sizeOfBiggestContour = 0;

        for (int i = 0; i < contours.size(); i++){
            if(contours.get(i).total() > sizeOfBiggestContour){
                sizeOfBiggestContour = contours.get(i).total();
                indexOfBiggestContour = i;
            }
        }
        return indexOfBiggestContour;
    }

    private int findLongestContour(List<MatOfPoint> in_conts)
    {
        if(in_conts.size()==0)
            return  -1;

        MatOfPoint2f curve;
        if (in_conts.size() == 1)
            return 0;
        double max_len, curr_len = 0.0;
        int i_max = 0;

        curve = new MatOfPoint2f(in_conts.get(0).toArray());
        max_len = Imgproc.arcLength(curve,false);

        for (int i = 0; i < in_conts.size(); i++)
        {
            curve = new MatOfPoint2f(in_conts.get(i).toArray());
            curr_len = Imgproc.arcLength(curve,false);
            if (curr_len > max_len){
                max_len = curr_len;
                i_max = i;
            }
        }

        curve.release();

        return i_max;
    }
/*
    public void showFeetInfo(Mat output){
        double x, y;
        x = base[1].x - base[0].x;
        y = base[1].y - base[0].y;
        double image_len = Math.sqrt(x * x + y * y);
        double real_size = scale * image_len;
        foot_size = real_size;

        String info = "Size = " + String.format("%1.2f",real_size) + " mm";
        Imgproc.putText(output, info, base[0], 0, 0.7, new Scalar(80.0, 200.0, 80.0), 1);
    }
*/
    public void updateBase(Point[] rect_points)
    {
        double max_val = rect_points[0].y;
        int i1 = 0;
        for (int i = 1; i < 4; i++)
        {
            if (rect_points[i].y > max_val){
                max_val = rect_points[i].y;
                i1 = i;
            }
        }
        int i2 = rect_points[(i1 + 1) % 4].y > rect_points[(i1 + 3) % 4].y ? (i1 + 1) % 4 : (i1 + 3) % 4;
        if (rect_points[i1].x > rect_points[i2].x)
        {
            int i0 = i1;
            i1 = i2;
            i2 = i0;
        }

        // set base line
        base[0] = rect_points[i1];
        base[1] = rect_points[i2];

    }

    public boolean detect(Point[] rect_points)
    {
        double sum_x = 0.0;
        double sum_y = 0.0;
        for (int i = 0; i < 2; i++)
        {
            sum_x += Math.abs(rect_points[i + 1].x - rect_points[i].x);
            sum_y += Math.abs(rect_points[i + 1].y - rect_points[i].y);
        }
        if ((700 > sum_x) && (sum_x > 300.0))
            return true;
        return false;
    }

    public Rect drawSheet(Mat frame, int view_type)
    {
        Rect rect;
        if(view_type == VIEW_SIDE)
            rect = new Rect(frame.cols()/8,frame.rows()/4,3*frame.cols()/4,frame.rows()/2);
        else if(view_type == VIEW_TOP )
            rect = new Rect(frame.cols()/4,frame.rows()/4,frame.cols()/2,frame.rows()/2);
        else
            rect = new Rect(frame.cols()/4,frame.rows()/4,frame.cols()/2,frame.rows()/2);
        return rect;
    }

}
