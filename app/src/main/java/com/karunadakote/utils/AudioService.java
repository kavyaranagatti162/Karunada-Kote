package com.karunadakote.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.karunadakote.R;
import com.karunadakote.activities.MapGuideActivity;

public class AudioService extends Service {

    private static final String TAG = "AudioService";
    private static final String CHANNEL_ID = "karunada_audio_channel";
    private static final int NOTIFICATION_ID = 1001;

    public static final String ACTION_PLAY    = "ACTION_PLAY";
    public static final String ACTION_PAUSE   = "ACTION_PAUSE";
    public static final String ACTION_STOP    = "ACTION_STOP";
    public static final String EXTRA_AUDIO_RES = "EXTRA_AUDIO_RES";
    public static final String EXTRA_TITLE     = "EXTRA_TITLE";

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new AudioBinder();
    private String currentTitle = "";

    public class AudioBinder extends Binder {
        public AudioService getService() { return AudioService.this; }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_NOT_STICKY;

        String action = intent.getAction();
        if (action == null) return START_NOT_STICKY;

        switch (action) {
            case ACTION_PLAY:
                int resId = intent.getIntExtra(EXTRA_AUDIO_RES, -1);
                currentTitle = intent.getStringExtra(EXTRA_TITLE);
                if (resId != -1) playAudio(resId);
                break;
            case ACTION_PAUSE:
                pauseAudio();
                break;
            case ACTION_STOP:
                stopAudio();
                break;
        }
        return START_NOT_STICKY;
    }

    private void playAudio(int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        try {
            mediaPlayer = MediaPlayer.create(this, resId);
            if (mediaPlayer != null) {
                // Wake lock keeps audio playing with screen off
                mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
                mediaPlayer.setOnCompletionListener(mp -> {
                    updateNotification("Finished: " + currentTitle);
                });
                mediaPlayer.start();
                startForeground(NOTIFICATION_ID, buildNotification("Playing: " + currentTitle));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error playing audio: " + e.getMessage());
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            updateNotification("Paused: " + currentTitle);
        }
    }

    public void resumeAudio() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            updateNotification("Playing: " + currentTitle);
        }
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopForeground(true);
        stopSelf();
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public int getCurrentPosition() {
        return mediaPlayer != null ? mediaPlayer.getCurrentPosition() : 0;
    }

    public int getDuration() {
        return mediaPlayer != null ? mediaPlayer.getDuration() : 0;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return binder; }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // ─── Notifications ────────────────────────────────────────────────────────

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Karunada Kote Audio Guide",
                NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Audio narration for fort tour");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }
    }

    private Notification buildNotification(String text) {
        Intent notifIntent = new Intent(this, MapGuideActivity.class);
        PendingIntent pi = PendingIntent.getActivity(
            this, 0, notifIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Intent stopIntent = new Intent(this, AudioService.class);
        stopIntent.setAction(ACTION_STOP);
        PendingIntent stopPi = PendingIntent.getService(
            this, 0, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        return new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("ಕರ್ನಾಟಕ ಕೋಟೆ ಗೈಡ್")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_fort_notify)
            .setContentIntent(pi)
            .addAction(R.drawable.ic_stop, "Stop", stopPi)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build();
    }

    private void updateNotification(String text) {
        NotificationManager manager = (NotificationManager)
            getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, buildNotification(text));
        }
    }
}
