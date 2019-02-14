package com.jediupc.helloandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jediupc.helloandroid.R;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class PlayRecord extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton mBPrev;
    FloatingActionButton mBPlay;
    FloatingActionButton mBNext;
    private boolean mPlaying;
    private AppCompatSeekBar mSeekBar;
    int position;
    private ModelContainer mAudio;
    MediaPlayer mediaPlayer;
    private boolean playing=false;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private TextView chronometer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_record);

        mBPrev = findViewById(R.id.bPrev);
        mBPlay = findViewById(R.id.bPlay);
        mBNext = findViewById(R.id.bNext);

        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setMax(100);
        chronometer = findViewById(R.id.chronometerTimer2);


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser) {
                    mediaPlayer.seekTo(progress*1000);
                    chronometer.setText(msToString(progress*1000));
                }
                else if (mediaPlayer!=null){
                    chronometer.setText(msToString(progress*1000));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





        mBPrev.setOnClickListener(this);
        mBPlay.setOnClickListener(this);
        mBNext.setOnClickListener(this);

        position = getIntent().getIntExtra("pos",0);
        mAudio = ModelContainer.load(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void playMusic() {
       /* Intent i = new Intent(this, PlayRecordService.class);
        i.setAction(PlayRecordService.ACTION_PLAY);
        i.putExtra("pos", position);
        Log.d("Pos: ",String.valueOf(position));
        startService(i);*/

       if (mediaPlayer!=null)stopMusic();

        mediaPlayer = new MediaPlayer();
        Log.d("PATH: ",mAudio.audios.get(position).path);
        try {
            mediaPlayer.setDataSource(mAudio.audios.get(position).path);



        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaPlayer.prepare();
            mSeekBar.setMax(mediaPlayer.getDuration());

            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        chronometer.setText(msToString(0));
        //a.start();
    }

    public void onServiceResult(Boolean playing) {
        mPlaying = playing;
        int id = playing
                ? R.drawable.ic_pause
                : R.drawable.ic_play;
        Drawable d = getResources().getDrawable(id);
        mBPlay.setImageDrawable(d);
    }



    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.bPrev:
                if (position - 1 >= 0) {
                    --position;
                }
                else position=mAudio.audios.size()-1;
                playMusic();
                break;
            case R.id.bPlay:
                if (playing) {
                    stopMusic();
                    playing = !playing;
                    onServiceResult(playing);
                } else {
                    playMusic();
                    playing = !playing;
                    onServiceResult(playing);
                    initializeSeekBar();
                }
                break;
            case R.id.bNext:
                if (position + 1 < mAudio.audios.size()) {
                    ++position;
                }
                else position = 0;
                playMusic();
                break;
        }
    }

    private void stopMusic() {
        if (mediaPlayer!=null) mediaPlayer.stop();

    }
    protected void initializeSeekBar(){
        mSeekBar.setMax(mediaPlayer.getDuration()/1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000; // In milliseconds
                    mSeekBar.setProgress(mCurrentPosition);
                   // getAudioStats();
                }
                mHandler.postDelayed(mRunnable,1000);
            }
        };
        mHandler.postDelayed(mRunnable,1000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopMusic();
    }

    @SuppressLint("DefaultLocale")
    private String msToString(long ms) {
        long seconds = (ms / 1000) % 60;
        long minutes = ms / 1000 / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
