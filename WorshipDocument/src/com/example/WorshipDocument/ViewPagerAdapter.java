package com.example.WorshipDocument;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
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

    ViewPagerAdapter(Context context,String dirFrom)
    {

        this.context = context;
        this.contentDetailList = getContentDetailList(dirFrom);
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
        TextView tvContent = (TextView) view.findViewById(R.id.detail_item_tvContent);
        tvContent.setText(Html.fromHtml(contentDetailList.get(position).getContent()));
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    public List<ContentDetail> getContentDetailList(String dirFrom)
    {
        List<ContentDetail> contentDetails = new ArrayList<ContentDetail>();
        try
        {
            String[] fileList = context.getAssets().list(dirFrom);
            if (fileList != null)
            {
                for (int i = 0; i < fileList.length; i++)
                {
                    ContentDetail contentDetail = new ContentDetail();
                    String tContents = "";
                    InputStream stream = context.getAssets().open(dirFrom + "/" + fileList[i]);
                    int size = stream.available();
                    byte[] buffer = new byte[size];
                    stream.read(buffer);
                    stream.close();
                    tContents = new String(buffer);
                    contentDetail.setContent(tContents);
                    contentDetails.add(contentDetail);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return contentDetails;

    }
}
