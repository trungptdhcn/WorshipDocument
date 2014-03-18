package com.example.WorshipDocument;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/18/14
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentActivity3 extends Activity
{
    private GridView gridView;
    ContentAdapter3 contentAdapter3;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_3);
        gridView = (GridView) findViewById(R.id.content_3_grView);
        contentAdapter3 = new ContentAdapter3(this);
        gridView.setAdapter(contentAdapter3);

    }
}