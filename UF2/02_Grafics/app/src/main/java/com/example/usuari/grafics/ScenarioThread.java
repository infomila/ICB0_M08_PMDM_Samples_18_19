package com.example.usuari.grafics;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class ScenarioThread extends  Thread {

    private Scenario mScenario;
    private SurfaceHolder mHolder;
    private boolean isAlive = true;

    public ScenarioThread(Scenario s, SurfaceHolder pHolder) {
        mScenario = s;
        mHolder   = pHolder;
    }

    public void kill() {
        isAlive = false;
    }


    @Override
    public void run() {
        super.run();

        while(isAlive) {

            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                // ignore
            }

            if(!mHolder.getSurface().isValid()) break;

            Canvas c=null;
            try {
                if (mHolder != null) {
                    c = mHolder.lockCanvas();
                    if (c != null) {
                        Log.d("FIL", "" + c);
                        synchronized (mHolder) {
                            mScenario.dibuixa(c);
                            //c.drawRGB(255,0,255);
                        }
                    }
                }
            } catch(Exception ex) {
              Log.e("ERROR", "error en el fil de draw", ex);

            } finally {

                // IMPORTANT!! Alliberem el Canvas
                if(mHolder.getSurface().isValid()) mHolder.unlockCanvasAndPost(c);
            }
        }
    }
}
