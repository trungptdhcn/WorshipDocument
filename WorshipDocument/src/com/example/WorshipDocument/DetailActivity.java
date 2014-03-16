package com.example.WorshipDocument;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.net.URL;
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
    private ImageView ivImage;
    private ImageView btShare;
    private ImageView btPrevious;
    private ImageView btNext;
    private ImageView btHome;
    private ImageView btCopy;
    int currentPosition;
    String flag;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        copyAssets();
        viewPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        btPrevious = (ImageView) findViewById(R.id.detail_layout_btPrevious);
        btNext = (ImageView) findViewById(R.id.detail_layout_btNext);
        btHome = (ImageView) findViewById(R.id.detail_layout_btHome);
        btCopy = (ImageView) findViewById(R.id.detail_layout_btCopy);
        flag = getIntent().getStringExtra("flag");
        btShare = (ImageView) findViewById(R.id.detail_layout_btShare);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        btPlayOrStop = (ImageView) findViewById(R.id.ivPlayOrStop);
        tvTimeCur = (TextView) findViewById(R.id.program_tvTimeCur);
        tvTimePlay = (TextView) findViewById(R.id.program_tvTimePlay);
        sbTime = (SeekBar) findViewById(R.id.program_sbTime);
        if (flag.equals("flag_1"))
        {
            viewPagerAdapter = new ViewPagerAdapter(this, "html_1", "image_1");
        }
        else if (flag.equals("flag_2"))
        {
            viewPagerAdapter = new ViewPagerAdapter(this, "html_2", "image_2");
        }
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
        btHome.setOnClickListener(this);
        btCopy.setOnClickListener(this);
        btShare.setOnClickListener(this);
    }

    private void getSource(int currentPosition)
    {
        Uri uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_0" + currentPosition);
        Log.e("DetailActivity", "===>" + uri.toString());
        mediaPlayer = MediaPlayer.create(this, uri);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.detail_layout_btHome:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.detail_layout_btCopy:
                finish();
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
            case R.id.detail_layout_btShare:
                shareOnWhatsApp(currentPosition);
//                shareOnFacebook(currentPosition);
//
//                Toast.makeText(getApplicationContext(), "Button Share was clicked!", 1).show();
//                try
//                {
//                    createFileForShare();
//                }
//                catch (FileNotFoundException e)
//                {
//                    Log.e("DetailActivity", "===>" + e.getMessage());
//                }
                break;

        }
    }

    private void shareOnWhatsApp(int currentPosition)
    {
        String filePath = null;
        if (currentPosition < 10)
        {
            filePath = "/mnt/sdcard/_0" + currentPosition + ".png";
        }
        else
        {
            filePath = "/mnt/sdcard/" + currentPosition + ".png";
        }

        Intent i = new Intent(Intent.ACTION_SEND, Uri.parse("content://com.android.contacts/data/"));
        i.setType("image/jpeg");
        i.setPackage("com.whatsapp");           // so that only Whatsapp reacts and not the chooser
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
//        i.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
        startActivity(i);
//        Intent whatsApp = new Intent(Intent.ACTION_SEND);
//        whatsApp.setType("image/jpeg");
//        whatsApp.setPackage("com.whatsapp");
//        whatsApp.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
//        startActivity(Intent.createChooser(whatsApp, "Test"));
//        startActivity(whatsApp);
    }

    private void shareOnFacebook(int currentPosition)
    {
        String facebookPackageName = "com.facebook.katana";
        try
        {
            String filePath = null;
            currentPosition = currentPosition + 1;
            getPackageManager().getApplicationInfo(facebookPackageName, 0);
            if (currentPosition < 10)
            {
                filePath = "/mnt/sdcard/_0" + currentPosition + ".png";
            }
            else
            {
                filePath = "/mnt/sdcard/" + currentPosition + ".png";
            }
            Log.e("DetailActivity", "===>" + filePath);
            share("facebook", filePath, "Test");
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Toast.makeText(getApplicationContext(), "Facebook not found! INSTALL.", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=" + facebookPackageName);
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
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
            handler.postDelayed(notification, 0);
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

    private void createFileForShare() throws FileNotFoundException
    {
        // set the path where we want to save the file
        File SDCardRoot = Environment.getExternalStorageDirectory();
        // create a new file, to save the downloaded file
        File file = new File(SDCardRoot, "_1.pdf");
//        FileOutputStream fileOutput = new FileOutputStream(file);
//        String path = "file:///android_asset/" + "_1.pdf";;
        PdfWriter pdfWriter = null;

        //create a new document
        Document document = new Document();

        try
        {

            //get Instance of the PDFWriter
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));

            //document header attributes
//            Image image = Image.getInstance("devi.jpg");
//            document.add(image);
//            document.addAuthor("betterThanZero");
//            document.addCreationDate();
//            document.addProducer();
//            document.addCreator("MySampleCode.com");
//            document.addTitle("Demo for iText XMLWorker");
            document.setPageSize(PageSize.LETTER);

            //open document
            document.open();

            //To convert a HTML file from the filesystem
//            String File_To_Convert = "docs/SamplePDF.html";
            AssetManager assetManager = this.getResources().getAssets();
            InputStream inputStream = null;
            inputStream = assetManager.open("_1.htm");
//            Uri uriFileHTML = Uri.parse("file:///android_asset/");
//            String File_To_Convert = uriFileHTML.getPath() + "_1.html";
//            FileInputStream fis = new FileInputStream(File_To_Convert);

            //URL for HTML page
//            URL myWebPage = new URL("http://demo.mysamplecode.com/");
//            InputStreamReader fis = new InputStreamReader(myWebPage.openStream());

            //get the XMLWorkerHelper Instance
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            //convert to PDF
            worker.parseXHtml(pdfWriter, document, inputStream);

            //close the document
            document.close();
            //close the writer
            pdfWriter.close();

        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }

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
                File SDCardRoot = Environment.getExternalStorageDirectory();
                // create a new file, to save the downloaded file
                File outFile = new File(SDCardRoot, filename);
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
}