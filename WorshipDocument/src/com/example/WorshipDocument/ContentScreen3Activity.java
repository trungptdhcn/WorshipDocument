package com.example.WorshipDocument;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.WorshipDocument.adapter.Screen3Adapter;
import com.example.WorshipDocument.data.ModelForScreen3DTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContentScreen3Activity extends ListActivity
{

    static final String[] values = new String[12];
    public static MediaPlayer[] mediaPlayer = new MediaPlayer[12];

    List<ModelForScreen3DTO> modelForScreen3DTOList = new ArrayList<ModelForScreen3DTO>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        modelForScreen3DTOList = getObjectDTOList("html_3");
        for (int i = 0; i < modelForScreen3DTOList.size(); i++)
        {
            values[i] = modelForScreen3DTOList.get(i).getContent();
            getSource(i + 1);
        }
        setListAdapter(new Screen3Adapter(this, values));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getApplicationContext(), "===>position" + position, 1).show();
    }

    public List<ModelForScreen3DTO> getObjectDTOList(String dirFromHtml)
    {
        List<ModelForScreen3DTO> modelForScreen3DTOs = new ArrayList<ModelForScreen3DTO>();
        try
        {
            String[] fileList = getAssets().list(dirFromHtml);

            if (fileList != null)
            {
                for (int i = 0; i <= fileList.length - 1; i++)
                {
                    int j = i + 1;
                    ModelForScreen3DTO modelForScreen3DTO = new ModelForScreen3DTO();
                    InputStream stream = getAssets().open(dirFromHtml + "/" + j + ".htm");
                    ByteArrayOutputStream oas = new ByteArrayOutputStream();
                    copyStream(stream, oas);
                    String t = oas.toString();
                    try
                    {
                        oas.close();
                        oas = null;
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    modelForScreen3DTO.setContent(t);
                    modelForScreen3DTOs.add(modelForScreen3DTO);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return modelForScreen3DTOs;
    }

    private void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 1024;
        try
        {
            byte[] bytes = new byte[buffer_size];
            for (; ; )
            {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                {
                    break;
                }
                os.write(bytes, 0, count);
            }
        }
        catch (Exception ex)
        {
        }
    }

    private void getSource(int position)
    {
        Uri uri = null;
        if (position < 10)
        {
            uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_b0" + position);
        }
        else
        {
            uri = Uri.parse("android.resource://com.example.WorshipDocument/raw/_b" + position);
        }
        if (mediaPlayer[position - 1] == null)
        {
            mediaPlayer[position - 1] = MediaPlayer.create(this, uri);
        }
        else
        {
            mediaPlayer[position - 1] = MediaPlayer.create(this, uri);
        }
    }

}