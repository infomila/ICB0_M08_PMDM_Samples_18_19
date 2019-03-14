package com.example.usuari.grafics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Scenario extends SurfaceView implements SurfaceHolder.Callback {

    private PointF ballCoordinate;
    private final static int BALL_RADIUS = 30;
    private Bitmap mBackground;
    private Paint p;
    private ScenarioThread mThread;



    public Scenario(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Ens registrem al callback de creació, canvi  i destrucció de la Surface
        this.getHolder().addCallback(this);

        // Precarreguem la imatge en un bitmap
        mBackground = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.wall);

        p = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect finestra = new Rect(0,0,getWidth(), getHeight());
        canvas.drawBitmap(mBackground,finestra, finestra, p);

        p.setColor(Color.parseColor("#ffffff"));
        canvas.drawCircle(ballCoordinate.x, ballCoordinate.y, BALL_RADIUS, p);
    }

    @SuppressLint("WrongCall")
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ballCoordinate = new PointF( getWidth()/2, getHeight()/2 );

        mThread = new ScenarioThread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mThread!=null) mThread.kill();
    }
}
