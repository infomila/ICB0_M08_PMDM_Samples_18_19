package com.example.usuari.myapplication.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.usuari.myapplication.MainActivity;
import com.example.usuari.myapplication.R;


/**
 *
 * Si voleu que el servei mori si es mata l'aplicació
 * cal que editeu el AndroidManifest i li afegiu un atribut
 * al servei:
 *
 *              android:stopWithTask="true"
 *
 *
 */

public class MusicPlayerService extends Service {

    public static final String ACTION = "ACTION";
    private static final int SERVICE_ID = 587234782;
    MediaPlayer mediaPlayer;

    public MusicPlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.mario_theme);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Actions action = (Actions)intent.getSerializableExtra(ACTION);
        if(intent.getAction()==null) return Service.START_NOT_STICKY;

        Actions action = Actions.valueOf(intent.getAction());
        Log.d("LOG", ">Start Command" + action);
        switch (action) {
            case PLAY:      mediaPlayer.start();initNotificationBar(true);break;
            case PAUSE:     mediaPlayer.pause();initNotificationBar(false);break;
            case STOP:      mediaPlayer.stop();break;
            case INIT: initNotificationBar(false);break;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    public enum Actions {
        INIT,
        PLAY,
        STOP,
        NEXT,
        PREVIOUS,
        PAUSE;

        @Override
        public String toString() {
            return super.toString();
        }
    }
    //--------------------------------------------------------------------
    private void initNotificationBar(boolean isPlaying) {

        Intent mainActivityIntent = new Intent( this, MainActivity.class);
        PendingIntent maPendingIntent =
                PendingIntent.getActivity(this, 0, mainActivityIntent,0);
        //----------------------------------------------------------------------------
        // Preparem l'itent que s'executarà quan fem click sobre el botó play
        // de la notificació
        Intent playIntent = new Intent(this, MusicPlayerService.class);
        playIntent.setAction(Actions.PLAY+"");
        PendingIntent playPendingIntent = PendingIntent.getService(
                this,
                0,
                playIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Intent pauseIntent = new Intent(this, MusicPlayerService.class);
        pauseIntent.setAction(Actions.PAUSE+"");
        PendingIntent pausePendingIntent = PendingIntent.getService(
                this,
                0,
                pauseIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);


        //----------------------------------------------------------------------------
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Notificació Music Player")
                .setContentText("Aquest text és contingut blah, blah, blah")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(maPendingIntent)
                .addAction(
                        !isPlaying?android.R.drawable.ic_media_play:android.R.drawable.ic_media_pause,
                        !isPlaying?"Play": "Pause",
                        !isPlaying? playPendingIntent: pausePendingIntent)
                .build();
        startForeground(SERVICE_ID, notification);
    }

}
