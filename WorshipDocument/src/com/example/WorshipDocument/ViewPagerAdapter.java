package com.example.WorshipDocument;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/15/14
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ViewPagerAdapter extends PagerAdapter
{
    private Context context;
    private List<ContentDetail> contentDetailList = new ArrayList<ContentDetail>();
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    public static List<String> assetFiles = new ArrayList<String>();

    ViewPagerAdapter(Context context,String dirFromHtml,String dirFromImage)
    {

        this.context = context;
        this.contentDetailList = getContentDetailList(dirFromHtml,dirFromImage);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
//                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
    }

    @Override
    public int getCount()
    {
        return contentDetailList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        view = inflater.inflate(R.layout.detail_item, null);
        ((ViewPager) container).addView(view, 0);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        TextView tvContent = (TextView) view.findViewById(R.id.detail_item_tvContent);
        ImageView ivImage = (ImageView) view.findViewById(R.id.detail_item_ivImage);
        imageLoader.displayImage(contentDetailList.get(position).getFileImage(), ivImage, options);
        tvContent.setText(Html.fromHtml(contentDetailList.get(position).getContent()));

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    public List<ContentDetail> getContentDetailList(String dirFromHtml,String dirFromImage)
    {
        List<ContentDetail> contentDetails = new ArrayList<ContentDetail>();
        try
        {
            String[] fileList = context.getAssets().list(dirFromHtml);
            List<String> listFileImage = assetFiles;
            if (fileList != null && listFileImage != null)
            {
                for (int i = 0; i <= fileList.length -1 ; i++)
                {
                    ContentDetail contentDetail = new ContentDetail();
                    String tContents = "";
                    InputStream stream = context.getAssets().open(dirFromHtml + "/" + fileList[i]);
                    ByteArrayOutputStream oas = new ByteArrayOutputStream();
                    copyStream(stream, oas);
                    String t = oas.toString();
                    try {
                        oas.close();
                        oas = null;
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
//                    int size = stream.available();
//                    byte[] buffer = new byte[size];
//                    stream.read(buffer);
//                    stream.close();
//                    tContents = new String(buffer);
                    contentDetail.setContent(t);
                    contentDetail.setFileImage(listFileImage.get(i));
                    contentDetails.add(contentDetail);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentDetails;
    }



    private void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

//    private List<String> listBitmap(String dirFrom)
//    {
//
//        try
//        {
//            String[] fileList = context.getAssets().list(dirFrom);
//            if (fileList != null)
//            {
//                for (int i = 0; i <= fileList.length -1 ; i++)
//                {
//                    assetFiles.add("assets://"+dirFrom + "/"+fileList[i]);
//                }
//            }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        return assetFiles;
//    }
}
