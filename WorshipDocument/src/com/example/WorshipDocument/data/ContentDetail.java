package com.example.WorshipDocument.data;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/15/14
 * Time: 1:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContentDetail
{
    private String fileImage;
    private String content;
    private String fileSound;

    public ContentDetail()
    {
    }

    public ContentDetail(String fileImage, String content, String fileSound)
    {
        this.fileImage = fileImage;
        this.content = content;
        this.fileSound = fileSound;
    }

    public String getFileImage()
    {
        return fileImage;
    }

    public void setFileImage(String fileImage)
    {
        this.fileImage = fileImage;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String fileContent)
    {
        this.content = fileContent;
    }

    public String getFileSound()
    {
        return fileSound;
    }

    public void setFileSound(String fileSound)
    {
        this.fileSound = fileSound;
    }
}
