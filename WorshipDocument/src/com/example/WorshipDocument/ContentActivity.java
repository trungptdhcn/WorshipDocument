package com.example.WorshipDocument;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.WorshipDocument.adapter.GridAdapter;

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
    String flag;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        gridView = (GridView) findViewById(R.id.grid_view);
        flag = getIntent().getStringExtra("flag");
        if (gridView.getAdapter() != null)
        {
            gridView.removeAllViews();
        }
        if (flag.equals("flag_1"))
        {
            adapter = new GridAdapter(this, new ArrayList<Bitmap>(), "image_1");
        }
        else if (flag.equals("flag_2"))
        {
            adapter = new GridAdapter(this, new ArrayList<Bitmap>(), "image_2");
        }
        else if (flag.equals("flag_3"))
        {
            adapter = new GridAdapter(this, new ArrayList<Bitmap>(), "");
        }
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("current_image", position);
                intent.putExtra("flag", flag);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
