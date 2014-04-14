package com.example.WorshipDocument.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.WorshipDocument.MainActivity;
import com.example.WorshipDocument.R;
import com.example.WorshipDocument.data.ContentDetail;
import com.example.WorshipDocument.popup.Popup;
import com.example.WorshipDocument.popup.PopupItem;
import com.example.WorshipDocument.popup.action.PopupListener;
import com.example.WorshipDocument.popup.common.PopupLocationType;
import com.example.WorshipDocument.popup.common.PopupSetting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Trung
 * Date: 3/15/14
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ViewPagerAdapter extends PagerAdapter
{
    Popup popupControl;
    List<PopupItem> itemListShare = new ArrayList<PopupItem>();
    ;
    Boolean isShowing = false;
    private Context context;
    private String dirHtml;
    private List<ContentDetail> contentDetailList = new ArrayList<ContentDetail>();
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    public List<String> assetFiles = new ArrayList<String>();

    public ViewPagerAdapter(Context context, String dirFromHtml, String dirFromImage)
    {
        this.dirHtml = dirFromHtml;
        this.context = context;
        this.contentDetailList = getContentDetailList(dirFromHtml, dirFromImage);
        if (dirFromImage.equals(""))
        {
            options = new DisplayImageOptions.Builder()
                    .build();
        }
        else
        {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.content_image_thumnail_background)
                    .showImageForEmptyUri(R.drawable.content_image_thumnail_background)
                    .showImageOnFail(R.drawable.content_image_thumnail_background)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    .build();
        }
        initPopupMenu();
    }

    @Override
    public int getCount()
    {
        return contentDetailList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        view = inflater.inflate(R.layout.detail_item, null);
        ((ViewPager) container).addView(view, 0);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        final RelativeLayout relativeLayoutContainer = (RelativeLayout) view.findViewById(R.id.detail_item_rlContainerContent);
        ImageView ivZomIn = (ImageView) view.findViewById(R.id.detail_layout_btZoomIn);
        ImageView ivZomOut = (ImageView) view.findViewById(R.id.detail_layout_btZoomOut);
        ImageView btHome = (ImageView) view.findViewById(R.id.detail_layout_btHome);
        final ImageView btShare = (ImageView) view.findViewById(R.id.detail_layout_btShare);

        final WebView webView = (WebView) view.findViewById(R.id.detail_item_wvContent);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.setHapticFeedbackEnabled(true);
        webView.setLongClickable(true);
        webView.setBackgroundColor(0x00000000);
        webView.loadDataWithBaseURL(null, contentDetailList.get(position).getContent(), "text/html", "utf-8", null);
        final ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ivZomIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                webView.zoomIn();
            }
        });
        btHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        btShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                setupPopup(itemListShare, position);
