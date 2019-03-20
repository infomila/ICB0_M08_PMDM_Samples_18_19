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
import android.graphics.Xfermode;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

public class Scenario extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private static final double VEL_MAXIMA = 100;
    public static final double COEF_FREGAMENT = 0.2;
    //--------------------------------------------------
    // Paràmetres Bola
    private Vector2D ballCoordinate;
    private final static int BALL_RADIUS = 30;
    private Vector2D ballSpeed;
    private Vector2D ballAcceleration;
    //--------------------------------------------------


    private Paint p;
    private Paint pClear;
    private Paint pSourceOver;
    private Paint pSourceIn;

    private ScenarioThread mThread;
    private SurfaceHolder mHolder;
    private Pad mPad;
    private MediaPlayer mMediaPlayer;


    //--------------------------------------------------
    private Bitmap mFalcon;
    private Bitmap mBackground;
    private Bitmap mBackgroundMask;
    private final static double FALCON_RELATIVE_SIZE = 0.08;
    //--------------------------------------------------
    Bitmap mask;
    Bitmap colisionDetectionBitmap;
    Canvas colisionDetecionCanvas;

    public Scenario(Context context, AttributeSet attrs) {
        super(context, attrs);

        mMediaPlayer = MediaPlayer.create(context, R.raw.boing);
        setOnTouchListener(this);


        p = new Paint();
        pClear = new Paint();
        pSourceOver = new Paint();
        pSourceOver .setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        pSourceIn = new Paint();
        pSourceIn.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        pSourceOver.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
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
        //canvas.drawRGB(0,0,0);

        //Rect finestra = new Rect(0,0,getWidth(), getHeight());
        //canvas.drawBitmap(mBackground,finestra, finestra, p);

        canvas.drawBitmap(mBackground,0, 0, pClear);
        canvas.drawBitmap(        mFalcon,
                        (int) ballCoordinate.getX() - mFalcon.getWidth() /2 ,
                        (int) ballCoordinate.getY() - mFalcon.getHeight()/2,
                           pSourceOver );
        //p.setColor(Color.parseColor("#ff0000"));
        //canvas.drawCircle((float)ballCoordinate.getX(), (float)ballCoordinate.getY(), BALL_RADIUS, p);


    }

    private Timer mTimer;
    private TimerTask mTimerTask;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        ballCoordinate = new Vector2D( getWidth()/2, getHeight()/2 );
        ballSpeed = new Vector2D(1,0);//Math.random()*10, Math.random()*10);
        //setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        //--------------------------------------------------------------------------
        // Precarreguem la imatge en un bitmap
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mBackground = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.fons_combinat, opts);
        mBackground = Bitmap.createScaledBitmap(mBackground, getWidth(), getHeight(), true);
        //--------------------------------------------------------------------------
        mBackgroundMask = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.mascara_fons_combinat_ampliat);
        mBackgroundMask = Bitmap.createScaledBitmap(mBackgroundMask, getWidth(), getHeight(), true);


        //--------------------------------------------------------------------------
        mask = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.mascara_fons_combinat);
        colisionDetectionBitmap = mask.copy(Bitmap.Config.ARGB_8888,true);
        colisionDetecionCanvas = new Canvas(colisionDetectionBitmap);



        //--------------------------------------------------------------------------
        mFalcon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.falcon);
        int falconWidth = (int)(getWidth() * FALCON_RELATIVE_SIZE);
        // apliquem la mateixa proporció a l'alçada
        int falconHeight =   (int)(((double)falconWidth/mFalcon.getWidth())  * mFalcon.getHeight());
        mFalcon = Bitmap.createScaledBitmap(mFalcon, falconWidth, falconHeight, true);
        //--------------------------------------------------------------------------
        //- Fil de control de la UI -------------------------------
        mThread = new ScenarioThread(this, mHolder);
        mThread.start();
        //- Timer per controlar la física -------------------------
        mTimer = new Timer();

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                updatePhysics();
            }
        };

        mTimer.scheduleAtFixedRate(mTimerTask, 0,50);

    }

    private void updatePhysics() {

        //-----------------------------
        //    Gestió del moviment
        if(mPad!=null) ballAcceleration = mPad.getPadValue().scalarMultiply(0.08);
        if(ballAcceleration.getNorm()==0) {
            ballAcceleration = ballSpeed.scalarMultiply(-COEF_FREGAMENT);
        }
        ballSpeed = ballSpeed.add(ballAcceleration);
        if(ballSpeed.getNorm()>VEL_MAXIMA) {
            ballSpeed = ballSpeed.normalize().scalarMultiply(VEL_MAXIMA);
        }

        Vector2D futurePosition = ballCoordinate.add( ballSpeed);


        // Busquem si xoca als marges
        boolean xocaX = false, xocaY = false;

        // detecció de col·lisió ....
        xocaX = (futurePosition.getX() - mFalcon.getWidth()/2 < 0 ||  // xoc dreta
                futurePosition.getX() + mFalcon.getWidth()/2 >= getWidth()  // xoc esquerra
        );
        xocaY = (futurePosition.getY() - mFalcon.getHeight()/2 < 0 ||  // xoc dreta
                futurePosition.getY() + mFalcon.getHeight()/2 >= getHeight()  // xoc esquerra
        );


        if (!xocaX && !xocaY) {

            boolean xocaEscenari = verifyScenarioColision(futurePosition);
            if(xocaEscenari) {
                ballSpeed = new Vector2D(  -ballSpeed.getX(),-ballSpeed.getY() );
                //ballCoordinate = ballCoordinate.add(ballSpeed);

            } else {
                // No xoco amb ningú !!!!!
                ballCoordinate = futurePosition;
            }
        } else {
            mMediaPlayer.start();
            ballSpeed = new Vector2D((xocaX ? -1 : 1) * ballSpeed.getX(),
                    (xocaY ? -1 : 1) * ballSpeed.getY());
            //ballCoordinate = ballCoordinate.add(ballSpeed);
        }

    }

    int getPixel(int x, int y, int [] pix, int width){
        return pix[x+y*width];
    }

    private boolean verifyScenarioColision(Vector2D ballCoordinate) {

        int color = mBackgroundMask.getPixel((int)ballCoordinate.getX(),(int)ballCoordinate.getY());
        if(color==Color.BLACK) {
            return true;
        } else if(color==Color.WHITE) {
            return false;
        } else {//if(color==Color.RED) {

            Vector2D falconHalfSize = new Vector2D(mFalcon.getWidth() / 2, mFalcon.getHeight() / 2);
            Vector2D pi = ballCoordinate.subtract(falconHalfSize);
            Vector2D pf = ballCoordinate.add(falconHalfSize);
            int x = 0, y = 0;
            int xF = 0, yF = 0;
            for (x = (int) pi.getX(), xF = 0; x < (int) pf.getX(); x++, xF++) {
                for (y = (int) pi.getY(), yF = 0; y < (int) pf.getY(); y++, yF++) {
                    //"Seguro de vida", si les coordenades estan fora
                    if (x < 0 || y < 0 || x >= mBackgroundMask.getWidth() || y >= mBackgroundMask.getHeight())
                        continue;
                    int falconColor = mFalcon.getPixel(xF, yF);
                    int backColor = mBackgroundMask.getPixel(x, y);
                    if (backColor == Color.BLACK && Color.alpha(falconColor) > 0) {
                        return true;
                    }
                }
            }
            return false;
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mThread!=null) mThread.kill();
        if(mTimer!=null) mTimer.cancel();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ballCoordinate = new Vector2D( event.getX(), event.getY() );

        return false;
    }
}
