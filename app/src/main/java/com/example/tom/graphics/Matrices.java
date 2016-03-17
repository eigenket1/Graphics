package com.example.tom.graphics;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Tom on 2016-03-12.
 */
public class Matrices {

    public static double TNet[][] = new double[][]{                 {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,   0,   0,   1}};

    public static double IDENTITY[][] = new double[][]{             {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,   0,   0,   1}};

    public static double BASE[][]  = new double[][]{                {   1,   0,   0,   0},
                                                                    {   0,   1,   0,   0},
                                                                    {   0,   0,   1,   0},
                                                                    {   0,   0,   0,   1}};

    public static MyView myView;

    static public void applyToTNet(){
        double temp[][] = new double[4][4];
        for(int i = 0 ;i<4 ; i++){
            for(int j = 0;j<4 ;j++){
                temp[i][j] = TNet[i][0] * BASE[0][j] + TNet[i][1] * BASE[1][j] +
                        TNet[i][2] * BASE[2][j] + TNet[i][3] * BASE[3][j];
            }
        }
        TNet = temp;
    }

    static public void resetBase(){
        for(int i=0; i<BASE.length; i++) {
            for (int j = 0; j < BASE[i].length; j++) {
                BASE[i][j] = IDENTITY[i][j];
            }
        }
    }

    static public void translate(double x, double y, double z){
        resetBase();
        BASE[3][0] = x;
        BASE[3][1] = y;
        BASE[3][2] = z;
        applyToTNet();
    }

    static public void shear(double factor){
        resetBase();
        BASE[1][0] = factor;
        applyToTNet();
    }

    static public void rotate(int first, int second, double radians){
        resetBase();
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        BASE[first][first] = cos;
        BASE[first][second] = sin;
        BASE[second][second] = cos;
        BASE[second][first] = -sin;
        applyToTNet();
    }

    static public void scale(double factor){
        resetBase();
        BASE[0][0] = factor;
        BASE[1][1] = factor;
        BASE[2][2] = factor;
        applyToTNet();
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
        myView.invalidate();
    }
}
