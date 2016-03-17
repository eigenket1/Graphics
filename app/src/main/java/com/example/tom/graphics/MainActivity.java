package com.example.tom.graphics;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class MainActivity extends Activity {

    MyView myView;
    final long mInterval = 10;
    Handler mHandler = new Handler();
    Runnable run;

    public static ArrayList<int[]> lines = new ArrayList<int[]>();
    public static ArrayList<double[]> points = new ArrayList<double[]>();
    public static ArrayList<double[]> initialPoints = null;

    public void translateLeft(View v){
        mHandler.removeCallbacks(run);
        Matrices.translate(-75/MyView.scale,0,0);
        Matrices.transform();
    }

    public void translateRight(View v){
        mHandler.removeCallbacks(run);
        Matrices.translate(75/MyView.scale,0,0);
        Matrices.transform();
    }

    public void translateUp(View v){
        mHandler.removeCallbacks(run);
        Matrices.translate(0,35/MyView.scale,0);
        Matrices.transform();
    }

    public void translateDown(View v){
        mHandler.removeCallbacks(run);
        Matrices.translate(0,-35/MyView.scale,0);
        Matrices.transform();
    }

    public void zoomIn(View v){
        mHandler.removeCallbacks(run);
        double x = points.get(0)[0];
        double y = points.get(0)[1];
        double z = points.get(0)[2];
        Matrices.translate(-x, -y, -z);
        Matrices.scale(1.1);
        Matrices.translate(x, y, z);
        Matrices.transform();
    }

    public void zoomOut(View v){
        mHandler.removeCallbacks(run);
        double x = points.get(0)[0];
        double y = points.get(0)[1];
        double z = points.get(0)[2];
        Matrices.translate(-x, -y, -z);
        Matrices.scale(0.9);
        Matrices.translate(x, y, z);
        Matrices.transform();
    }

    public void rotateX(View v){
        mHandler.removeCallbacks(run);
        double x = points.get(0)[0];
        double y = points.get(0)[1];
        double z = points.get(0)[2];
        Matrices.translate(-x, -y, -z);
        Matrices.rotate(1, 2, 0.05);
        Matrices.translate(x, y, z);
        Matrices.transform();
    }

    public void rotateY(View v){
        mHandler.removeCallbacks(run);
        double x = points.get(0)[0];
        double y = points.get(0)[1];
        double z = points.get(0)[2];
        Matrices.translate(-x, -y, -z);
        Matrices.rotate(0, 2, 0.05);
        Matrices.translate(x, y, z);
        Matrices.transform();
    }

    public void rotateZ(View v){
        mHandler.removeCallbacks(run);
        double x = points.get(0)[0];
        double y = points.get(0)[1];
        double z = points.get(0)[2];
        Matrices.translate(-x, -y, -z);
        Matrices.rotate(0, 1, 0.05);
        Matrices.translate(x, y, z);
        Matrices.transform();
    }

    public void shearLeft(View v){
        mHandler.removeCallbacks(run);
        double offset = initialPoints.get(0)[1]*Matrices.TNet[1][1]-points.get(0)[1];
        Matrices.translate(0, offset, 0);
        Matrices.shear(-0.1);
        Matrices.translate(0, -offset, 0);
        Matrices.transform();
    }

    public void shearRight(View v){
        mHandler.removeCallbacks(run);
        double offset = initialPoints.get(0)[1]*Matrices.TNet[1][1]-points.get(0)[1];
        Matrices.translate(0, offset, 0);
        Matrices.shear(0.1);
        Matrices.translate(0, -offset, 0);
        Matrices.transform();
    }

    public void spinX(View v){
        mHandler.removeCallbacks(run);

        run = new Runnable() {
            @Override
            public void run() {
                double x = points.get(0)[0];
                double y = points.get(0)[1];
                double z = points.get(0)[2];
                Matrices.translate(-x, -y, -z);
                Matrices.rotate(1, 2, 0.05);
                Matrices.translate(x, y, z);
                Matrices.transform();
                mHandler.postDelayed(run, mInterval);
            }
        };

        run.run();
    }

    public void spinY(View v){
        mHandler.removeCallbacks(run);

        run = new Runnable() {
            @Override
            public void run() {
                double x = points.get(0)[0];
                double y = points.get(0)[1];
                double z = points.get(0)[2];
                Matrices.translate(-x, -y, -z);
                Matrices.rotate(0, 2, 0.05);
                Matrices.translate(x, y, z);
                Matrices.transform();
                mHandler.postDelayed(run, mInterval);
            }
        };

        run.run();
    }

    public void spinZ(View v){
        mHandler.removeCallbacks(run);

        run = new Runnable() {
            @Override
            public void run() {
                double x = points.get(0)[0];
                double y = points.get(0)[1];
                double z = points.get(0)[2];
                Matrices.translate(-x, -y, -z);
                Matrices.rotate(0, 1, 0.05);
                Matrices.translate(x, y, z);
                Matrices.transform();
                mHandler.postDelayed(run, mInterval);
            }
        };

        run.run();
    }


    public void reset(View v){
        mHandler.removeCallbacks(run);
        Matrices.TNet = Matrices.IDENTITY;
        Matrices.transform();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Matrices.myView = (com.example.tom.graphics.MyView) findViewById(R.id.View);

        int ok[] = new int [] {1,2};
        int ok1[] = new int [] {2,3};
        int ok2[] = new int [] {3,4};
        int ok3[] = new int [] {4,1};
        lines.add(ok);
        lines.add(ok1);
        lines.add(ok2);
        lines.add(ok3);
        double ok4[] = new double [] {5,5,0};
        double ok5[] = new double [] {0,0,0};
        double ok6[] = new double [] {0,10,0};
        double ok7[] = new double [] {10,10,0};
        double ok8[] = new double [] {10,0,0};
        points.add(ok4);
        points.add(ok5);
        points.add(ok6);
        points.add(ok7);
        points.add(ok8);
        initialPoints = points;

        if(initialPoints == null) {
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/lines.txt");

            try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                while(true){
                    int line[] = new int[2];
                    StringTokenizer tok = new StringTokenizer(in.readLine());
                    line[0] = Integer.parseInt(tok.nextToken());
                    if(!tok.hasMoreTokens()){
                        break;
                    }
                    line[1] = Integer.parseInt(tok.nextToken());
                    lines.add(line);
                }

                file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/points.txt");
                in = new BufferedReader(new FileReader(file));
                while(true){
                    double point[] = new double[3];
                    StringTokenizer tok = new StringTokenizer(in.readLine());
                    point[0] = Double.parseDouble(tok.nextToken());
                    if(!tok.hasMoreTokens()){
                        break;
                    }
                    point[1] = Double.parseDouble(tok.nextToken());
                    point[2] = Double.parseDouble(tok.nextToken());
                    points.add(point);
                }
                initialPoints = points;

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}