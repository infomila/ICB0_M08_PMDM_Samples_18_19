package com.example.usuari.appanimations;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity  {

    private ImageView imvSubZero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imvSubZero = findViewById(R.id.imvSubZero);
        AnimationDrawable ad = (AnimationDrawable) imvSubZero.getDrawable();
        ad.start();



    }
    private AnimationDrawable ad;
    public void onHit(View view) {

        ad = (AnimationDrawable)getResources().getDrawable(R.drawable.sprite_puny);
        imvSubZero.setImageDrawable(ad);
        ad.setOneShot(true);
        ad.start();
        PunyAT at = new PunyAT();
        at.execute();


        LinearLayout llyTerra = findViewById(R.id.llyTerra);
        //------------------VIEW ANIMATION----------------------------------------
        // Anem a afegir botons a un contenidor que té animació
        /*imvSubZero.animate().scaleY(0.3f).alpha(0.0f).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imvSubZero.animate().scaleY(1f).alpha(1f).setDuration(1000).setListener(null);
            }
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });*/
        //-----------------------PROPERTY ANIMATION-----------------------------------
        /*float reduccio = 0.3f;
        float alcada = imvSubZero.getHeight();
        ObjectAnimator a1 = ObjectAnimator.ofFloat(imvSubZero, "ScaleY",1f, reduccio, 1f);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(imvSubZero, "Alpha",1f, 0f, 1f);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(imvSubZero, "TranslationY",0f, alcada * (1-reduccio) *0.5f, 0f);

        AnimatorSet as = new AnimatorSet();
        as.setDuration(4000);
        as.setInterpolator(new LinearInterpolator());
        as.playTogether(a1, a2, a3);
        as.start();*/
        //-----------------------PROPERTY ANIMATION (el paseillo)-----------------------------------
        View mainContainer = findViewById(R.id.mainContainer);
        float H = mainContainer.getHeight();
        float W  = mainContainer.getWidth();
        float w = imvSubZero.getWidth();
        float h = imvSubZero.getHeight();
        Log.d("DIMENSIONS", "D:"+H+","+W+","+w+","+h);

        ObjectAnimator a1 = ObjectAnimator.ofFloat(imvSubZero, "TranslationX",
                0f, W-(w+(h-w)/2.0f));
        ObjectAnimator a2 = ObjectAnimator.ofFloat(imvSubZero, "Rotation",
                0f, 90f);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(imvSubZero, "TranslationY",
                0f, H-h);
        ObjectAnimator a4 = ObjectAnimator.ofFloat(imvSubZero, "Rotation",
                0f, 0f);
        ObjectAnimator a5 = ObjectAnimator.ofFloat(imvSubZero, "ScaleX",
                0f, -1f);
        ObjectAnimator a6 = ObjectAnimator.ofFloat(imvSubZero, "TranslationX",
                W-(w+(h-w)/2.0f), 0f);

        a1.setDuration(2000);
        a2.setDuration(1000);
        a1.setDuration(3000);
        AnimatorSet as1 = new AnimatorSet();

        as1.playSequentially(a1, a2, a3, a4, a5, a6);
        as1.start();


        Button b = new Button(this);

        b.setLayoutParams( new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // amplada
                LinearLayout.LayoutParams.MATCH_PARENT)); // alçada
        b.setText("O");
        llyTerra.addView( b );


    }

    private class PunyAT extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //while(ad.isRunning())
                {
                    Thread.sleep(ad.getDuration(0)*5);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ad = (AnimationDrawable)getResources().getDrawable(R.drawable.sprite_idle);
            imvSubZero.setImageDrawable(ad);
            ad.setOneShot(false);
            ad.start();
        }
    }


}