//                popupControl.showBelow(btShare, PopupLocationType.RIGHT);
//                shareOnWhatsApp(position);
                String filePath = getFilePath(position + 1);
                Intent dropbox = new Intent(Intent.ACTION_SEND);
                dropbox.setType("text/plain");
                dropbox.putExtra(Intent.EXTRA_TEXT, contentDetailList.get(position).getContent());
                context.startActivity(dropbox);
            }
        });
        ivZomOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                webView.zoomOut();
            }
        });
        ivZomOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                webView.zoomOut();
            }
        });

        ImageView ivImage = (ImageView) view.findViewById(R.id.detail_item_ivImage);
        imageLoader.displayImage(contentDetailList.get(position).getFileImage(), ivImage, options);


        setupPopup(itemListShare, position);
        if (isShowing)
        {
            popupControl.showAbove(btShare, PopupLocationType.CENTER);
        }

        return view;
    }

    private void setupPopup(final List<PopupItem> itemListShare, final int position)
    {
        popupControl = new Popup(context, PopupSetting.STRING_ONLY_TYPE, itemListShare);

        popupControl.setOnItemClickListener(new PopupListener()
        {
            String filePath = null;

            @Override
            public void onItemClick(int itemId, Boolean value)
            {
                switch (itemId)
                {
                    case 0:
                        //Todo share to face
                        try
                        {
                            shareOnFacebook(position);
                        }
                        catch (PackageManager.NameNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        //Todo share to whatsapp
                        shareOnWhatsApp(position);
                        break;
                    case 2:
                        //Todo share to dropbox
                        filePath = getFilePath(position + 1);
                        Intent dropbox = new Intent(Intent.ACTION_SEND);
                        dropbox.setType("text/plain");
                        dropbox.putExtra(Intent.EXTRA_TEXT, "test tset adsf klsjdfkasd");
                        context.startActivity(dropbox);
                        break;
                    case 3:
                        //Todo share to email
                        filePath = getFilePath(position + 1);
                        String subject = "Praying Guide App";
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
                        emailIntent.setType("image/jpeg");
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                        emailIntent.setType("message/rfc822");
                        context.startActivity(emailIntent);

                        break;
                }
            }

            @Override
            public void onDismiss()
            {

            }
        });
    }

    private void initPopupMenu()
    {
        itemListShare.add(new PopupItem(0, context.getResources().getString(R.string.share_face), "true"));
        itemListShare.add(new PopupItem(1, context.getResources().getString(R.string.share_whatsapp), "true"));
        itemListShare.add(new PopupItem(2, context.getResources().getString(R.string.share_dropbox), "true"));
        itemListShare.add(new PopupItem(3, context.getResources().getString(R.string.share_email), "true"));
    }

    private void shareOnFacebook(int currentPosition) throws PackageManager.NameNotFoundException
    {
        String facebookPackageName = "com.facebook.katana";
        try
        {
            String filePath = null;
            currentPosition = currentPosition + 1;
            context.getPackageManager().getApplicationInfo(facebookPackageName, 0);
            filePath = getFilePath(currentPosition);
            share("facebook", filePath, "Test");
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Uri uri = Uri.parse("market://details?id=" + facebookPackageName);
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(i);
        }
    }

    private void share(String nameApp, String imagePath, String text)
    {
        try
        {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("image/jpeg");
            List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
            if (!resInfo.isEmpty())
            {
                for (ResolveInfo info : resInfo)
                {
                    Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShare.setType("image/jpeg");
                    if (info.activityInfo.packageName.toLowerCase().contains(nameApp) || info.activityInfo.name.toLowerCase().contains(nameApp))
                    {
                        targetedShare.putExtra(Intent.EXTRA_TEXT, text);
                        targetedShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
                        targetedShare.setPackage(info.activityInfo.packageName);
                        targetedShareIntents.add(targetedShare);
                    }
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooserIntent);
            }
        }
        catch (Exception e)
        {
        }
    }

    private void shareOnWhatsApp(int currentPosition)
    {
        currentPosition = currentPosition + 1;
        String filePath = null;
        filePath = getFilePath(currentPosition);
        Intent whatsApp = new Intent(Intent.ACTION_SEND);
        whatsApp.setType("image/jpeg");
        whatsApp.setPackage("com.whatsapp");
        whatsApp.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        context.startActivity(Intent.createChooser(whatsApp, "Test"));
        context.startActivity(whatsApp);
    }

    private String getFilePath(int currentPosition)
    {
        String filePath;
        if (currentPosition < 10)
        {
            if (dirHtml.equals("html_1"))
            {
                filePath = "/mnt/sdcard/Praying Guide/_0" + currentPosition + ".htm";
            }
            else
            {
                filePath = "/mnt/sdcard/Praying Guide/_a0" + currentPosition + ".htm";
            }
        }
        else
        {
            if (dirHtml.equals("html_2"))
            {
                filePath = "/mnt/sdcard/Praying Guide/_" + currentPosition + ".htm";
            }
            else
            {
                filePath = "/mnt/sdcard/Praying Guide/_a" + currentPosition + ".htm";
            }
        }
        return filePath;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    public List<ContentDetail> getContentDetailList(String dirFromHtml, String dirFromImage)
    {
        List<ContentDetail> contentDetails = new ArrayList<ContentDetail>();
        try
        {
            String[] fileList = context.getAssets().list(dirFromHtml);

            List<String> listFileImage = listBitmap(dirFromImage);
            if (fileList != null && listFileImage != null)
            {
                for (int i = 0; i <= fileList.length - 1; i++)
                {
                    int j = i + 1;
                    ContentDetail contentDetail = new ContentDetail();
                    String tContents = "";
                    InputStream stream = context.getAssets().open(dirFromHtml + "/" + j + ".htm");
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
                    contentDetail.setContent(t);
                    contentDetail.setFileImage(listFileImage.get(i));
                    contentDetails.add(contentDetail);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentDetails;
    }

    private List<String> listBitmap(String dirFrom)
    {
        try
        {
            String[] fileList = context.getAssets().list(dirFrom);
            if (fileList != null)
            {
                for (int i = 0; i <= fileList.length - 1; i++)
                {
                    assetFiles.add("assets://" + dirFrom + "/" + fileList[i]);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return assetFiles;
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

    public void zoom(RelativeLayout view, Float scaleX, Float scaleY, PointF pivot)
    {
        view.setPivotX(pivot.x);
        view.setPivotY(pivot.y);
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
    }

}
