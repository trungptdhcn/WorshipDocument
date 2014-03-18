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
<<<<<<< Updated upstream
    private ImageView btTwo, btAbout;
=======
    private ImageView btTwo;
    private ImageView btThree;
>>>>>>> Stashed changes

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        btOne = (ImageView) findViewById(R.id.main_layout_bt1);
        btTwo = (ImageView) findViewById(R.id.main_layout_bt2);
<<<<<<< Updated upstream
        btAbout = (ImageView) findViewById(R.id.main_layout_bt4);
=======
        btThree = (ImageView)findViewById(R.id.main_layout_bt3);
>>>>>>> Stashed changes
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
                Intent intent3 = new Intent(getApplicationContext(),ContentActivity3.class);
                intent3.putExtra("flag","flag_2") ;
                startActivity(intent3);
            }
        });

    }
}