package com.example.usuari.grafics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Item implements GameObject
{
    private Vector2D mPosition;
    private Bitmap mBitmap;
    private ItemTouchListener mListener;
    private Paint mP;

    public Item(ItemTouchListener pListener, Vector2D pPosition, Bitmap pBitmap) {
        this.mPosition =    pPosition;
        this.mBitmap =      pBitmap;
        this.mListener =    pListener;
        mP = new Paint();
        mP.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
    }

    @Override
    public void updatePhysics() {

    }

    @Override
    public void dibuixa(Canvas c) {

        Vector2D bitmapPos =
                mPosition.subtract(new Vector2D(mBitmap.getWidth()/2, mBitmap.getHeight()/2));

        c.drawBitmap(mBitmap, (int)bitmapPos.getX(), (int)bitmapPos.getY(), mP );
    }

    @Override
    public Rect getBoundingBox() {
        Vector2D falconHalfSize = new Vector2D(mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        Vector2D pi = mPosition.subtract(falconHalfSize);
        Vector2D pf = mPosition.add(falconHalfSize);
        return new Rect((int)pi.getX(), (int)pi.getY(), (int)pf.getX(), (int)pf.getY());
    }

    public interface ItemTouchListener {
        public void onItemTouched(Item i);
    }

}
