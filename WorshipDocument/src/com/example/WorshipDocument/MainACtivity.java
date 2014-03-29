package com.example.WorshipDocument;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: three/12/14
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends Activity
{
    private ImageView btOne;
    private ImageView btAbout;
    private ImageView btTwo;
    private ImageView btThree;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        btOne = (ImageView) findViewById(R.id.main_layout_bt1);
        btTwo = (ImageView) findViewById(R.id.main_layout_bt2);
        btThree = (ImageView) findViewById(R.id.main_layout_bt3);
        btAbout = (ImageView) findViewById(R.id.main_layout_bt4);

        btOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                i.putExtra("flag", "flag_1");
                startActivity(i);

            }
        });
        btTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("flag", "flag_2");
                startActivity(intent);
            }
        });

        btAbout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
        btThree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent3 = new Intent(getApplicationContext(), ContentScreen3Activity.class);
//                intent3.putExtra("flag", "flag_2");
                startActivity(intent3);
            }
        });

    }

//    private void copyAssets()
//    {
//        AssetManager assetManager = getAssets();
//        String[] files = null;
//        try
//        {
//            files = assetManager.list("");
//        }
//        catch (IOException e)
//        {
//            Log.e("tag", "Failed to get asset file list.", e);
//        }
//        for (String filename : files)
//        {
//            InputStream in = null;
//            OutputStream out = null;
//            try
//            {
//                in = assetManager.open(filename);
//                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Praying Guide";
//                File dir = new File(filePath);
//                File outFile = new File(dir, filename);
//                Log.e("DetailActivity", "===>" + outFile.getAbsolutePath());
//                out = new FileOutputStream(outFile);
//                copyFile(in, out);
//                in.close();
//                in = null;
//                out.flush();
//                out.close();
//                out = null;
//            }
//            catch (IOException e)
//            {
//                Log.e("tag", "Failed to copy asset file: " + filename, e);
//            }
//        }
//    }
//
//    private void copyFile(InputStream in, OutputStream out) throws IOException
//    {
//        byte[] buffer = new byte[1024];
//        int read;
//        while ((read = in.read(buffer)) != -1)
//        {
//            out.write(buffer, 0, read);
//        }
//    }
}