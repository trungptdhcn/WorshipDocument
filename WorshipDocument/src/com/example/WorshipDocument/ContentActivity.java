package com.example.WorshipDocument;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/14/14
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentActivity extends FragmentActivity
{
    private GridView gridView;
    private GridAdapter adapter;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        gridView = (GridView)findViewById(R.id.grid_view);
        adapter = new GridAdapter(this,new ArrayList<Bitmap>(),"image_1");
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("current_image",position);
                startActivity(intent);
            }
        });
    }
}
