package com.example.WorshipDocument;

import android.app.Activity;
import android.os.Bundle;

/**
 * User: Khiemvx
 * Date: 3/17/14
 */
public class AboutActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
}
