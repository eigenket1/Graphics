package com.example.tom.graphics;

import java.util.ArrayList;

/**
 * Created by Tom on 2016-03-12.
 */
public class Matrices {
    final static double COS = Math.cos(0.05);
    final static double SIN = Math.sin(0.05);

    public static double TNet[][] = new double[][]{                 {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,   0,   0,   1}};

    public static double IDENTITY[][] = new double[][]{       {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,   0,   0,   1}};

    public static double TRANSLATELEFT[][] = new double[][]{  {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {-7.5,   0,   0,   1}};

    public static double TRANSLATERIGHT[][] = new double[][]{ {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    { 7.5,   0,   0,   1}};

    public static double TRANSLATEUP[][] = new double[][]{    {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0, 3.5,   0,   1}};

    public static double TRANSLATEDOWN[][] = new double[][]{  {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,-3.5,   0,   1}};

    public static double ZOOMIN[][] = new double[][]{               { 1.1,   0,   0,   0},
                                                                    {   0, 1.1,   0,   0},
                                                                    {   0,   0, 1.1,   0},
                                                                    {   0,   0,   0,   1}};

    public static double ZOOMOUT[][] = new double[][]{              { 0.9,   0,   0,   0},
                                                                    {   0, 0.9,   0,   0},
                                                                    {   0,   0, 0.9,   0},
                                                                    {   0,   0,   0,   1}};

    public static double ROTATEX[][] = new double[][]{              {   1,   0,   0,   0},
                                                                    {   0, COS, SIN,   0},
                                                                    {   0, -SIN, COS,   0},
                                                                    {   0,   0,   0,   1}};

    public static double ROTATEY[][] = new double[][]{              { COS,   0, SIN,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {-SIN,   0, COS,   0},
                                                                    {   0,   0,   0,   1}};

    public static double ROTATEZ[][] = new double[][]{              { COS, SIN,   0,   0},
                                                                    {-SIN, COS,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,   0,   0,   1}};

    public static double SHEARLEFT[][] = new double[][]{            {   1,   0,   0,   0},
                                                                    {-0.1,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,   0,   0,   1}};

    public static double SHEARRIGHT[][] = new double[][]{           {   1,   0,   0,   0},
                                                                    { 0.1,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,   0,   0,   1}};

    static public void applyToTNet(double[][] arg){
        double temp[][] = new double[4][4];
        for(int i = 0 ;i<4 ; i++){
            for(int j = 0;j<4 ;j++){
                temp[i][j] = TNet[i][0] * arg[0][j] + TNet[i][1] * arg[1][j] +
                        TNet[i][2] * arg[2][j] + TNet[i][3] * arg[3][j];
            }
        }
        TNet = temp;
    }

    static public void transform(){
        ArrayList<double[]> temp = new ArrayList<double[]>();

        for(double[] point :MainActivity.initialPoints){
            double[] newPoint = new double[3];
            newPoint[0] = point[0]*TNet[0][0] + point[1]*TNet[1][0] + point[2]*TNet[2][0] + 1*TNet[3][0];
            newPoint[1] = point[0]*TNet[0][1] + point[1]*TNet[1][1] + point[2]*TNet[2][1] + 1*TNet[3][1];
            newPoint[2] = point[0]*TNet[0][2] + point[1]*TNet[1][2] + point[2]*TNet[2][2] + 1*TNet[3][2];
            temp.add(newPoint);
        }
        MainActivity.points = temp;

    }
}
