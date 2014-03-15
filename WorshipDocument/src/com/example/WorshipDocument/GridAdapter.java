package com.example.WorshipDocument;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;

    public GridAdapter(Context mContext, List<Bitmap> listBitmap, String dirFrom)
    {
        this.mContext = mContext;
        this.dirFrom = dirFrom;
        this.bitmapsFiles = listBitmap(dirFrom);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(0)
                .showImageOnFail(0)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();

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
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        imageLoader.displayImage(bitmapsFiles.get(position),imageView,options);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(120, 210));
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
