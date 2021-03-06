package com.example.WorshipDocument;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity
{
    private ImageView btOne;
    private ImageView btAbout;
    private ImageView btTwo;
    private ImageView btThree;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        btOne = (ImageView) findViewById(R.id.main_layout_bt1);
        btTwo = (ImageView) findViewById(R.id.main_layout_bt2);
        btThree = (ImageView) findViewById(R.id.main_layout_bt3);
        btAbout = (ImageView) findViewById(R.id.main_layout_bt4);

        btOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), ContentActivity.class);
                i.putExtra("flag", "flag_1");
                startActivity(i);

            }
        });
        btTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("flag", "flag_2");
                startActivity(intent);
            }
        });

        btAbout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
        btThree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent3 = new Intent(getApplicationContext(), ContentScreen3Activity.class);
                startActivity(intent3);
            }
        });
    }
}