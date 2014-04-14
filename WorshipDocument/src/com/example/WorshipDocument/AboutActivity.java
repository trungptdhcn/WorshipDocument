package com.example.WorshipDocument;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * User: Khiemvx
 * Date: 3/17/14
 */
public class AboutActivity extends Activity implements View.OnClickListener
{
    Button btShare, btMail, btApp, btInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        btShare = (Button) findViewById(R.id.btShare);
        btApp = (Button) findViewById(R.id.btAppstore);
        btMail = (Button) findViewById(R.id.btEmail);
        btInfo = (Button) findViewById(R.id.btInfo);

        btInfo.setOnClickListener(this);
        btShare.setOnClickListener(this);
        btApp.setOnClickListener(this);
        btMail.setOnClickListener(this);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btAppstore:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mobileservice.bookstore"));
                startActivity(browserIntent);
                break;
            case R.id.btEmail:
                String subject = "Praying Guide App";
                String message = "إذَا أرادَ القيامَ واقفًا بينَ يدَيْ ربِه العظيمِ جلَّ في علاهُ أنْ يُخلصَ \n" +
                        "القصدَ والمرادَ للهِ تعالَى ثم يُعيِّنَ الصَّلاةَ التي يريدُ القيامَ لها ظهرًا \n" +
                        "أو عصرًا...ثمَّ يعينَها فرضًا أو نفلاً... حضرًا أو سفرًا.. أداءً أو قضاءً، \n" +
                        "وعليهِ أنْ يستحضرَ نيتَهُ طوالَ الصلاةِ إنْ أمكنَهُ، وإنْ عزبتِ النيةُ أثناءَ \n" +
                        "الصلاةِ فلا بأسَ ما لم ينوِ قطعهَا أو تبديلَ النيةِ لصلاةٍ أخْرى كأ;" +
                        "\n https://play.google.com/store/apps/details?id=com.mobileservice.bookstore";
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"khiemvu.org@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                // need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client"));
                break;
            case R.id.btInfo:
                Intent intent = new Intent(AboutActivity.this, InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.btShare:
                Intent dropbox = new Intent(Intent.ACTION_SEND);
                dropbox.setType("text/plain");
                dropbox.putExtra(Intent.EXTRA_TEXT, "\"إذَا أرادَ القيامَ واقفًا بينَ يدَيْ ربِه العظيمِ جلَّ في علاهُ أنْ يُخلصَ \\n\" +\n" +
                        "                        \"القصدَ والمرادَ للهِ تعالَى ثم يُعيِّنَ الصَّلاةَ التي يريدُ القيامَ لها ظهرًا \\n\" +\n" +
                        "                        \"أو عصرًا...ثمَّ يعينَها فرضًا أو نفل");
                startActivity(dropbox);
                break;
        }
    }
}
