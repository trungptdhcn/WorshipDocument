package com.example.WorshipDocument;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.WorshipDocument.adapter.ViewPagerAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    private ImageView btShare;
    private ImageView btPrevious;
    private ImageView btNext;
    private ImageView btHome;
    int currentPosition;
    String flag;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        viewPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        btPrevious = (ImageView) findViewById(R.id.detail_layout_btPrevious);
        btNext = (ImageView) findViewById(R.id.detail_layout_btNext);
//        btHome = (ImageView) findViewById(R.id.detail_layout_btHome);
        flag = getIntent().getStringExtra("flag");
//        btShare = (ImageView) findViewById(R.id.detail_layout_btShare);

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
        else if (flag.equals("flag_3"))
        {
            viewPagerAdapter = new ViewPagerAdapter(this, "html_3", "");
            copyAssets();
            getSource(currentPosition + 1);
            getTimeOfRecordAndShow();
        }
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
//        btHome.setOnClickListener(this);
//        btShare.setOnClickListener(this);
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
//            case R.id.detail_layout_btHome:
//                 intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//                finish();
//                break;
            case R.id.detail_layout_btPrevious:
                if (currentPosition >= 1)
                {
                    currentPosition = currentPosition - 1;
                    callNextOrPreviousData();
//                    viewPager.setCurrentItem(currentPosition);

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
//                    viewPager.setCurrentItem(currentPosition);
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
//            case R.id.detail_layout_btShare:
//                shareOnWhatsApp(currentPosition);
//                shareOnFacebook(currentPosition);
//
//                Toast.makeText(getApplicationContext(), "Button Share was clicked!", 1).show();
//                try
//                {
//                    createFileForShare();
//                }
//                catch (IOException e)
//                {
//                    Log.e("DetailActivity", "===>" + e.getMessage());
//                }
//                break;
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

//    private void shareOnWhatsApp(int currentPosition)
//    {
//        String filePath = null;
//        if (currentPosition < 10)
//        {
//            if (flag.equals("flag_1"))
//            {
//                filePath = "/mnt/sdcard/Praying Guide/_0" + currentPosition + ".htm";
//            }
//            else
//            {
//                filePath = "/mnt/sdcard/Praying Guide/_a0" + currentPosition + ".htm";
//            }
//        }
//        else
//        {
//            if (flag.equals("flag_1"))
//            {
//                filePath = "/mnt/sdcard/Praying Guide/" + currentPosition + ".htm";
//            }
//            else
//            {
//                filePath = "/mnt/sdcard/Praying Guide/a" + currentPosition + ".htm";
//            }
//        }
//        Intent whatsApp = new Intent(Intent.ACTION_SEND);
//        whatsApp.setType("plain/text");
//        whatsApp.setPackage("com.whatsapp");
//        whatsApp.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
//        startActivity(Intent.createChooser(whatsApp, "Test"));
//        startActivity(whatsApp);
//    }

//    private void shareOnFacebook(int currentPosition)
//    {
//        String facebookPackageName = "com.facebook.katana";
//        try
//        {
//            String filePath = null;
//            currentPosition = currentPosition + 1;
//            getPackageManager().getApplicationInfo(facebookPackageName, 0);
//            if (currentPosition < 10)
//            {
//                filePath = "/mnt/sdcard/_0" + currentPosition + ".png";
//            }
//            else
//            {
//                filePath = "/mnt/sdcard/" + currentPosition + ".png";
//            }
//            Log.e("DetailActivity", "===>" + filePath);
//            share("facebook", filePath, "Test");
//        }
//        catch (PackageManager.NameNotFoundException e)
//        {
//            Toast.makeText(getApplicationContext(), "Facebook not found! INSTALL.", Toast.LENGTH_LONG).show();
//            Uri uri = Uri.parse("market://details?id=" + facebookPackageName);
//            Intent i = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(i);
//        }
//    }

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

//    private void createFileForShare() throws IOException
//    {
//
//        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Praying Guide File Out";
//        File dir = new File(filePath);
//        if (!dir.exists())
//        {
//            dir.mkdirs();
//        }
//        File file = new File(dir, "_1.pdf");
//        PdfWriter pdfWriter = null;
//        Document document = new Document();
//        try
//        {
//            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
//
//            //document header attributes
////            Image image = Image.getInstance("devi.jpg");
////            document.add(image);
////            document.addAuthor("betterThanZero");
////            document.addCreationDate();
////            document.addProducer();
////            document.addCreator("MySampleCode.com");
////            document.addTitle("Demo for iText XMLWorker");
//            document.setPageSize(PageSize.LETTER);
//
//            //open document
//            document.open();
//
//            //To convert a HTML file from the filesystem
//            String File_To_Convert = "/mnt/sdcard/Praying Guide/_1.htm";
//            FileInputStream stream = new FileInputStream(File_To_Convert);
//            InputStreamReader fis = new InputStreamReader(stream);
//            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
//            //convert to PDF
//            worker.parseXHtml(pdfWriter, document, fis);
//            document.close();
//            pdfWriter.close();
//
//        }
//
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        catch (DocumentException e)
//        {
//            e.printStackTrace();
//        }
//    }

    private void share(String nameApp, String imagePath, String text)
    {
        try
        {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("image/jpeg");
            List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
            if (!resInfo.isEmpty())
            {
                for (ResolveInfo info : resInfo)
                {
                    Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShare.setType("image/jpeg");
                    if (info.activityInfo.packageName.toLowerCase().contains(nameApp) || info.activityInfo.name.toLowerCase().contains(nameApp))
                    {
                        targetedShare.putExtra(Intent.EXTRA_TEXT, text);
                        targetedShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
                        targetedShare.setPackage(info.activityInfo.packageName);
                        targetedShareIntents.add(targetedShare);
                    }
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            }
        }
        catch (Exception e)
        {
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