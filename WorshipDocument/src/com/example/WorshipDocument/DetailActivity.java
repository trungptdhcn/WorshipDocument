package com.example.WorshipDocument;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/15/14
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class DetailActivity extends Activity implements View.OnClickListener
{
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private ImageView ivImage;
    private ImageView btFirst;
    private ImageView btLast;
    private ImageView btPrevious;
    private ImageView btNext;
    private ImageView btHome;
    private ImageView btCopy;
    private ImageView btZomIn;
    private ImageView bZomOut;
    int currentPosition;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        viewPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        btFirst = (ImageView)findViewById(R.id.detail_layout_btFirst);
        btLast = (ImageView)findViewById(R.id.detail_layout_btLast);
        btPrevious = (ImageView)findViewById(R.id.detail_layout_btPrevious);
        btNext = (ImageView)findViewById(R.id.detail_layout_btNext);
        btHome = (ImageView)findViewById(R.id.detail_layout_btHome);
        btCopy = (ImageView)findViewById(R.id.detail_layout_btCopy);
        btZomIn = (ImageView)findViewById(R.id.detail_layout_btZom_In);
        bZomOut = (ImageView)findViewById(R.id.detail_layout_btZom_Out);

        viewPagerAdapter = new ViewPagerAdapter(this, "html_1","image_1");
        viewPager.setAdapter(viewPagerAdapter);
        int position = getIntent().getIntExtra("current_image", -1);
        if (position == -1)
        {
            currentPosition = 0;
        }
        else
        {
            currentPosition = position;
        }
        viewPager.setCurrentItem(currentPosition);
        clickEvent();

    }
    public void clickEvent()
    {
        btFirst.setOnClickListener(this);
        btLast.setOnClickListener(this);
        btPrevious.setOnClickListener(this);
        btNext.setOnClickListener(this);
        btHome.setOnClickListener(this);
        btCopy.setOnClickListener(this);
        btZomIn.setOnClickListener(this);
        bZomOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.detail_layout_btHome:
                finish();
                break;
            case R.id.detail_layout_btCopy:
                break;
            case R.id.detail_layout_btFirst:
                viewPager.setCurrentItem(0);
                currentPosition = 0;
                break;
            case R.id.detail_layout_btLast:
                viewPager.setCurrentItem(25);
                currentPosition = 25;
                break;
            case R.id.detail_layout_btPrevious:
                if(currentPosition >=1)
                {
                    currentPosition = currentPosition-1;
                    viewPager.setCurrentItem(currentPosition);

                }

                break;
            case R.id.detail_layout_btNext:
                if(currentPosition <= 25)
                {
                    currentPosition = currentPosition+1;
                    viewPager.setCurrentItem(currentPosition);

                }

                break;
            case R.id.detail_layout_btZom_In:
                break;
            case R.id.detail_layout_btZom_Out:
                break;

        }
    }
}