package com.project.wisdomfirecontrol.common.soundRecordUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/4/17.
 */

public class PlayBackAudioUtils {

    private boolean isPlaying = false;
    private RecordingItem item;
    private Intent intent;


    public PlayBackAudioUtils() {

    }

    public void onRecord(Context context, boolean start) {

        intent = new Intent(context, RecordingService.class);

        if (start) {
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                folder.mkdir();
            }

            (context).startService(intent);

        } else {
            context.stopService(intent);
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public void palyAudio(RecordingItem item) {
        isPlaying = false;
        onPlay(isPlaying, item);
        isPlaying = !isPlaying;
    }

    private MediaPlayer mMediaPlayer = null;


    // Play start/stop
    private void onPlay(boolean isPlaying, RecordingItem item) {
        if (!isPlaying) {
            //currently MediaPlayer is not playing audio
            if (mMediaPlayer == null) {
                startPlaying(item); //start from beginning
            } else {
                resumePlaying(); //resume the currently paused MediaPlayer
            }

        } else {
            pausePlaying();
        }
    }

    private void startPlaying(RecordingItem item) {
        mMediaPlayer = new MediaPlayer();

        try {
            mMediaPlayer.setDataSource(item.getFilePath());
            mMediaPlayer.prepare();

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });
        } catch (IOException e) {
        }

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        });
    }

    private void prepareMediaPlayerFromPoint(int progress) {
        //set mediaPlayer to start from middle of the audio file

        mMediaPlayer = new MediaPlayer();

        try {
            mMediaPlayer.setDataSource(item.getFilePath());
            mMediaPlayer.prepare();
            mMediaPlayer.seekTo(progress);

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                }
            });

        } catch (IOException e) {
            Log.e("tag", "prepare() failed");
        }
    }

    private void pausePlaying() {
        mMediaPlayer.pause();
    }

    private void resumePlaying() {
        mMediaPlayer.start();
    }

    private void stopPlaying() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;

        isPlaying = !isPlaying;
    }
}
