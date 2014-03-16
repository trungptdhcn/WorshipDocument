package com.example.WorshipDocument;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.*;
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

    ViewPagerAdapter(Context context, String dirFromHtml, String dirFromImage)
    {

        this.context = context;
        this.contentDetailList = getContentDetailList(dirFromHtml, dirFromImage);
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
        final RelativeLayout relativeLayoutContainer = (RelativeLayout) view.findViewById(R.id.detail_item_rlContainerContent);
        ImageView ivZomIn = (ImageView) view.findViewById(R.id.detail_item_btZom_in);
        ImageView ivZomOut = (ImageView) view.findViewById(R.id.detail_item_btZom_out);
        final WebView webView = (WebView) view.findViewById(R.id.detail_item_wvContent);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.loadDataWithBaseURL(null, contentDetailList.get(position).getContent(), "text/html", "utf-8", null);
        ivZomIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                webView.zoomIn();
            }
        });
        ivZomOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                webView.zoomOut();
            }
        });

        ImageView ivImage = (ImageView) view.findViewById(R.id.detail_item_ivImage);
        imageLoader.displayImage(contentDetailList.get(position).getFileImage(), ivImage, options);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    public List<ContentDetail> getContentDetailList(String dirFromHtml, String dirFromImage)
    {
        List<ContentDetail> contentDetails = new ArrayList<ContentDetail>();
        try
        {
            String[] fileList = context.getAssets().list(dirFromHtml);
            List<String> listFileImage = assetFiles;
            if (fileList != null && listFileImage != null)
            {
                for (int i = 0; i <= fileList.length - 1; i++)
                {
                    ContentDetail contentDetail = new ContentDetail();
                    String tContents = "";
                    InputStream stream = context.getAssets().open(dirFromHtml + "/" + fileList[i]);
                    ByteArrayOutputStream oas = new ByteArrayOutputStream();
                    copyStream(stream, oas);
                    String t = oas.toString();
                    try
                    {
                        oas.close();
                        oas = null;
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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
            byte[] bytes = new byte[buffer_size];
            for (; ; )
            {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                {
                    break;
                }
                os.write(bytes, 0, count);
            }
        }
        catch (Exception ex)
        {
        }
    }

    public void zoom(RelativeLayout view, Float scaleX, Float scaleY, PointF pivot)
    {
        view.setPivotX(pivot.x);
        view.setPivotY(pivot.y);
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
    }

}
