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
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Pad extends View {

    private Bitmap mFletxaUp;
    private Bitmap mFletxaDown;

    public Pad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

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

        canvas.drawColor(Color.parseColor("#00ff00"));//LIME POWER

        Paint p = new Paint();
        p.setColor(Color.parseColor("#ff00ff"));

        int diametre = Math.min(getWidth(), getHeight());
        float radi = diametre/2;
        float cx = getWidth()/2;
        float cy = getHeight()/2;
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
}
