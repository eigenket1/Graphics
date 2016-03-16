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

    public static ArrayList<int[]> lines = new ArrayList<int[]>();
    public static ArrayList<double[]> points = new ArrayList<double[]>();
    public static ArrayList<double[]> initialPoints = null;

    public void translateLeft(View v){
        Matrices.applyToTNet(Matrices.TRANSLATELEFT);
        myView.invalidate();
    }

    public void translateRight(View v){
        Matrices.applyToTNet(Matrices.TRANSLATERIGHT);
        myView.invalidate();
    }

    public void translateUp(View v){
        Matrices.applyToTNet(Matrices.TRANSLATEUP);
        myView.invalidate();
    }

    public void translateDown(View v){
        Matrices.applyToTNet(Matrices.TRANSLATEDOWN);
        myView.invalidate();
    }

    public void zoomIn(View v){
        Matrices.ZOOMIN[3][0] = -points.get(0)[0]/10;
        Matrices.ZOOMIN[3][1] = -points.get(0)[1]/10;
        Matrices.ZOOMIN[3][2] = -points.get(0)[2]/10;
        Matrices.applyToTNet(Matrices.ZOOMIN);
        myView.invalidate();
    }

    public void zoomOut(View v){
        Matrices.ZOOMOUT[3][0] = points.get(0)[0]/10;
        Matrices.ZOOMOUT[3][1] = points.get(0)[1]/10;
        Matrices.ZOOMOUT[3][2] = points.get(0)[2]/10;
        Matrices.applyToTNet(Matrices.ZOOMOUT);
        myView.invalidate();
    }

    public void rotateX(View v){
        Matrices.ROTATEX[3][1] = -points.get(0)[1]*(Matrices.COS-1)-
                -points.get(0)[2]*Matrices.SIN;
        Matrices.ROTATEX[3][2] = -points.get(0)[1]*(Matrices.SIN)+
                -points.get(0)[2]*(Matrices.COS-1);
        Matrices.applyToTNet(Matrices.ROTATEX);
        myView.invalidate();
    }

    public void rotateY(View v){
        Matrices.ROTATEY[3][0] = -points.get(0)[0]*(Matrices.COS-1)-
                -points.get(0)[2]*Matrices.SIN;
        Matrices.ROTATEY[3][2] = -points.get(0)[0]*(Matrices.SIN)+
                -points.get(0)[2]*(Matrices.COS-1);
        Matrices.applyToTNet(Matrices.ROTATEY);
        myView.invalidate();
    }

    public void rotateZ(View v){
        Matrices.ROTATEZ[3][0] =- points.get(0)[0]*(Matrices.COS-1)-
                -points.get(0)[1]*Matrices.SIN;
        Matrices.ROTATEZ[3][1] = -points.get(0)[0]*(Matrices.SIN)+
                -points.get(0)[1]*(Matrices.COS-1);
        Matrices.applyToTNet(Matrices.ROTATEZ);
        myView.invalidate();
    }

    public void shearLeft(View v){
        Matrices.SHEARLEFT[3][0]= (initialPoints.get(0)[1]*Matrices.TNet[1][1]-points.get(0)[1])*-0.1;
        Matrices.applyToTNet(Matrices.SHEARLEFT);
        myView.invalidate();
    }

    public void shearRight(View v){
        Matrices.SHEARRIGHT[3][0]= (initialPoints.get(0)[1]*Matrices.TNet[1][1]-points.get(0)[1])*0.1;
        Matrices.applyToTNet(Matrices.SHEARRIGHT);
        myView.invalidate();
    }

    boolean Xon = false;
    public void spinX(View v){
        if(!Xon) {
            Xrun.run();
        }else{
            mHandler.removeCallbacks(Xrun);
        }
        Xon = !Xon;
    }

    Runnable Xrun = new Runnable() {
        @Override
        public void run() {
            rotateX(myView);
            mHandler.postDelayed(Xrun, mInterval);
        }
    };

    boolean Yon = false;
    public void spinY(View v){
        if(!Yon) {
            Yrun.run();
        }else{
            mHandler.removeCallbacks(Yrun);
        }
        Yon = !Yon;
    }

    Runnable Yrun = new Runnable() {
        @Override
        public void run() {
            rotateY(myView);
            mHandler.postDelayed(Yrun, mInterval);
        }
    };

    boolean Zon = false;
    public void spinZ(View v){
        if(!Zon) {
            Zrun.run();
        }else{
            mHandler.removeCallbacks(Zrun);
        }
        Zon = !Zon;
    }

    Runnable Zrun = new Runnable() {
        @Override
        public void run() {
            rotateZ(myView);
            mHandler.postDelayed(Zrun, mInterval);
        }
    };

    public void reset(View v){
        mHandler.removeCallbacks(Xrun);
        mHandler.removeCallbacks(Yrun);
        mHandler.removeCallbacks(Zrun);
        Xon = false;
        Yon = false;
        Zon = false;
        Matrices.TNet = Matrices.IDENTITY;
        myView.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        myView = (com.example.tom.graphics.MyView) findViewById(R.id.View);

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
                initialPoints = (ArrayList<double[]>)points.clone();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}