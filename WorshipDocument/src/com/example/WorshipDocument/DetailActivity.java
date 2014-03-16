package com.example.WorshipDocument;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/15/14
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class DetailActivity extends Activity implements View.OnClickListener
{
    private int mediaDuration;
    private int mediaPosition;
    private AudioManager audioManager = null;
    private SeekBar sbTime;
    private ImageView btPlayOrStop;
    private TextView tvTimeCur;
    private TextView tvTimePlay;
    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();


    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private ImageView ivImage;
    private ImageView btFirst;
    private ImageView btLast;
    private ImageView btPrevious;
    private ImageView btNext;
    private ImageView btHome;
    private ImageView btCopy;
    private ImageView btZomIn;
    private ImageView bZomOut;
    int currentPosition;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        viewPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        btFirst = (ImageView) findViewById(R.id.detail_layout_btFirst);
        btLast = (ImageView) findViewById(R.id.detail_layout_btLast);
        btPrevious = (ImageView) findViewById(R.id.detail_layout_btPrevious);
        btNext = (ImageView) findViewById(R.id.detail_layout_btNext);
        btHome = (ImageView) findViewById(R.id.detail_layout_btHome);
        btCopy = (ImageView) findViewById(R.id.detail_layout_btCopy);
        btZomIn = (ImageView) findViewById(R.id.detail_layout_btZom_In);
        bZomOut = (ImageView) findViewById(R.id.detail_layout_btZom_Out);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        btPlayOrStop = (ImageView) findViewById(R.id.ivPlayOrStop);
        tvTimeCur = (TextView) findViewById(R.id.program_tvTimeCur);
        tvTimePlay = (TextView) findViewById(R.id.program_tvTimePlay);
        sbTime = (SeekBar) findViewById(R.id.program_sbTime);

        viewPagerAdapter = new ViewPagerAdapter(this, "html_1", "image_1");
        viewPager.setAdapter(viewPagerAdapter);
        int position = getIntent().getIntExtra("current_image", -1);
        if (position == -1)
        {
            currentPosition = 0;
        }
        else
        {
            currentPosition = position;
        }
        getSource(currentPosition + 1);
        viewPager.setCurrentItem(currentPosition);
        clickEvent();
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getTimeOfRecordAndShow();
        btPlayOrStop.setOnClickListener(this);
        sbTime.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                seekChange(v);
                return false;
            }
        });

    }

    private void seekChange(View v)
    {
        if (mediaPlayer.isPlaying())
        {
            SeekBar sb = (SeekBar) v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }


    private void getTimeOfRecordAndShow()
    {
        mediaDuration = mediaPlayer.getDuration();
        mediaPosition = mediaPlayer.getCurrentPosition();

        tvTimeCur.setText(getTimeString(mediaPosition));
        tvTimePlay.setText(getTimeString(mediaDuration));
    }

    private String getTimeString(long millis)
    {
        StringBuffer buf = new StringBuffer();

        //int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                //.append(String.format("%02d", hours))
                //.append(":")
                .append(String.format("%01d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


    public void clickEvent()
    {
        btFirst.setOnClickListener(this);
        btLast.setOnClickListener(this);
        btPrevious.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btHome.setOnClickListener(this);
        btCopy.setOnClickListener(this);
        btZomIn.setOnClickListener(this);
        bZomOut.setOnClickListener(this);
    }

    private void getSource(int currentPosition)
    {
        Uri uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_0" + currentPosition);
        mediaPlayer = MediaPlayer.create(this, uri);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.detail_layout_btHome:
                finish();
                break;
            case R.id.detail_layout_btCopy:
                break;
            case R.id.detail_layout_btFirst:
                viewPager.setCurrentItem(0);
                currentPosition = 0;
                reloadResource();
                break;
            case R.id.detail_layout_btLast:
                viewPager.setCurrentItem(25);
                currentPosition = 25;
                reloadResource();
                break;
            case R.id.detail_layout_btPrevious:
                if (currentPosition >= 1)
                {
                    currentPosition = currentPosition - 1;
                    viewPager.setCurrentItem(currentPosition);
                }
                reloadResource();
                break;
            case R.id.detail_layout_btNext:
                if (currentPosition <= 25)
                {
                    currentPosition = currentPosition + 1;
                    viewPager.setCurrentItem(currentPosition);
                }
                reloadResource();
                break;
            case R.id.detail_layout_btZom_In:
                break;
            case R.id.detail_layout_btZom_Out:
                break;
            case R.id.ivPlayOrStop:
                if (!mediaPlayer.isPlaying())
                {
                    btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.content_bt_play));
                    try
                    {
                        mediaPlayer.start();
                        startPlayProgressUpdater();
                    }
                    catch (IllegalStateException e)
                    {
                        mediaPlayer.pause();
                    }
                }
                else
                {
                    mediaPlayer.pause();
                    btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.content_bt_pause));
                }
                break;

        }
    }

    private void reloadResource()
    {
        mediaPlayer.stop();
        mediaPlayer.seekTo(0);
        getSource(currentPosition + 1);
        getTimeOfRecordAndShow();
    }

    public void startPlayProgressUpdater()
    {
        getTimeOfRecordAndShow();
        sbTime.setProgress(mediaPosition);
        sbTime.setMax(mediaDuration);
        if (mediaPlayer.isPlaying())
        {
            Runnable notification = new Runnable()
            {
                public void run()
                {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
        else
        {
            mediaPlayer.pause();
            btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.content_bt_pause));
            if (mediaPosition == mediaDuration)
            {
                sbTime.setProgress(0);
            }
            else
            {
                sbTime.setProgress(mediaPosition);
            }
        }
    }

}