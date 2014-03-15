package com.example.WorshipDocument;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/15/14
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class DetailActivity extends Activity
{
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        viewPager = (ViewPager)findViewById(R.id.myfivepanelpager);
        viewPagerAdapter = new ViewPagerAdapter(this,"html_1");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
    }
}