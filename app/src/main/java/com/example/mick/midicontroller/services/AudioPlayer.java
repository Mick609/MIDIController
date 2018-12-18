package com.example.mick.midicontroller.services;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.PlaybackParams;

public class AudioPlayer {
    private Context mContext;
    private AppService app;
    private MediaPlayer mediaPlayer;
    private int resid;
    private float speed;
    private float pitch;

    public AudioPlayer(Context mContext, int resid) {
        this.mContext = mContext;
        app = new AppService(mContext);
        this.resid = resid;
        mediaPlayer = MediaPlayer.create(this.mContext, this.resid);
    }

    public void play() {
        mediaPlayer.start();
    }

    public void pause() {
        app.mLog("AudioPlayer.pause");
        mediaPlayer.pause();
    }

    public void release() {
        mediaPlayer.release();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public float getSpeed() {
        PlaybackParams mediaPlayerPlaybackParams = mediaPlayer.getPlaybackParams();
        speed = mediaPlayerPlaybackParams.getSpeed();
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        PlaybackParams mediaPlayerPlaybackParams = mediaPlayer.getPlaybackParams();
        mediaPlayerPlaybackParams.setSpeed(this.speed);
        mediaPlayer.setPlaybackParams(mediaPlayerPlaybackParams);
    }

    public float getPitch() {
        PlaybackParams mediaPlayerPlaybackParams = mediaPlayer.getPlaybackParams();
        pitch = mediaPlayerPlaybackParams.getPitch();
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        PlaybackParams mediaPlayerPlaybackParams = mediaPlayer.getPlaybackParams();
        mediaPlayerPlaybackParams.setPitch(this.pitch);
        mediaPlayer.setPlaybackParams(mediaPlayerPlaybackParams);
    }

    public String getDuration() {
        int mills = mediaPlayer.getDuration();
        int second = mills / 1000;
        int min = second / 60;
        int sec = second % 60;
        if (sec < 10) {
            return min + ":0" + sec;
        } else {
            return min + ":" + sec;
        }
    }

    public String getPosition() {
        long micro = mediaPlayer.getTimestamp().getAnchorMediaTimeUs();
        int mills = (int) micro / 1000;
        int second = mills / 1000;
        int min = second / 60;
        int sec = second % 60;

        if (sec < 10) {
            return min + ":0" + sec;
        } else {
            return min + ":" + sec;
        }
    }
}
