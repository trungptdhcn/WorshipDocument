package com.example.WorshipDocument.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.example.WorshipDocument.ContentScreen3Activity;
import com.example.WorshipDocument.R;

public class Screen3Adapter extends ArrayAdapter<String>
{
    private final Context context;
    private final String[] values;

    private final Handler handler = new Handler();

    public Screen3Adapter(Context context, String[] values)
    {
        super(context, R.layout.content_screen3, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.content_screen3, parent, false);
        WebView webView = (WebView) rowView.findViewById(R.id.label);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.loadDataWithBaseURL(null, values[position], "text/html", "utf-8", null);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (!ContentScreen3Activity.mediaPlayer[position].isPlaying())
                {
                    imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.matte_pause_big_focused));
                    try
                    {
                        ContentScreen3Activity.mediaPlayer[position].start();
                        startPlayProgressUpdater(position, imageView);
                    }
                    catch (IllegalStateException e)
                    {
                        ContentScreen3Activity.mediaPlayer[position].pause();
                    }
                }
                else
                {
                    ContentScreen3Activity.mediaPlayer[position].pause();
                    imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.matte_play_big_focused));
                }
            }
        });
        return rowView;
    }

    public void startPlayProgressUpdater(final int position, final ImageView imageView)
    {
        if (ContentScreen3Activity.mediaPlayer[position].isPlaying())
        {
            Runnable notification = new Runnable()
            {
                public void run()
                {
                    startPlayProgressUpdater(position, imageView);
                }
            };
            handler.postDelayed(notification, 0);
        }
        else
        {
            ContentScreen3Activity.mediaPlayer[position].pause();
            imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.matte_play_big_focused));
        }
    }


}
