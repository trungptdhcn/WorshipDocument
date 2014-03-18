package com.example.WorshipDocument;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/18/14
 * Time: 10:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentAdapter3 extends BaseAdapter
{
    private String[] list;
    private List<String> listHtmlFile = new ArrayList<String>();
    private Context context;

    public ContentAdapter3(Context context)
    {
        this.list = new String[11];
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return list.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int i)
    {
        return list[i];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int i)
    {
        return i;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.content_3_item, null);
        Button button = (Button)view.findViewById(R.id.content_3_item_btItem);
        button.setText(i+"");
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("current_image", i);
                intent.putExtra("flag", "flag_3");
                context.startActivity(intent);
            }
        });
        return view;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
