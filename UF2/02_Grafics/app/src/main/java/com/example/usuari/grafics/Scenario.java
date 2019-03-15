package com.example.usuari.grafics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Scenario extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    //--------------------------------------------------
    // Paràmetres Bola
    private Vector2D ballCoordinate;
    private final static int BALL_RADIUS = 30;
    private Vector2D ballSpeed;
    //--------------------------------------------------

    private Bitmap mBackground;
    private Paint p;
    private ScenarioThread mThread;
    private SurfaceHolder mHolder;
    private Pad mPad;


    public Scenario(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        ballCoordinate = new Vector2D( getWidth()/2, getHeight()/2 );

        setOnTouchListener(this);

        // Precarreguem la imatge en un bitmap
        mBackground = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.wall);

        p = new Paint();

        // Ens registrem al callback de creació, canvi  i destrucció de la Surface
        mHolder = this.getHolder();
        mHolder.addCallback(this);


    }
    int x=0;

    public void setPad(Pad pPad) {
        mPad = pPad;
    }

    public void dibuixa(Canvas canvas) {

        //---------------------------------------------------------------
        // IMPORTANT: CAL Esborrar escenari : tenim dos formes
        // canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        // canvas.drawRGB(0,0,0);
        canvas.drawRGB(0,0,0);

        Rect finestra = new Rect(0,0,getWidth(), getHeight());
        canvas.drawBitmap(mBackground,finestra, finestra, p);

        p.setColor(Color.parseColor("#ff0000"));
        canvas.drawCircle((float)ballCoordinate.getX(), (float)ballCoordinate.getY(), BALL_RADIUS, p);

        // sumar el vector velocitat a la posició de la pilota
        if(mPad!=null) ballSpeed = mPad.getPadValue();
        ballCoordinate = ballCoordinate.add( ballSpeed);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        ballCoordinate = new Vector2D( getWidth()/2, getHeight()/2 );
        ballSpeed = new Vector2D(1,0);//Math.random()*10, Math.random()*10);

        mThread = new ScenarioThread(this, mHolder);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mThread!=null) mThread.kill();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ballCoordinate = new Vector2D( event.getX(), event.getY() );

        return false;
    }
}
