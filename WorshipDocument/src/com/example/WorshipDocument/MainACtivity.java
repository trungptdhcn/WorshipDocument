package com.example.WorshipDocument;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: three/12/14
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends Activity
{
    private ImageView btOne;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        btOne = (ImageView) findViewById(R.id.main_layout_bt1);
        btOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                startActivity(i);

            }
        });
    }
}