package com.example.usuari.grafics;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ScenarioThread extends  Thread {

    private Scenario mScenario;
    private SurfaceHolder mHolder;
    private boolean isAlive = true;

    public ScenarioThread(Scenario s) {
        mScenario = s;
        mHolder = s.getHolder();
    }

    public void kill() {
        isAlive = false;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        super.run();

        while(isAlive) {

            Canvas c=null;
            try {
                c = mHolder.lockCanvas();
                mScenario.onDraw(c);
            } finally {
                // IMPORTANT!! Alliberem el Canvas
                mHolder.unlockCanvasAndPost(c);
            }

        }
    }
}
