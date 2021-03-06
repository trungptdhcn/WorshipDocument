package com.example.WorshipDocument.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.WorshipDocument.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.IOException;
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
    public List<String> assetFiles = new ArrayList<String>();

    public GridAdapter(Context mContext, List<Bitmap> listBitmap, String dirFrom)
    {
        this.mContext = mContext;
        this.dirFrom = dirFrom;
        this.bitmapsFiles = listBitmap(dirFrom);
        if (dirFrom.equals(""))
        {
            options = new DisplayImageOptions.Builder()
                    .build();
        }
        else
        {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.content_image_thumnail_background)
                    .showImageForEmptyUri(R.drawable.content_image_thumnail_background)
                    .showImageOnFail(R.drawable.content_image_thumnail_background)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .build();
        }
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
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .memoryCacheExtraOptions(480, 800)
                        // Can slow ImageLoader, use it carefully (Better don't use it)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
        imageLoader.init(config);
//        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        imageLoader.displayImage(bitmapsFiles.get(position), imageView, options);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
        return imageView;
    }

    private List<String> listBitmap(String dirFrom)
    {
        try
        {
            String[] fileList = mContext.getAssets().list(dirFrom);
            if (fileList != null)
            {
                for (int i = 0; i <= fileList.length - 1; i++)
                {
                    assetFiles.add("assets://" + dirFrom + "/" + fileList[i]);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return assetFiles;
    }
}
