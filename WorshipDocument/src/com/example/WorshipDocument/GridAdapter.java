package com.example.WorshipDocument;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/15/14
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class GridAdapter extends BaseAdapter
{
    private Context mContext;
    private List<String> bitmapsFiles = new ArrayList<String>();
    private String dirFrom;
    Bitmap bitmap;

    public GridAdapter(Context mContext, List<Bitmap> listBitmap, String dirFrom)
    {
        this.mContext = mContext;
        this.dirFrom = dirFrom;
        this.bitmapsFiles = listBitmap(dirFrom);


    }

    @Override
    public int getCount()
    {
        return bitmapsFiles.size();
    }

    @Override
    public Object getItem(int i)
    {
        return bitmapsFiles.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        ImageView imageView = new ImageView(mContext);
        InputStream istr = null;
        try
        {
            istr = mContext.getAssets().open(dirFrom + "/" + bitmapsFiles.get(position));
//            try {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 4;
//                bitmap = BitmapFactory.decodeFile(file, options);
//            } catch (OutOfMemoryError e) {
//                e.printStackTrace();
//
//                System.gc();
//
//                try {
//                    bitmap = BitmapFactory.decodeFile(file);
//                } catch (OutOfMemoryError e2) {
//                    e2.printStackTrace();
//                    // handle gracefully.
//                }
//            }
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        if (bitmap != null)
//        {
//            bitmap.recycle();
//        }
//        else
//        {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        bitmap = BitmapFactory.decodeStream(istr, null, options);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(120, 210));
//        }
        return imageView;
    }

    private List<String> listBitmap(String dirFrom)
    {
        List<String> assetFiles = new ArrayList<String>();
        try
        {
            String[] fileList = mContext.getAssets().list(dirFrom);
            if (fileList != null)
            {
                for (int i = 0; i < fileList.length; i++)
                {
                    InputStream istr = mContext.getAssets().open(dirFrom + "/" + fileList[i]);
//                    Bitmap bitmap = BitmapFactory.decodeStream(istr);
                    assetFiles.add(fileList[i]);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return assetFiles;
    }
}
