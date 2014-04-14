package com.example.WorshipDocument;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.WorshipDocument.adapter.ViewPagerAdapter;

import java.io.*;

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
    private ImageView btPrevious;
    private ImageView btNext;
    int currentPosition;
    String flag;
    boolean isFinish = false;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        viewPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        btPrevious = (ImageView) findViewById(R.id.detail_layout_btPrevious);
        btNext = (ImageView) findViewById(R.id.detail_layout_btNext);
        flag = getIntent().getStringExtra("flag");

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        btPlayOrStop = (ImageView) findViewById(R.id.ivPlayOrStop);
        tvTimeCur = (TextView) findViewById(R.id.program_tvTimeCur);
        tvTimePlay = (TextView) findViewById(R.id.program_tvTimePlay);
        sbTime = (SeekBar) findViewById(R.id.program_sbTime);
        int position = getIntent().getIntExtra("current_image", -1);
        if (position == -1)
        {
            currentPosition = 0;
        }
        else
        {
            currentPosition = position;
        }
        prepareData();
//        else if (flag.equals("flag_3"))
//        {
//            viewPagerAdapter = new ViewPagerAdapter(this, "html_3", "");
//            copyAssets();
//            getSource(currentPosition + 1);
//            getTimeOfRecordAndShow();
//        }
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(currentPosition);
        clickEvent();
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
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

    private void prepareData()
    {
        if (flag.equals("flag_1"))
        {
            viewPagerAdapter = new ViewPagerAdapter(this, "html_1", "image_1");
            copyAssets();
            getSource(currentPosition + 1);
            getTimeOfRecordAndShow();
        }
        else if (flag.equals("flag_2"))
        {
            viewPagerAdapter = new ViewPagerAdapter(this, "html_2", "image_2");
            copyAssets();
            getSource(currentPosition + 1);
            getTimeOfRecordAndShow();
        }
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

        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%01d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    public void clickEvent()
    {
        btPrevious.setOnClickListener(this);
        btNext.setOnClickListener(this);
    }

    private void getSource(int currentPosition)
    {
        Uri uri = null;
        if (flag.equals("flag_1"))
        {
            if (currentPosition < 10)
            {
                uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_0" + currentPosition);
            }
            else
            {
                uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_" + currentPosition);
            }
        }
        else if (flag.equals("flag_2"))
        {
            if (currentPosition < 10)
            {
                uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_a0" + currentPosition);
            }
            else
            {
                uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_a" + currentPosition);
            }
        }
        else if (flag.equals("flag_3"))
        {
            if (currentPosition < 10)
            {
                uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_b0" + currentPosition);
            }
            else
            {
                uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_b" + currentPosition);
            }
        }
        Log.e("DetailActivity", "===>" + uri.toString());
        mediaPlayer = MediaPlayer.create(this, uri);
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = null;
        switch (view.getId())
        {
            case R.id.detail_layout_btPrevious:
                if (currentPosition >= 1)
                {
                    currentPosition = currentPosition - 1;
                    callNextOrPreviousData();

                }
                else if (currentPosition == 0)
                {
                    if (flag.equals("flag_1"))
                    {
                        currentPosition = 25;
                    }
                    else if (flag.equals("flag_2"))
                    {
                        currentPosition = 12;
                    }
                    callNextOrPreviousData();
                }
                reloadResource();
                break;
            case R.id.detail_layout_btNext:
                if (flag.equals("flag_1"))
                {
                    if (currentPosition < 25)
                    {
                        currentPosition = currentPosition + 1;
                        callNextOrPreviousData();
                    }
                    else if (currentPosition == 25)
                    {
                        currentPosition = 0;
                        callNextOrPreviousData();
                    }
                }
                else if (flag.equals("flag_2"))
                {
                    if (currentPosition < 12)
                    {
                        currentPosition = currentPosition + 1;
                        callNextOrPreviousData();
                    }
                    else if (currentPosition == 12)
                    {
                        currentPosition = 0;
                        callNextOrPreviousData();
                    }
                }
                reloadResource();
                break;
            case R.id.ivPlayOrStop:
                if (!mediaPlayer.isPlaying())
                {
                    btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.appwidget_control_pause_neutral));
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
                    btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.appwidget_control_play_neutral));
                }
                break;
        }
    }

    private void callNextOrPreviousData()
    {
        Intent intent;
        intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("current_image", currentPosition);
        intent.putExtra("flag", flag);
        startActivity(intent);
        finish();
    }


    private void reloadResource()
    {
        mediaPlayer.stop();
        mediaPlayer.seekTo(0);
        mediaPlayer.reset();
        mediaPlayer.release();
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
            handler.postDelayed(notification, 0);
        }
        else
        {
            mediaPlayer.pause();
            btPlayOrStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.appwidget_control_play_neutral));
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

    private void copyAssets()
    {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try
        {
            files = assetManager.list("");
        }
        catch (IOException e)
        {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        for (String filename : files)
        {
            InputStream in = null;
            OutputStream out = null;
            try
            {
                in = assetManager.open(filename);
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Praying Guide";
                File dir = new File(filePath);
                if (!dir.exists())
                {
                    dir.mkdirs();
                }
//                File SDCardRoot = Environment.getExternalStorageDirectory();
                // create a new file, to save the downloaded file
                File outFile = new File(dir, filename);
                Log.e("DetailActivity", "===>" + outFile.getAbsolutePath());
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            }
            catch (IOException e)
            {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        mediaPlayer.stop();
        mediaPlayer.seekTo(0);
        mediaPlayer.reset();
        mediaPlayer.release();
        Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
        intent.putExtra("flag", getIntent().getStringExtra("flag"));
        startActivity(intent);
        finish();
    }
}