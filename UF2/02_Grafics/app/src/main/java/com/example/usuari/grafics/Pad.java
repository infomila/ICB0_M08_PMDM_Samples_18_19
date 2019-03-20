package com.example.usuari.grafics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import static android.content.Context.SENSOR_SERVICE;

public class Pad extends View implements SensorEventListener, View.OnTouchListener {


    private Vector2D padValue;


    //----- Gravity sensor stuff -----------------------------
    private float yAcceleration;
    private float xAcceleration;
    private boolean gravityEnabled = true;
    //----------------------------------

    private Bitmap mFletxaUp;
    private Bitmap mFletxaDown;
    //----------------------------------
    private int diametre;
    private float radi;
    private float cx;
    private float cy;


    public Pad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        padValue = new Vector2D(0,0);

        //------------------------------------------------------------------------
        // Setup grafic resources
        setupGraficResources();

        //------------------------------------------------------------------------
        // Setup touch events
        setOnTouchListener(this);
        //------------------------------------------------------------------------
        // Setup sensor for gravity detection
        setupGravitySensor();
    }

    public void setGravityEnabled(boolean gravityEnabled) {
        this.gravityEnabled = gravityEnabled;
    }


    private void setupGraficResources() {
        mFletxaUp = BitmapFactory.decodeResource(this.getResources(), R.drawable.arrow);
        Matrix m = new Matrix();
        m.postRotate(180);
        mFletxaDown = Bitmap.createBitmap(mFletxaUp,0,0,
                mFletxaUp.getWidth(), mFletxaUp.getHeight(),
                m,true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#00000000"));//LIME POWER

        Paint p = new Paint();
        p.setColor(Color.parseColor("#ff00ff"));

        diametre = Math.min(getWidth(), getHeight());
        radi = diametre/2;
        cx = getWidth()/2;
        cy = getHeight()/2;
        canvas.drawCircle(cx,cy,radi, p);



        p.setColor(Color.parseColor("#ffffff"));
        float radiPetit = 0.2f * radi;
        canvas.drawCircle(cx,cy,radiPetit, p);
        Rect origen = new Rect(0,0, mFletxaUp.getWidth(), mFletxaUp.getHeight());

        float Warrow = 2*radiPetit;
        float Harrow = 0.9f*(radi-radiPetit);

        //arrowBizarroSystem(canvas, p, cx, cy, radiPetit, origen, Warrow, Harrow);



        for(int angle=0;angle<360; angle+= 90) {
            Matrix m = calculateMatrix(angle, cx, cy, radiPetit, Warrow, Harrow);
            canvas.drawBitmap(mFletxaUp, m, p);
        }
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(2);

        canvas.drawLine(cx,cy, cx+(float)padValue.getX(), cy+(float)padValue.getY(), p);
    }

    private Matrix calculateMatrix(float angle, float cx, float cy, float radiPetit, float warrow, float harrow) {
        Matrix transformacio = new Matrix();
        transformacio.postScale(warrow / mFletxaUp.getWidth(), harrow / mFletxaUp.getHeight() );
        transformacio.postTranslate(-radiPetit, -radiPetit - harrow - 4);
        transformacio.postRotate(angle);
        transformacio.postTranslate(cx, cy);
        return transformacio;
    }

    private void arrowBizarroSystem(Canvas canvas, Paint p, float cx, float cy, float radiPetit, Rect origen, float warrow, float harrow) {
        //------------------------------------------------------------------
        RectF desti = new RectF(
                cx - warrow /2, cy - harrow - radiPetit,
                cx + warrow /2, cy - radiPetit - 4
        );
        canvas.drawBitmap(mFletxaUp, origen, desti,p );
        //------------------------------------------------------------------
        desti = new RectF(
                cx - warrow /2, cy + radiPetit +4,
                cx + warrow /2, cy + radiPetit + harrow
        );
        canvas.drawBitmap(mFletxaDown, origen, desti,p );
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {


        padValue =  new Vector2D(event.getX()-cx, event.getY()-cy);
        //escalem el vector per a que mesuri entre 0 i 10
        padValue.scalarMultiply(10/radi);

        if(event.getAction() == MotionEvent.ACTION_UP) {
            padValue = new Vector2D(0,0);
        }

        return true;
    }

    //-------------------------------------------------------------------------
    //     Region devoted to gravity sensor management
    //-------------------------------------------------------------------------

    private void setupGravitySensor() {
        //-----------------------------------------
        // Setup sensor for gravity detection
        SensorManager sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(!gravityEnabled) return;

        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {

            //Set sensor values as acceleration
            yAcceleration = event.values[1]*10;
            xAcceleration = -event.values[0]*10;

            padValue = new Vector2D(xAcceleration,yAcceleration);

            invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { /*nothing to do*/ }


    public Vector2D getPadValue(){
        return padValue;
    }

}
