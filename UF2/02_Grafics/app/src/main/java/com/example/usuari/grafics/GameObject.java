package com.example.usuari.grafics;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface GameObject {

    void updatePhysics();

    void dibuixa(Canvas c);

    Rect getBoundingBox();
}
